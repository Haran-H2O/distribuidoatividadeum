package br.com.utfpr.distribuidoatividadeum.loja.provider;

import br.com.utfpr.distribuidoatividadeum.cep.provider.Endereco;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Path("/loja")
@Produces(MediaType.APPLICATION_JSON)
public class LojaResource {

    private static final String BASE = "http://localhost:8080/api";

    @Autowired
    private LojaService lojaService;

    @GET
    @Path("/produtos")
    public Response listarProdutos() {
        String body = Unirest.get(BASE + "/produtos").asString().getBody();
        return Response.ok(body).build();
    }

    @GET
    @Path("/cep/{cep}")
    public Response consultarCep(@PathParam("cep") String cep) {
        Endereco endereco = lojaService.consultarCep(cep);
        if (endereco == null) {
            return Response.ok(Map.of("erro", true)).build();
        }
        return Response.ok(endereco).build();
    }

    @POST
    @Path("/compra")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response realizarCompra(CompraRequest request) {
        CompraResponse response = lojaService.realizarCompra(request);
        return Response.ok(response).build();
    }
}
