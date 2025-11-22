package br.com.fiap.skillbridge.repository;

import br.com.fiap.skillbridge.model.Curso;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CursoRepository implements PanacheRepository<Curso> {

    public List<Curso> findByArea(String area) {
        return list("area = ?1 AND ativo = '1'", area);
    }

    public List<Curso> findByNivel(String nivel) {
        return list("nivel = ?1 AND ativo = '1'", nivel);
    }

    public List<Curso> findByAreaAndNivel(String area, String nivel) {
        return list("area = ?1 AND nivel = ?2 AND ativo = '1'", area, nivel);
    }

    public List<Curso> findAtivos() {
        return list("ativo = '1'");
    }

    public List<Curso> searchByNome(String nome) {
        return list("LOWER(nome) LIKE LOWER(?1) AND ativo = '1'", "%" + nome + "%");
    }
}
