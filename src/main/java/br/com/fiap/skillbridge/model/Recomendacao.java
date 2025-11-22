package br.com.fiap.skillbridge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_RECOMENDACAO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recomendacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_recomendacao")
    @SequenceGenerator(name = "seq_recomendacao", sequenceName = "SEQ_RECOMENDACAO", allocationSize = 1)
    @Column(name = "ID_RECOMENDACAO")
    private Long id;

    @NotNull(message = "Usuário é obrigatório")
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @NotNull(message = "Curso é obrigatório")
    @ManyToOne
    @JoinColumn(name = "ID_CURSO", nullable = false)
    private Curso curso;

    @NotNull(message = "Score de relevância é obrigatório")
    @DecimalMin(value = "0.0", message = "Score deve ser positivo")
    @Column(name = "SCORE_RELEVANCIA", nullable = false, precision = 5, scale = 2)
    private BigDecimal scoreRelevancia;

    @Lob
    @Column(name = "MOTIVO")
    private String motivo;

    @Column(name = "VISUALIZADA", length = 1, insertable = false)
    private String visualizada;

    @Column(name = "SE_INSCREVEU", length = 1, insertable = false)
    private String seInscreveu;

    @Column(name = "DATA_RECOMENDACAO", insertable = false)
    private LocalDateTime dataRecomendacao;

    @Size(max = 50, message = "Modelo IA deve ter no máximo 50 caracteres")
    @Column(name = "MODELO_IA", length = 50)
    private String modeloIa;

    @Size(max = 20, message = "Versão do modelo deve ter no máximo 20 caracteres")
    @Column(name = "VERSAO_MODELO", length = 20)
    private String versaoModelo;

    @Min(value = 0, message = "Tempo até inscrição deve ser positivo")
    @Column(name = "TEMPO_ATE_INSCRICAO_DIAS")
    private Integer tempoAteInscricaoDias;

    @PrePersist
    protected void onCreate() {
        if (dataRecomendacao == null) {
            dataRecomendacao = LocalDateTime.now();
        }
        if (visualizada == null) {
            visualizada = "0";
        }
        if (seInscreveu == null) {
            seInscreveu = "0";
        }
    }
}
