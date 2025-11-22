package br.com.fiap.skillbridge.resource;

import br.com.fiap.skillbridge.dto.LoginDTO;
import br.com.fiap.skillbridge.dto.UsuarioCreateDTO;
import br.com.fiap.skillbridge.dto.UsuarioResponseDTO;
import br.com.fiap.skillbridge.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Usuários", description = "Gerenciamento de usuários")
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @GET
    @Operation(summary = "Listar todos os usuários")
    public List<UsuarioResponseDTO> listar() {
        return usuarioService.listarTodos();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public UsuarioResponseDTO buscarPorId(@PathParam("id") Long id) {
        return usuarioService.buscarPorId(id);
    }

    @POST
    @Operation(summary = "Criar novo usuário")
    public Response criar(@Valid UsuarioCreateDTO dto) {
        UsuarioResponseDTO usuario = usuarioService.criar(dto);
        return Response.status(Response.Status.CREATED).entity(usuario).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar usuário")
    public UsuarioResponseDTO atualizar(@PathParam("id") Long id, @Valid UsuarioCreateDTO dto) {
        return usuarioService.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletar usuário")
    public Response deletar(@PathParam("id") Long id) {
        usuarioService.deletar(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/login")
    @Operation(summary = "Fazer login")
    public UsuarioResponseDTO login(@Valid LoginDTO dto) {
        return usuarioService.login(dto);
    }
}
