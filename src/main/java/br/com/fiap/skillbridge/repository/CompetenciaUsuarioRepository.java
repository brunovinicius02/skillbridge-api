package br.com.fiap.skillbridge.repository;

import br.com.fiap.skillbridge.model.CompetenciaUsuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CompetenciaUsuarioRepository implements PanacheRepository<CompetenciaUsuario> {

    public List<CompetenciaUsuario> findByUsuarioId(Long usuarioId) {
        return list("usuario.id", usuarioId);
    }

    public List<CompetenciaUsuario> findValidadas(Long usuarioId) {
        return list("usuario.id = ?1 AND validada = '1'", usuarioId);
    }

    public boolean existsByUsuarioIdAndNome(Long usuarioId, String nomeCompetencia) {
        return count("usuario.id = ?1 AND nomeCompetencia = ?2", usuarioId, nomeCompetencia) > 0;
    }
}
