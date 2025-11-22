package br.com.fiap.skillbridge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_CURSO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_curso")
    @SequenceGenerator(name = "seq_curso", sequenceName = "SEQ_CURSO", allocationSize = 1)
    @Column(name = "ID_CURSO")
    private Long id;

    @Size(max = 200, message = "Nome deve ter no máximo 200 caracteres")
    @Column(name = "NOME", length = 200)
    private String nome;

    @Lob
    @Column(name = "DESCRICAO")
    private String descricao;

    @NotBlank(message = "Área é obrigatória")
    @Size(max = 50, message = "Área deve ter no máximo 50 caracteres")
    @Column(name = "AREA", nullable = false, length = 50)
    private String area;

    @NotBlank(message = "Nível é obrigatório")
    @Size(max = 20, message = "Nível deve ter no máximo 20 caracteres")
    @Column(name = "NIVEL", nullable = false, length = 20)
    private String nivel;

    @NotNull(message = "Carga horária é obrigatória")
    @DecimalMin(value = "0.0", message = "Carga horária deve ser positiva")
    @Column(name = "CARGA_HORARIA", nullable = false, precision = 6, scale = 2)
    private BigDecimal cargaHoraria;

    @DecimalMin(value = "0.0", message = "Avaliação média deve ser positiva")
    @DecimalMax(value = "5.0", message = "Avaliação média deve ser no máximo 5.0")
    @Column(name = "AVALIACAO_MEDIA", precision = 3, scale = 2, insertable = false)
    private BigDecimal avaliacaoMedia;

    @DecimalMin(value = "0.0", message = "Taxa de conclusão deve ser positiva")
    @DecimalMax(value = "100.0", message = "Taxa de conclusão deve ser no máximo 100")
    @Column(name = "TAXA_CONCLUSAO_MEDIA", precision = 5, scale = 2, insertable = false)
    private BigDecimal taxaConclusaoMedia;

    @Min(value = 0, message = "Popularidade deve ser positiva")
    @Column(name = "POPULARIDADE_SCORE", insertable = false)
    private Integer popularidadeScore;

    @Size(max = 500, message = "URL externo deve ter no máximo 500 caracteres")
    @Column(name = "URL_EXTERNO", length = 500)
    private String urlExterno;

    @Size(max = 500, message = "URL da imagem deve ter no máximo 500 caracteres")
    @Column(name = "IMAGEM_URL", length = 500)
    private String imagemUrl;

    @Column(name = "ATIVO", length = 1, insertable = false)
    private String ativo;

    @Column(name = "DATA_CRIACAO", insertable = false)
    private LocalDateTime dataCriacao;

    @Min(value = 0, message = "Total de inscritos deve ser positivo")
    @Column(name = "TOTAL_INSCRITOS", insertable = false)
    private Integer totalInscritos;

    @PrePersist
    protected void onCreate() {
        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now();
        }
        if (ativo == null) {
            ativo = "1";
        }
        if (avaliacaoMedia == null) {
            avaliacaoMedia = BigDecimal.ZERO;
        }
        if (taxaConclusaoMedia == null) {
            taxaConclusaoMedia = BigDecimal.ZERO;
        }
        if (popularidadeScore == null) {
            popularidadeScore = 0;
        }
        if (totalInscritos == null) {
            totalInscritos = 0;
        }
    }
}
