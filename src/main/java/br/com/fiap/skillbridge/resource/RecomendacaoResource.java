package br.com.fiap.skillbridge.resource;

import br.com.fiap.skillbridge.model.Recomendacao;
import br.com.fiap.skillbridge.service.RecomendacaoIAService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Map;

/**
 * Resource REST para gerenciar recomendações de cursos geradas por IA
 */
@Path("/api/recomendacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecomendacaoResource {

    private static final Logger LOG = Logger.getLogger(RecomendacaoResource.class);

    @Inject
    RecomendacaoIAService recomendacaoService;

    /**
     * Gera recomendações personalizadas para um usuário
     * 
     * POST /api/recomendacoes/gerar/{usuarioId}?topN=10
     */
    @POST
    @Path("/gerar/{usuarioId}")
    public Response gerarRecomendacoes(
            @PathParam("usuarioId") Long usuarioId,
            @QueryParam("topN") @DefaultValue("10") Integer topN) {
        
        LOG.infof("POST /api/recomendacoes/gerar/%d?topN=%d", usuarioId, topN);

        try {
            List<Recomendacao> recomendacoes = recomendacaoService.gerarRecomendacoes(usuarioId, topN);
            
            return Response.ok(Map.of(
                "message", "Recomendações geradas com sucesso",
                "total", recomendacoes.size(),
                "recomendacoes", recomendacoes
            )).build();

        } catch (IllegalArgumentException e) {
            LOG.error("Usuário não encontrado: " + usuarioId, e);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
                    
        } catch (Exception e) {
            LOG.error("Erro ao gerar recomendações", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", "Erro ao gerar recomendações: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Busca recomendações já geradas para um usuário
     * 
     * GET /api/recomendacoes/usuario/{usuarioId}
     * CORRIGIDO: Removido parâmetro limit
     */
    @GET
    @Path("/usuario/{usuarioId}")
    public Response buscarRecomendacoes(@PathParam("usuarioId") Long usuarioId) {
        
        LOG.infof("GET /api/recomendacoes/usuario/%d", usuarioId);

        try {
            List<Recomendacao> recomendacoes = recomendacaoService.buscarRecomendacoesUsuario(usuarioId);
            
            return Response.ok(Map.of(
                "total", recomendacoes.size(),
                "recomendacoes", recomendacoes
            )).build();

        } catch (Exception e) {
            LOG.error("Erro ao buscar recomendações", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", "Erro ao buscar recomendações: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Marca uma recomendação como visualizada
     * 
     * PUT /api/recomendacoes/{recomendacaoId}/visualizar
     */
    @PUT
    @Path("/{recomendacaoId}/visualizar")
    public Response marcarComoVisualizada(@PathParam("recomendacaoId") Long recomendacaoId) {
        LOG.infof("PUT /api/recomendacoes/%d/visualizar", recomendacaoId);

        try {
            recomendacaoService.marcarComoVisualizada(recomendacaoId);
            
            return Response.ok(Map.of(
                "message", "Recomendação marcada como visualizada"
            )).build();

        } catch (Exception e) {
            LOG.error("Erro ao marcar recomendação como visualizada", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        }
    }

    /**
     * Marca que o usuário se inscreveu no curso recomendado
     * 
     * PUT /api/recomendacoes/{recomendacaoId}/inscreveu
     */
    @PUT
    @Path("/{recomendacaoId}/inscreveu")
    public Response marcarInscricao(@PathParam("recomendacaoId") Long recomendacaoId) {
        LOG.infof("PUT /api/recomendacoes/%d/inscreveu", recomendacaoId);

        try {
            recomendacaoService.marcarInscricao(recomendacaoId, java.time.LocalDateTime.now());
            
            return Response.ok(Map.of(
                "message", "Inscrição registrada com sucesso"
            )).build();

        } catch (Exception e) {
            LOG.error("Erro ao registrar inscrição", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        }
    }
}
