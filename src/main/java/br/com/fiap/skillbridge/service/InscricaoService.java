package br.com.fiap.skillbridge.service;

import br.com.fiap.skillbridge.model.Curso;
import br.com.fiap.skillbridge.model.Inscricao;
import br.com.fiap.skillbridge.model.Usuario;
import br.com.fiap.skillbridge.repository.CursoRepository;
import br.com.fiap.skillbridge.repository.InscricaoRepository;
import br.com.fiap.skillbridge.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class InscricaoService {

    @Inject
    InscricaoRepository inscricaoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    CursoRepository cursoRepository;

    public List<Inscricao> listarPorUsuario(Long usuarioId) {
        return inscricaoRepository.findByUsuarioId(usuarioId);
    }

    public List<Inscricao> listarEmAndamento(Long usuarioId) {
        return inscricaoRepository.findEmAndamento(usuarioId);
    }

    public List<Inscricao> listarConcluidos(Long usuarioId) {
        return inscricaoRepository.findConcluidos(usuarioId);
    }

    public Inscricao buscarPorId(Long id) {
        return inscricaoRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Inscrição não encontrada"));
    }

    @Transactional
    public Inscricao criar(Long usuarioId, Long cursoId) {
        Usuario usuario = usuarioRepository.findByIdOptional(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        Curso curso = cursoRepository.findByIdOptional(cursoId)
                .orElseThrow(() -> new NotFoundException("Curso não encontrado"));

        if (inscricaoRepository.existsByUsuarioIdAndCursoId(usuarioId, cursoId)) {
            throw new BadRequestException("Usuário já inscrito neste curso");
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setUsuario(usuario);
        inscricao.setCurso(curso);

        inscricaoRepository.persist(inscricao);
        return inscricao;
    }

    @Transactional
    public Inscricao atualizarProgresso(Long id, BigDecimal progresso) {
        Inscricao inscricao = buscarPorId(id);
        inscricao.setProgresso(progresso);
        inscricaoRepository.persist(inscricao);
        return inscricao;
    }

    @Transactional
    public Inscricao marcarComoConcluido(Long id, BigDecimal nota) {
        Inscricao inscricao = buscarPorId(id);
        inscricao.setProgresso(new BigDecimal("100"));
        inscricao.setStatus("CONCLUIDO");
        if (nota != null) {
            inscricao.setNotaAvaliacao(nota);
        }
        inscricaoRepository.persist(inscricao);
        return inscricao;
    }

    @Transactional
    public void deletar(Long id) {
        Inscricao inscricao = buscarPorId(id);
        inscricaoRepository.delete(inscricao);
    }
}
