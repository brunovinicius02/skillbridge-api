package br.com.fiap.skillbridge.service;

import br.com.fiap.skillbridge.dto.LoginDTO;
import br.com.fiap.skillbridge.dto.UsuarioCreateDTO;
import br.com.fiap.skillbridge.dto.UsuarioResponseDTO;
import br.com.fiap.skillbridge.model.Usuario;
import br.com.fiap.skillbridge.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.listAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return toResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO criar(UsuarioCreateDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenhaHash(BCrypt.hashpw(dto.getSenha(), BCrypt.gensalt()));

        usuarioRepository.persist(usuario);
        return toResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioCreateDTO dto) {
        Usuario usuario = usuarioRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        // Verifica se email já existe em outro usuário
        if (!usuario.getEmail().equals(dto.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email já cadastrado");
        }

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenhaHash(BCrypt.hashpw(dto.getSenha(), BCrypt.gensalt()));
        }

        usuarioRepository.persist(usuario);
        return toResponseDTO(usuario);
    }

    @Transactional
    public void deletar(Long id) {
        Usuario usuario = usuarioRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        usuarioRepository.delete(usuario);
    }

    @Transactional
    public UsuarioResponseDTO login(LoginDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BadRequestException("Email ou senha inválidos"));

        if (!BCrypt.checkpw(dto.getSenha(), usuario.getSenhaHash())) {
            throw new BadRequestException("Email ou senha inválidos");
        }

        // Atualiza último acesso
        usuario.setUltimoAcesso(LocalDateTime.now());
        usuarioRepository.persist(usuario);

        return toResponseDTO(usuario);
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setAtivo(usuario.getAtivo());
        dto.setDataCadastro(usuario.getDataCadastro());
        dto.setUltimoAcesso(usuario.getUltimoAcesso());
        return dto;
    }
}
