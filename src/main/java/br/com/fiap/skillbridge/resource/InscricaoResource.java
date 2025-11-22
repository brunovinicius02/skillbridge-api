package br.com.fiap.skillbridge.resource;

import br.com.fiap.skillbridge.model.Inscricao;
import br.com.fiap.skillbridge.service.InscricaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Path("/api/inscricoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Inscrições", description = "Gerenciamento de inscrições em cursos")
public class InscricaoResource {

    @Inject
    InscricaoService inscricaoService;

    @GET
    @Path("/usuario/{usuarioId}")
    @Operation(summary = "Listar inscrições do usuário")
    public List<Inscricao> listarPorUsuario(@PathParam("usuarioId") Long usuarioId) {
        return inscricaoService.listarPorUsuario(usuarioId);
    }

    @GET
    @Path("/usuario/{usuarioId}/em-andamento")
    @Operation(summary = "Listar cursos em andamento do usuário")
    public List<Inscricao> listarEmAndamento(@PathParam("usuarioId") Long usuarioId) {
        return inscricaoService.listarEmAndamento(usuarioId);
    }

    @GET
    @Path("/usuario/{usuarioId}/concluidos")
    @Operation(summary = "Listar cursos concluídos do usuário")
    public List<Inscricao> listarConcluidos(@PathParam("usuarioId") Long usuarioId) {
        return inscricaoService.listarConcluidos(usuarioId);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar inscrição por ID")
    public Inscricao buscarPorId(@PathParam("id") Long id) {
        return inscricaoService.buscarPorId(id);
    }

    @POST
    @Operation(summary = "Criar nova inscrição")
    public Response criar(Map<String, Long> body) {
        Long usuarioId = body.get("usuarioId");
        Long cursoId = body.get("cursoId");
        Inscricao inscricao = inscricaoService.criar(usuarioId, cursoId);
        return Response.status(Response.Status.CREATED).entity(inscricao).build();
    }

    @PUT
    @Path("/{id}/progresso")
    @Operation(summary = "Atualizar progresso da inscrição")
    public Inscricao atualizarProgresso(@PathParam("id") Long id, Map<String, BigDecimal> body) {
        BigDecimal progresso = body.get("progresso");
        return inscricaoService.atualizarProgresso(id, progresso);
    }

    @PUT
    @Path("/{id}/concluir")
    @Operation(summary = "Marcar inscrição como concluída")
    public Inscricao concluir(@PathParam("id") Long id, Map<String, BigDecimal> body) {
        BigDecimal nota = body.get("nota");
        return inscricaoService.marcarComoConcluido(id, nota);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Cancelar inscrição")
    public Response deletar(@PathParam("id") Long id) {
        inscricaoService.deletar(id);
        return Response.noContent().build();
    }
}
