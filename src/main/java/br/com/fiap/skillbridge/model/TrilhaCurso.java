package br.com.fiap.skillbridge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_TRILHA_CURSO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrilhaCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_trilha_curso")
    @SequenceGenerator(name = "seq_trilha_curso", sequenceName = "SEQ_TRILHA_CURSO", allocationSize = 1)
    @Column(name = "ID_TRILHA_CURSO")
    private Long id;

    @NotNull(message = "Trilha é obrigatória")
    @ManyToOne
    @JoinColumn(name = "ID_TRILHA", nullable = false)
    private Trilha trilha;

    @NotNull(message = "Curso é obrigatório")
    @ManyToOne
    @JoinColumn(name = "ID_CURSO", nullable = false)
    private Curso curso;

    @NotNull(message = "Ordem é obrigatória")
    @Min(value = 1, message = "Ordem deve ser maior que zero")
    @Column(name = "ORDEM", nullable = false)
    private Integer ordem;
}
