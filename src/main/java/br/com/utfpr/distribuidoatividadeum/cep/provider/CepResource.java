package br.com.utfpr.distribuidoatividadeum.cep.provider;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Path("/cep")
@Produces(MediaType.APPLICATION_JSON)
public class CepResource {

    @Autowired
    private CepService cepService;

    @GET
    @Path("/{cep}")
    public Response consultar(@PathParam("cep") String cep) {
        Endereco endereco = cepService.consultar(cep);
        if (endereco == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erro", "CEP não encontrado"))
                .build();
        }
        return Response.ok(endereco).build();
    }
}
