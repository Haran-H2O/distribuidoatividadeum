package br.com.utfpr.distribuidoatividadeum.fiscal.provider;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Path("/fiscal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FiscalResource {

    @Autowired
    private FiscalService fiscalService;

    @POST
    @Path("/emitir")
    public Response emitir(FiscalRequest request) {
        FiscalResponse response = fiscalService.emitir(request);
        if (response == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("erro", "Falha ao emitir nota fiscal"))
                .build();
        }
        return Response.ok(response).build();
    }
}
