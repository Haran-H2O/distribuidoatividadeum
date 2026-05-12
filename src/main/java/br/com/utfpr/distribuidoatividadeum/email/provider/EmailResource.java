package br.com.utfpr.distribuidoatividadeum.email.provider;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Path("/email")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmailResource {

    @Autowired
    private EmailService emailService;

    @POST
    @Path("/enviar")
    public Response enviar(EmailRequest request) {
        emailService.enviar(request);
        return Response.ok(Map.of("status", "ENVIADO", "destinatario", request.getDestinatario())).build();
    }
}
