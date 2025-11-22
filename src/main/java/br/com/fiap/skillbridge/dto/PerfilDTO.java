package br.com.fiap.skillbridge.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilDTO {

    @Size(max = 100, message = "Objetivo de carreira deve ter no máximo 100 caracteres")
    private String objetivoCarreira;

    @Size(max = 20, message = "Nível de experiência deve ter no máximo 20 caracteres")
    private String nivelExperiencia;

    @DecimalMin(value = "0.0", message = "Tempo disponível deve ser positivo")
    private BigDecimal tempoDisponivelSemanal;

    @Min(value = 0, message = "Idade deve ser positiva")
    private Integer idade;

    @Size(max = 50, message = "Escolaridade deve ter no máximo 50 caracteres")
    private String escolaridade;

    @Min(value = 0, message = "Anos de experiência deve ser positivo")
    private Integer anosExperienciaTotal;

    private String biografia;

    @Size(max = 100, message = "Área de atuação deve ter no máximo 100 caracteres")
    private String areaAtuacao;

    @Size(max = 100, message = "Cargo atual deve ter no máximo 100 caracteres")
    private String cargoAtual;

    @Size(max = 200, message = "Objetivo profissional deve ter no máximo 200 caracteres")
    private String objetivoProfissional;
}
