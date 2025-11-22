package br.com.fiap.skillbridge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_COMPETENCIA_CARREIRA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetenciaCarreira {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_comp_carreira")
    @SequenceGenerator(name = "seq_comp_carreira", sequenceName = "SEQ_COMPETENCIA_CARREIRA", allocationSize = 1)
    @Column(name = "ID_COMPETENCIA_CARREIRA")
    private Long id;

    @NotNull(message = "Carreira é obrigatória")
    @ManyToOne
    @JoinColumn(name = "ID_CARREIRA", nullable = false)
    private Carreira carreira;

    @NotBlank(message = "Nome da competência é obrigatório")
    @Size(max = 50, message = "Nome da competência deve ter no máximo 50 caracteres")
    @Column(name = "NOME_COMPETENCIA", nullable = false, length = 50)
    private String nomeCompetencia;

    @NotBlank(message = "Nível mínimo é obrigatório")
    @Size(max = 20, message = "Nível mínimo deve ter no máximo 20 caracteres")
    @Column(name = "NIVEL_MINIMO", nullable = false, length = 20)
    private String nivelMinimo;

    @Column(name = "OBRIGATORIA", length = 1, insertable = false)
    private String obrigatoria;

    @PrePersist
    protected void onCreate() {
        if (obrigatoria == null) {
            obrigatoria = "1";
        }
    }
}
