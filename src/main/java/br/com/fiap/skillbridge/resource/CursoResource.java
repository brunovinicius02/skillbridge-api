package br.com.fiap.skillbridge.resource;

import br.com.fiap.skillbridge.model.Curso;
import br.com.fiap.skillbridge.service.CursoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/cursos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Cursos", description = "Catálogo de cursos")
public class CursoResource {

    @Inject
    CursoService cursoService;

    @GET
    @Operation(summary = "Listar todos os cursos ativos")
    public List<Curso> listar() {
        return cursoService.listarTodos();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar curso por ID")
    public Curso buscarPorId(@PathParam("id") Long id) {
        return cursoService.buscarPorId(id);
    }

    @GET
    @Path("/area/{area}")
    @Operation(summary = "Buscar cursos por área")
    public List<Curso> buscarPorArea(@PathParam("area") String area) {
        return cursoService.buscarPorArea(area);
    }

    @GET
    @Path("/nivel/{nivel}")
    @Operation(summary = "Buscar cursos por nível")
    public List<Curso> buscarPorNivel(@PathParam("nivel") String nivel) {
        return cursoService.buscarPorNivel(nivel);
    }

    @GET
    @Path("/pesquisar")
    @Operation(summary = "Pesquisar cursos por nome")
    public List<Curso> pesquisar(@QueryParam("nome") String nome) {
        return cursoService.pesquisarPorNome(nome);
    }

    @POST
    @Operation(summary = "Criar novo curso")
    public Response criar(@Valid Curso curso) {
        Curso cursoCriado = cursoService.criar(curso);
        return Response.status(Response.Status.CREATED).entity(cursoCriado).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar curso")
    public Curso atualizar(@PathParam("id") Long id, @Valid Curso curso) {
        return cursoService.atualizar(id, curso);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Inativar curso")
    public Response deletar(@PathParam("id") Long id) {
        cursoService.deletar(id);
        return Response.noContent().build();
    }
}
