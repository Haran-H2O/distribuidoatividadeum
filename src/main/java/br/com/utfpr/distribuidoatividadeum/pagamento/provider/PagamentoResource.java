package br.com.utfpr.distribuidoatividadeum.pagamento.provider;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/pagamento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PagamentoResource {

    @Autowired
    private PagamentoService pagamentoService;

    @POST
    @Path("/processar")
    public Response processar(PagamentoRequest request) {
        PagamentoResponse response = pagamentoService.processar(request);
        return Response.ok(response).build();
    }
}
