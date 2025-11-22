package br.com.fiap.skillbridge.repository;

import br.com.fiap.skillbridge.model.Perfil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository para gerenciar Perfis de usuários
 */
@ApplicationScoped
public class PerfilRepository implements PanacheRepository<Perfil> {

    /**
     * Busca perfil por ID do usuário
     * 
     * @param usuarioId ID do usuário
     * @return Perfil ou null se não encontrado
     */
    public Perfil findByUsuarioId(Long usuarioId) {
        return find("usuario.id", usuarioId).firstResult();
    }

    /**
     * Verifica se usuário possui perfil
     * 
     * @param usuarioId ID do usuário
     * @return true se possui perfil
     */
    public boolean existsByUsuarioId(Long usuarioId) {
        return count("usuario.id", usuarioId) > 0;
    }
}
