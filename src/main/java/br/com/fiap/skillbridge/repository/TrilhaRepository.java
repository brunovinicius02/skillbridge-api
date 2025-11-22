package br.com.fiap.skillbridge.repository;

import br.com.fiap.skillbridge.model.Trilha;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TrilhaRepository implements PanacheRepository<Trilha> {

    public List<Trilha> findByUsuarioId(Long usuarioId) {
        return list("usuario.id", usuarioId);
    }

    public List<Trilha> findGeradasPorIA(Long usuarioId) {
        return list("usuario.id = ?1 AND geradaPorIa = '1'", usuarioId);
    }

    public List<Trilha> findManuais(Long usuarioId) {
        return list("usuario.id = ?1 AND geradaPorIa = '0'", usuarioId);
    }
}
