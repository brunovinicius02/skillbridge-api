package br.com.fiap.skillbridge.resource;

import br.com.fiap.skillbridge.dto.PerfilDTO;
import br.com.fiap.skillbridge.service.PerfilService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/usuarios/{usuarioId}/perfil")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Perfil", description = "Gerenciamento de perfil profissional")
public class PerfilResource {

    @Inject
    PerfilService perfilService;

    @GET
    @Operation(summary = "Buscar perfil do usuário")
    public PerfilDTO buscar(@PathParam("usuarioId") Long usuarioId) {
        return perfilService.buscarPorUsuarioId(usuarioId);
    }

    @PUT
    @Operation(summary = "Atualizar perfil do usuário")
    public PerfilDTO atualizar(@PathParam("usuarioId") Long usuarioId, @Valid PerfilDTO dto) {
        return perfilService.criarOuAtualizar(usuarioId, dto);
    }

    @POST
    @Operation(summary = "Criar perfil do usuário")
    public PerfilDTO criar(@PathParam("usuarioId") Long usuarioId, @Valid PerfilDTO dto) {
        return perfilService.criarOuAtualizar(usuarioId, dto);
    }
}
