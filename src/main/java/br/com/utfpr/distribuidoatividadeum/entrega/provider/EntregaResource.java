package br.com.utfpr.distribuidoatividadeum.entrega.provider;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/entrega")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EntregaResource {

    @Autowired
    private EntregaService entregaService;

    @POST
    @Path("/disponibilizar")
    public Response disponibilizar(EntregaRequest request) {
        EntregaResponse response = entregaService.disponibilizar(request);
        return Response.ok(response).build();
    }
}
