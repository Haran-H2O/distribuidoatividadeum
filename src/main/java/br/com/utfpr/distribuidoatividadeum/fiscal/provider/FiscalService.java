package br.com.utfpr.distribuidoatividadeum.fiscal.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.Unirest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class FiscalService {

    private static final String BASE = "http://localhost:8080/api";
    private final ObjectMapper mapper = new ObjectMapper();

    public FiscalResponse emitir(FiscalRequest request) {
        try {
            String baixarBody = mapper.writeValueAsString(Map.of("quantidade", request.getQuantidade()));
            String baixarResp = Unirest.post(BASE + "/produtos/{id}/baixar-estoque")
                .routeParam("id", request.getProdutoId().toString())
                .header("Content-Type", "application/json")
                .body(baixarBody)
                .asString()
                .getBody();

            JsonNode baixarJson = mapper.readTree(baixarResp);
            if (!baixarJson.path("sucesso").asBoolean(false)) {
                System.out.println("ERRO: Falha ao baixar estoque para emissão da NF — pedido #" + request.getPedidoId());
                return null;
            }

            String chaveNF = "NF-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
            FiscalResponse response = new FiscalResponse(chaveNF, "EMITIDA", request.getPedidoId(), request.getValorTotal());
            System.out.println("NOTA FISCAL EMITIDA: " + chaveNF + " | pedido #" + request.getPedidoId() + " | R$ " + String.format("%.2f", request.getValorTotal()));
            return response;
        } catch (Exception e) {
            System.out.println("ERRO ao emitir nota fiscal: " + e.getMessage());
            return null;
        }
    }
}
