package br.com.fiap.skillbridge.service;

import br.com.fiap.skillbridge.dto.PerfilDTO;
import br.com.fiap.skillbridge.model.Perfil;
import br.com.fiap.skillbridge.model.Usuario;
import br.com.fiap.skillbridge.repository.PerfilRepository;
import br.com.fiap.skillbridge.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PerfilService {

    @Inject
    PerfilRepository perfilRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    public PerfilDTO buscarPorUsuarioId(Long usuarioId) {
        // CORRIGIDO: findByUsuarioId retorna Perfil direto (não Optional)
        Perfil perfil = perfilRepository.findByUsuarioId(usuarioId);
        if (perfil == null) {
            throw new NotFoundException("Perfil não encontrado");
        }
        return toDTO(perfil);
    }

    @Transactional
    public PerfilDTO criarOuAtualizar(Long usuarioId, PerfilDTO dto) {
        Usuario usuario = usuarioRepository.findByIdOptional(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        // CORRIGIDO: findByUsuarioId retorna Perfil direto (não Optional)
        Perfil perfil = perfilRepository.findByUsuarioId(usuarioId);
        if (perfil == null) {
            perfil = new Perfil();
        }

        perfil.setUsuario(usuario);
        perfil.setObjetivoCarreira(dto.getObjetivoCarreira());
        perfil.setNivelExperiencia(dto.getNivelExperiencia());
        perfil.setTempoDisponivelSemanal(dto.getTempoDisponivelSemanal());
        perfil.setIdade(dto.getIdade());
        perfil.setEscolaridade(dto.getEscolaridade());
        perfil.setAnosExperienciaTotal(dto.getAnosExperienciaTotal());
        perfil.setBiografia(dto.getBiografia());
        perfil.setAreaAtuacao(dto.getAreaAtuacao());
        perfil.setCargoAtual(dto.getCargoAtual());
        perfil.setObjetivoProfissional(dto.getObjetivoProfissional());

        perfilRepository.persist(perfil);
        return toDTO(perfil);
    }

    private PerfilDTO toDTO(Perfil perfil) {
        PerfilDTO dto = new PerfilDTO();
        dto.setObjetivoCarreira(perfil.getObjetivoCarreira());
        dto.setNivelExperiencia(perfil.getNivelExperiencia());
        dto.setTempoDisponivelSemanal(perfil.getTempoDisponivelSemanal());
        dto.setIdade(perfil.getIdade());
        dto.setEscolaridade(perfil.getEscolaridade());
        dto.setAnosExperienciaTotal(perfil.getAnosExperienciaTotal());
        dto.setBiografia(perfil.getBiografia());
        dto.setAreaAtuacao(perfil.getAreaAtuacao());
        dto.setCargoAtual(perfil.getCargoAtual());
        dto.setObjetivoProfissional(perfil.getObjetivoProfissional());
        return dto;
    }
}
