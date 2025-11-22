package br.com.fiap.skillbridge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_INSCRICAO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_inscricao")
    @SequenceGenerator(name = "seq_inscricao", sequenceName = "SEQ_INSCRICAO", allocationSize = 1)
    @Column(name = "ID_INSCRICAO")
    private Long id;

    @NotNull(message = "Usuário é obrigatório")
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @NotNull(message = "Curso é obrigatório")
    @ManyToOne
    @JoinColumn(name = "ID_CURSO", nullable = false)
    private Curso curso;

    @Column(name = "DATA_INSCRICAO", insertable = false)
    private LocalDateTime dataInscricao;

    @DecimalMin(value = "0.0", message = "Progresso deve ser positivo")
    @DecimalMax(value = "100.0", message = "Progresso deve ser no máximo 100")
    @Column(name = "PROGRESSO", precision = 5, scale = 2, insertable = false)
    private BigDecimal progresso;

    @Size(max = 20, message = "Status deve ter no máximo 20 caracteres")
    @Column(name = "STATUS", length = 20, insertable = false)
    private String status;

    @DecimalMin(value = "0.0", message = "Tempo gasto deve ser positivo")
    @Column(name = "TEMPO_GASTO_HORAS", precision = 8, scale = 2, insertable = false)
    private BigDecimal tempoGastoHoras;

    @Column(name = "DATA_CONCLUSAO")
    private LocalDateTime dataConclusao;

    @DecimalMin(value = "0.0", message = "Nota deve ser positiva")
    @DecimalMax(value = "10.0", message = "Nota deve ser no máximo 10")
    @Column(name = "NOTA_AVALIACAO", precision = 3, scale = 2)
    private BigDecimal notaAvaliacao;

    @PrePersist
    protected void onCreate() {
        if (dataInscricao == null) {
            dataInscricao = LocalDateTime.now();
        }
        if (progresso == null) {
            progresso = BigDecimal.ZERO;
        }
        if (status == null) {
            status = "EM_ANDAMENTO";
        }
        if (tempoGastoHoras == null) {
            tempoGastoHoras = BigDecimal.ZERO;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        // Se progresso = 100, marca como concluído
        if (progresso != null && progresso.compareTo(new BigDecimal("100")) >= 0 && dataConclusao == null) {
            dataConclusao = LocalDateTime.now();
            status = "CONCLUIDO";
        }
    }
}
