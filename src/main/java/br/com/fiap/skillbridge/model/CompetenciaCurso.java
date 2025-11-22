package br.com.fiap.skillbridge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_COMPETENCIA_CURSO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetenciaCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_comp_curso")
    @SequenceGenerator(name = "seq_comp_curso", sequenceName = "SEQ_COMPETENCIA_CURSO", allocationSize = 1)
    @Column(name = "ID_COMPETENCIA_CURSO")
    private Long id;

    @NotNull(message = "Curso é obrigatório")
    @ManyToOne
    @JoinColumn(name = "ID_CURSO", nullable = false)
    private Curso curso;

    @NotBlank(message = "Nome da competência é obrigatório")
    @Size(max = 50, message = "Nome da competência deve ter no máximo 50 caracteres")
    @Column(name = "NOME_COMPETENCIA", nullable = false, length = 50)
    private String nomeCompetencia;

    @Size(max = 20, message = "Nível ensinado deve ter no máximo 20 caracteres")
    @Column(name = "NIVEL_ENSINADO", length = 20)
    private String nivelEnsinado;
}
