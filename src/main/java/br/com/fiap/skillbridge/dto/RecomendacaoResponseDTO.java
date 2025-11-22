package br.com.fiap.skillbridge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para resposta de recomendações da API de IA
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecomendacaoResponseDTO {
    
    @JsonProperty("usuario_id")
    private Long usuarioId;
    
    @JsonProperty("timestamp")
    private String timestamp;
    
    @JsonProperty("total_recomendacoes")
    private Integer totalRecomendacoes;
    
    @JsonProperty("recomendacoes")
    private List<RecomendacaoItemDTO> recomendacoes;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecomendacaoItemDTO {
        @JsonProperty("rank")
        private Integer rank;
        
        @JsonProperty("curso")
        private CursoRecomendadoDTO curso;
        
        @JsonProperty("score_relevancia")
        private BigDecimal scoreRelevancia;
        
        @JsonProperty("probabilidade_conclusao")
        private BigDecimal probabilidadeConclusao;
        
        @JsonProperty("motivo")
        private String motivo;
        
        @JsonProperty("modelo_ia")
        private String modeloIa;
        
        @JsonProperty("versao_modelo")
        private String versaoModelo;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CursoRecomendadoDTO {
        @JsonProperty("id_curso")
        private Long idCurso;
        
        @JsonProperty("nome")
        private String nome;
        
        @JsonProperty("descricao")
        private String descricao;
        
        @JsonProperty("area")
        private String area;
        
        @JsonProperty("nivel")
        private String nivel;
        
        @JsonProperty("carga_horaria")
        private BigDecimal cargaHoraria;
        
        @JsonProperty("avaliacao_media")
        private BigDecimal avaliacaoMedia;
        
        @JsonProperty("taxa_conclusao_media")
        private BigDecimal taxaConclusaoMedia;
        
        @JsonProperty("popularidade_score")
        private BigDecimal popularidadeScore;
    }
}
