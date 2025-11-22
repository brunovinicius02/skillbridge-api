package br.com.fiap.skillbridge.repository;

import br.com.fiap.skillbridge.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Optional<Usuario> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public boolean existsByEmail(String email) {
        return count("email", email) > 0;
    }

    public Optional<Usuario> findByIdWithPerfil(Long id) {
        return find("SELECT u FROM Usuario u LEFT JOIN FETCH u.perfil WHERE u.id = ?1", id)
                .firstResultOptional();
    }
}
