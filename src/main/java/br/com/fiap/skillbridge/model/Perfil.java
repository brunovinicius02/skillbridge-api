package br.com.fiap.skillbridge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PERFIL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Perfil {

    @Id
    @Column(name = "ID_USUARIO")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @Size(max = 100, message = "Objetivo de carreira deve ter no máximo 100 caracteres")
    @Column(name = "OBJETIVO_CARREIRA", length = 100)
    private String objetivoCarreira;

    @Size(max = 20, message = "Nível de experiência deve ter no máximo 20 caracteres")
    @Column(name = "NIVEL_EXPERIENCIA", length = 20, insertable = false)
    private String nivelExperiencia;

    @DecimalMin(value = "0.0", message = "Tempo disponível deve ser positivo")
    @Column(name = "TEMPO_DISPONIVEL_SEMANAL", precision = 5, scale = 2)
    private BigDecimal tempoDisponivelSemanal;

    @Min(value = 0, message = "Idade deve ser positiva")
    @Column(name = "IDADE")
    private Integer idade;

    @Size(max = 50, message = "Escolaridade deve ter no máximo 50 caracteres")
    @Column(name = "ESCOLARIDADE", length = 50)
    private String escolaridade;

    @Min(value = 0, message = "Anos de experiência deve ser positivo")
    @Column(name = "ANOS_EXPERIENCIA_TOTAL", insertable = false)
    private Integer anosExperienciaTotal;

    @Lob
    @Column(name = "BIOGRAFIA")
    private String biografia;

    @Column(name = "DATA_ATUALIZACAO", insertable = false)
    private LocalDateTime dataAtualizacao;

    @Size(max = 100, message = "Área de atuação deve ter no máximo 100 caracteres")
    @Column(name = "AREA_ATUACAO", length = 100)
    private String areaAtuacao;

    @Size(max = 100, message = "Cargo atual deve ter no máximo 100 caracteres")
    @Column(name = "CARGO_ATUAL", length = 100)
    private String cargoAtual;

    @Size(max = 200, message = "Objetivo profissional deve ter no máximo 200 caracteres")
    @Column(name = "OBJETIVO_PROFISSIONAL", length = 200)
    private String objetivoProfissional;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
        if (nivelExperiencia == null) {
            nivelExperiencia = "JUNIOR";
        }
        if (anosExperienciaTotal == null) {
            anosExperienciaTotal = 0;
        }
    }
}
