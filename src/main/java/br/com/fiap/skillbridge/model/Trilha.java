package br.com.fiap.skillbridge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_TRILHAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trilha {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_trilha")
    @SequenceGenerator(name = "seq_trilha", sequenceName = "SEQ_TRILHA", allocationSize = 1)
    @Column(name = "ID_TRILHA")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @NotBlank(message = "Nome da trilha é obrigatório")
    @Size(max = 100, message = "Nome da trilha deve ter no máximo 100 caracteres")
    @Column(name = "NOME_TRILHA", nullable = false, length = 100)
    private String nomeTrilha;

    @Lob
    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "GERADA_POR_IA", length = 1, insertable = false)
    private String geradaPorIa;

    @Column(name = "DATA_CRIACAO", insertable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now();
        }
        if (geradaPorIa == null) {
            geradaPorIa = "0";
        }
    }
}
