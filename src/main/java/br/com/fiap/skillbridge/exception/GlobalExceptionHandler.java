package br.com.fiap.skillbridge.exception;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());

        if (exception instanceof NotFoundException) {
            error.put("status", 404);
            error.put("error", "Not Found");
            error.put("message", exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }

        if (exception instanceof BadRequestException) {
            error.put("status", 400);
            error.put("error", "Bad Request");
            error.put("message", exception.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }

        // Exception genérica
        error.put("status", 500);
        error.put("error", "Internal Server Error");
        error.put("message", exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
    }
}
