package br.com.fiap.skillbridge.repository;

import br.com.fiap.skillbridge.model.Recomendacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class RecomendacaoRepository implements PanacheRepository<Recomendacao> {

    public List<Recomendacao> findByUsuarioId(Long usuarioId) {
        return find("usuario.id = ?1 ORDER BY scoreRelevancia DESC", usuarioId).list();
    }

    public List<Recomendacao> findNaoVisualizadas(Long usuarioId) {
        return list("usuario.id = ?1 AND visualizada = '0' ORDER BY scoreRelevancia DESC", usuarioId);
    }

    public List<Recomendacao> findTopRecomendacoes(Long usuarioId, int limit) {
        return find("usuario.id = ?1 ORDER BY scoreRelevancia DESC", usuarioId)
                .page(0, limit)
                .list();
    }

    public long countNaoVisualizadas(Long usuarioId) {
        return count("usuario.id = ?1 AND visualizada = '0'", usuarioId);
    }
}
