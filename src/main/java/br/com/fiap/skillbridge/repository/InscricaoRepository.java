package br.com.fiap.skillbridge.repository;

import br.com.fiap.skillbridge.model.Inscricao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class InscricaoRepository implements PanacheRepository<Inscricao> {

    public List<Inscricao> findByUsuarioId(Long usuarioId) {
        return find("usuario.id", usuarioId).list();
    }

    public List<Inscricao> findByUsuarioIdAndStatus(Long usuarioId, String status) {
        return list("usuario.id = ?1 AND status = ?2", usuarioId, status);
    }

    public Optional<Inscricao> findByUsuarioIdAndCursoId(Long usuarioId, Long cursoId) {
        return find("usuario.id = ?1 AND curso.id = ?2", usuarioId, cursoId).firstResultOptional();
    }

    public boolean existsByUsuarioIdAndCursoId(Long usuarioId, Long cursoId) {
        return count("usuario.id = ?1 AND curso.id = ?2", usuarioId, cursoId) > 0;
    }

    public List<Inscricao> findConcluidos(Long usuarioId) {
        return list("usuario.id = ?1 AND status = 'CONCLUIDO'", usuarioId);
    }

    public List<Inscricao> findEmAndamento(Long usuarioId) {
        return list("usuario.id = ?1 AND status = 'EM_ANDAMENTO'", usuarioId);
    }
}
