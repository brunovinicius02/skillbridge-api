package br.com.fiap.skillbridge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String ativo;
    private LocalDateTime dataCadastro;
    private LocalDateTime ultimoAcesso;
}
