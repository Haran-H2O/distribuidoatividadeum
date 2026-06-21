package br.com.utfpr.distribuidoatividadeum.produtos.provider;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutosResource {

    @Autowired
    private ProdutosService produtosService;

    @GET
    public Response listar() {
        return Response.ok(produtosService.listarTodos()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") String id) {
        Optional<Produto> produto = produtosService.buscarPorId(id);
        if (produto.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erro", "Produto não encontrado"))
                .build();
        }
        return Response.ok(produto.get()).build();
    }
}
