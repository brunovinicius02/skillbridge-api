package br.com.fiap.skillbridge.service;

import br.com.fiap.skillbridge.dto.RecomendacaoRequestDTO;
import br.com.fiap.skillbridge.dto.RecomendacaoResponseDTO;
import br.com.fiap.skillbridge.model.Curso;
import br.com.fiap.skillbridge.model.Perfil;
import br.com.fiap.skillbridge.model.Recomendacao;
import br.com.fiap.skillbridge.model.Usuario;
import br.com.fiap.skillbridge.repository.CursoRepository;
import br.com.fiap.skillbridge.repository.PerfilRepository;
import br.com.fiap.skillbridge.repository.RecomendacaoRepository;
import br.com.fiap.skillbridge.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service responsável por integrar com a API de IA (Flask) para gerar recomendações de cursos
 */
@ApplicationScoped
public class RecomendacaoIAService {

    private static final Logger LOG = Logger.getLogger(RecomendacaoIAService.class);

    @ConfigProperty(name = "ia.api.url", defaultValue = "http://localhost:5000")
    String iaApiUrl;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    PerfilRepository perfilRepository;

    @Inject
    CursoRepository cursoRepository;

    @Inject
    RecomendacaoRepository recomendacaoRepository;

    /**
     * Apaga recomendações antigas em uma transação separada
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    void apagarRecomendacoesAntigas(Long usuarioId) {
        try {
            recomendacaoRepository.delete("usuario.id", usuarioId);
            LOG.infof("✓ Recomendações antigas do usuário %d foram apagadas", usuarioId);
        } catch (Exception e) {
            LOG.warnf("Erro ao apagar recomendações antigas: %s", e.getMessage());
        }
    }

    /**
     * Gera recomendações personalizadas para um usuário chamando a API de IA
     */
    @Transactional
    public List<Recomendacao> gerarRecomendacoes(Long usuarioId, Integer topN) {
        LOG.infof("Gerando recomendações para usuário %d (top %d)", usuarioId, topN);

        // APAGAR recomendações antigas PRIMEIRO (transação separada)
        apagarRecomendacoesAntigas(usuarioId);

        // Buscar usuário
        Usuario usuario = usuarioRepository.findById(usuarioId);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado: " + usuarioId);
        }

        // CORRIGIDO: findByUsuarioId retorna Perfil direto (não Optional)
        Perfil perfil = perfilRepository.findByUsuarioId(usuarioId);
        if (perfil == null) {
            LOG.warnf("Usuário %d não possui perfil, criando perfil padrão", usuarioId);
            perfil = criarPerfilPadrao(usuario);
        }

        // Construir request para API de IA
        RecomendacaoRequestDTO request = construirRequest(usuario, perfil, topN);

        try {
            // Chamar API de IA
            RecomendacaoResponseDTO response = chamarAPIIA(request);

            if (response == null || response.getRecomendacoes() == null) {
                LOG.error("Resposta da API de IA está vazia");
                return new ArrayList<>();
            }

            // Salvar recomendações no banco
            List<Recomendacao> recomendacoes = salvarRecomendacoes(usuario, response);

            LOG.infof("✓ %d recomendações geradas e salvas para usuário %d", 
                      recomendacoes.size(), usuarioId);

            return recomendacoes;

        } catch (Exception e) {
            LOG.errorf(e, "Erro ao gerar recomendações para usuário %d", usuarioId);
            throw new RuntimeException("Erro ao comunicar com API de IA", e);
        }
    }

    /**
     * Chama a API Flask de IA para obter recomendações
     */
    private RecomendacaoResponseDTO chamarAPIIA(RecomendacaoRequestDTO request) {
        String endpoint = iaApiUrl + "/recomendar";
        LOG.infof("Chamando API de IA: %s", endpoint);

        Client client = ClientBuilder.newClient();
        
        try (Response response = client.target(endpoint)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(request))) {

            if (response.getStatus() != 200) {
                String error = response.readEntity(String.class);
                LOG.errorf("Erro na API de IA [%d]: %s", response.getStatus(), error);
                throw new RuntimeException("API de IA retornou erro: " + response.getStatus());
            }

            RecomendacaoResponseDTO responseDTO = response.readEntity(RecomendacaoResponseDTO.class);
            LOG.infof("✓ API de IA respondeu com %d recomendações", 
                      responseDTO.getTotalRecomendacoes());
            
            return responseDTO;

        } catch (Exception e) {
            LOG.error("Erro ao chamar API de IA", e);
            throw new RuntimeException("Falha na comunicação com API de IA", e);
        } finally {
            client.close();
        }
    }

    /**
     * Salva as recomendações retornadas pela IA no banco de dados
     */
    private List<Recomendacao> salvarRecomendacoes(Usuario usuario, RecomendacaoResponseDTO response) {
        List<Recomendacao> recomendacoes = new ArrayList<>();

        for (RecomendacaoResponseDTO.RecomendacaoItemDTO item : response.getRecomendacoes()) {
            try {
                // Buscar curso
                Long cursoId = item.getCurso().getIdCurso();
                Curso curso = cursoRepository.findById(cursoId);

                if (curso == null) {
                    LOG.warnf("Curso %d não encontrado, pulando recomendação", cursoId);
                    continue;
                }

                // Criar entidade Recomendacao
                Recomendacao recomendacao = new Recomendacao();
                recomendacao.setUsuario(usuario);
                recomendacao.setCurso(curso);
                recomendacao.setScoreRelevancia(item.getScoreRelevancia());
                recomendacao.setMotivo(item.getMotivo());
                recomendacao.setModeloIa(item.getModeloIa());
                recomendacao.setVersaoModelo(item.getVersaoModelo());
                recomendacao.setVisualizada("0");
                recomendacao.setSeInscreveu("0");
                recomendacao.setDataRecomendacao(LocalDateTime.now());

                // Persistir
                recomendacaoRepository.persist(recomendacao);
                recomendacoes.add(recomendacao);

            } catch (Exception e) {
                LOG.errorf(e, "Erro ao salvar recomendação do curso %d", 
                           item.getCurso().getIdCurso());
            }
        }

        return recomendacoes;
    }

    /**
     * Constrói o DTO de request para a API de IA no formato correto
     */
    private RecomendacaoRequestDTO construirRequest(Usuario usuario, Perfil perfil, Integer topN) {
        // Buscar TODOS os cursos ativos do banco
        List<Curso> todosCursos = cursoRepository.findAtivos();
        
        // FILTRAR APENAS CURSOS COM ID >= 10000
        List<Curso> cursosValidos = todosCursos.stream()
            .filter(c -> c.getId() >= 10000)
            .toList();
        
        LOG.infof("Total de cursos ativos: %d | Cursos com ID >= 10000: %d", 
                  todosCursos.size(), cursosValidos.size());
        
        RecomendacaoRequestDTO.PerfilRecomendacaoDTO perfilDTO = 
            new RecomendacaoRequestDTO.PerfilRecomendacaoDTO();
        
        perfilDTO.setNivelExperiencia(perfil.getNivelExperiencia());
        perfilDTO.setTempoDisponivelSemanal(perfil.getTempoDisponivelSemanal());
        perfilDTO.setIdade(perfil.getIdade());
        perfilDTO.setAnosExperienciaTotal(perfil.getAnosExperienciaTotal());
        perfilDTO.setObjetivoCarreira(perfil.getObjetivoCarreira());

        RecomendacaoRequestDTO request = new RecomendacaoRequestDTO();
        request.setUsuarioId(usuario.getId());
        request.setPerfil(perfilDTO);
        request.setTopN(topN != null ? topN : 10);
        request.setCursos(cursosValidos); // Enviar apenas cursos >= 10000

        return request;
    }

    /**
     * Cria perfil padrão para usuário sem perfil
     */
    private Perfil criarPerfilPadrao(Usuario usuario) {
        Perfil perfil = new Perfil();
        perfil.setUsuario(usuario);
        perfil.setNivelExperiencia("JUNIOR");
        perfil.setTempoDisponivelSemanal(new BigDecimal("5.0"));
        perfil.setIdade(25);
        perfil.setAnosExperienciaTotal(0);
        perfilRepository.persist(perfil);
        return perfil;
    }

    /**
     * Busca recomendações já geradas para um usuário
     */
    public List<Recomendacao> buscarRecomendacoesUsuario(Long usuarioId) {
        return recomendacaoRepository.findByUsuarioId(usuarioId);
    }

    /**
     * Marca uma recomendação como visualizada
     */
    @Transactional
    public void marcarComoVisualizada(Long recomendacaoId) {
        Recomendacao recomendacao = recomendacaoRepository.findById(recomendacaoId);
        if (recomendacao != null) {
            recomendacao.setVisualizada("1");
            recomendacaoRepository.persist(recomendacao);
        }
    }

    /**
     * Marca que o usuário se inscreveu no curso recomendado
     */
    @Transactional
    public void marcarInscricao(Long recomendacaoId, LocalDateTime dataInscricao) {
        Recomendacao recomendacao = recomendacaoRepository.findById(recomendacaoId);
        if (recomendacao != null) {
            recomendacao.setSeInscreveu("1");
            
            // Calcular tempo entre recomendação e inscrição
            if (recomendacao.getDataRecomendacao() != null) {
                long diasAteInscricao = java.time.temporal.ChronoUnit.DAYS
                    .between(recomendacao.getDataRecomendacao(), dataInscricao);
                recomendacao.setTempoAteInscricaoDias((int) diasAteInscricao);
            }
            
            recomendacaoRepository.persist(recomendacao);
        }
    }
}
