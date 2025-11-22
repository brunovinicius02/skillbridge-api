package br.com.fiap.skillbridge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_COMPETENCIA_USUARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetenciaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_comp_usuario")
    @SequenceGenerator(name = "seq_comp_usuario", sequenceName = "SEQ_COMPETENCIA_USUARIO", allocationSize = 1)
    @Column(name = "ID_COMPETENCIA_USUARIO")
    private Long id;

    @NotNull(message = "Usuário é obrigatório")
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @NotBlank(message = "Nome da competência é obrigatório")
    @Size(max = 50, message = "Nome da competência deve ter no máximo 50 caracteres")
    @Column(name = "NOME_COMPETENCIA", nullable = false, length = 50)
    private String nomeCompetencia;

    @Size(max = 20, message = "Nível de domínio deve ter no máximo 20 caracteres")
    @Column(name = "NIVEL_DOMINIO", length = 20)
    private String nivelDominio;

    @Column(name = "DATA_ADICAO", insertable = false)
    private LocalDateTime dataAdicao;

    @Column(name = "VALIDADA", length = 1, insertable = false)
    private String validada;

    @Min(value = 0, message = "Anos de experiência deve ser positivo")
    @Column(name = "ANOS_EXPERIENCIA", insertable = false)
    private Integer anosExperiencia;

    @PrePersist
    protected void onCreate() {
        if (dataAdicao == null) {
            dataAdicao = LocalDateTime.now();
        }
        if (validada == null) {
            validada = "0";
        }
        if (anosExperiencia == null) {
            anosExperiencia = 0;
        }
    }
}
