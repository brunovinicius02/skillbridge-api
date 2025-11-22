package br.com.fiap.skillbridge.dto;

import br.com.fiap.skillbridge.model.Curso;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para requisição de recomendações à API de IA
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecomendacaoRequestDTO {
    
    @JsonProperty("usuario_id")
    private Long usuarioId;
    
    @JsonProperty("perfil")
    private PerfilRecomendacaoDTO perfil;
    
    @JsonProperty("cursos")
    private List<Curso> cursos;
    
    @JsonProperty("top_n")
    private Integer topN = 10; // Padrão: top 10 recomendações
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PerfilRecomendacaoDTO {
        @JsonProperty("nivel_experiencia")
        private String nivelExperiencia; // JUNIOR, INTERMEDIARIO, SENIOR
        
        @JsonProperty("tempo_disponivel_semanal")
        private BigDecimal tempoDisponivelSemanal;
        
        @JsonProperty("idade")
        private Integer idade;
        
        @JsonProperty("anos_experiencia_total")
        private Integer anosExperienciaTotal;
        
        @JsonProperty("objetivo_carreira")
        private String objetivoCarreira;
    }
}
