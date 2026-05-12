package br.com.utfpr.distribuidoatividadeum.loja.provider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LojaService {

    private static final String BASE = "http://localhost:8080/api";
    private final AtomicLong pedidoCounter = new AtomicLong(1000);
    private final ObjectMapper mapper = new ObjectMapper();

    public CompraResponse realizarCompra(CompraRequest request) {
        Long pedidoId = pedidoCounter.incrementAndGet();
        List<String> etapas = new ArrayList<>();

        try {
            String produtoBody = Unirest.get(BASE + "/produtos/{id}")
                .routeParam("id", request.getProdutoId().toString())
                .asString().getBody();
            JsonNode produtoJson = mapper.readTree(produtoBody);

            if (produtoJson.has("erro")) {
                throw new RuntimeException("Produto #" + request.getProdutoId() + " não encontrado");
            }

            String nomeProduto = produtoJson.get("nome").asText();
            double preco = produtoJson.get("preco").asDouble();
            double valorTotal = preco * request.getQuantidade();

            String cepBody = Unirest.get(BASE + "/cep/{cep}")
                .routeParam("cep", request.getCep())
                .asString().getBody();
            JsonNode cepJson = mapper.readTree(cepBody);

            String endereco;
            if (cepJson.has("erro")) {
                endereco = "CEP " + request.getCep() + " (não localizado)";
            } else {
                endereco = cepJson.path("logradouro").asText("") + ", "
                    + cepJson.path("bairro").asText("") + " - "
                    + cepJson.path("localidade").asText("") + "/"
                    + cepJson.path("uf").asText("");
            }

            Map<String, Object> emailConfirmacao = new HashMap<>();
            emailConfirmacao.put("tipo", "CONFIRMACAO_COMPRA");
            emailConfirmacao.put("destinatario", request.getEmail());
            emailConfirmacao.put("conteudo", "Pedido #" + pedidoId + " confirmado: " + nomeProduto + " x" + request.getQuantidade());
            Unirest.post(BASE + "/email/enviar")
                .header("Content-Type", "application/json")
                .body(mapper.writeValueAsString(emailConfirmacao))
                .asString();
            etapas.add("E-mail de confirmação de compra enviado");

            Map<String, Object> pagamentoReq = new HashMap<>();
            pagamentoReq.put("pedidoId", pedidoId);
            pagamentoReq.put("valor", valorTotal);
            pagamentoReq.put("cartao", request.getCartao());
            pagamentoReq.put("email", request.getEmail());
            String pagamentoBody = Unirest.post(BASE + "/pagamento/processar")
                .header("Content-Type", "application/json")
                .body(mapper.writeValueAsString(pagamentoReq))
                .asString().getBody();
            JsonNode pagamentoJson = mapper.readTree(pagamentoBody);
            Map<String, Object> pagamentoMap = mapper.convertValue(pagamentoJson, new TypeReference<>() {});
            etapas.add("Pagamento " + pagamentoJson.path("status").asText());

            Map<String, Object> emailPagamento = new HashMap<>();
            emailPagamento.put("tipo", "RESULTADO_PAGAMENTO");
            emailPagamento.put("destinatario", request.getEmail());
            emailPagamento.put("conteudo", "Transação " + pagamentoJson.path("transacaoId").asText() + " — " + pagamentoJson.path("status").asText());
            Unirest.post(BASE + "/email/enviar")
                .header("Content-Type", "application/json")
                .body(mapper.writeValueAsString(emailPagamento))
                .asString();
            etapas.add("E-mail com resultado do pagamento enviado");

            Map<String, Object> fiscalReq = new HashMap<>();
            fiscalReq.put("pedidoId", pedidoId);
            fiscalReq.put("produtoId", request.getProdutoId());
            fiscalReq.put("quantidade", request.getQuantidade());
            fiscalReq.put("valorTotal", valorTotal);
            fiscalReq.put("email", request.getEmail());
            HttpResponse<String> fiscalHttpResp = Unirest.post(BASE + "/fiscal/emitir")
                .header("Content-Type", "application/json")
                .body(mapper.writeValueAsString(fiscalReq))
                .asString();
            if (fiscalHttpResp.getStatus() != 200) {
                throw new RuntimeException("Falha ao emitir nota fiscal: estoque insuficiente");
            }
            JsonNode fiscalJson = mapper.readTree(fiscalHttpResp.getBody());
            Map<String, Object> fiscalMap = mapper.convertValue(fiscalJson, new TypeReference<>() {});
            etapas.add("Nota fiscal " + fiscalJson.path("chaveNF").asText() + " emitida");

            Map<String, Object> emailNF = new HashMap<>();
            emailNF.put("tipo", "NOTA_FISCAL");
            emailNF.put("destinatario", request.getEmail());
            emailNF.put("conteudo", "NF " + fiscalJson.path("chaveNF").asText() + " disponível");
            Unirest.post(BASE + "/email/enviar")
                .header("Content-Type", "application/json")
                .body(mapper.writeValueAsString(emailNF))
                .asString();
            etapas.add("E-mail com nota fiscal enviado");

            Map<String, Object> entregaReq = new HashMap<>();
            entregaReq.put("pedidoId", pedidoId);
            entregaReq.put("enderecoEntrega", endereco);
            entregaReq.put("produtoId", request.getProdutoId());
            entregaReq.put("quantidade", request.getQuantidade());
            entregaReq.put("email", request.getEmail());
            String entregaBody = Unirest.post(BASE + "/entrega/disponibilizar")
                .header("Content-Type", "application/json")
                .body(mapper.writeValueAsString(entregaReq))
                .asString().getBody();
            JsonNode entregaJson = mapper.readTree(entregaBody);
            Map<String, Object> entregaMap = mapper.convertValue(entregaJson, new TypeReference<>() {});
            etapas.add("Entrega disponibilizada — rastreio: " + entregaJson.path("codigoRastreio").asText());

            Map<String, Object> emailEntrega = new HashMap<>();
            emailEntrega.put("tipo", "DADOS_ENTREGA");
            emailEntrega.put("destinatario", request.getEmail());
            emailEntrega.put("conteudo", "Rastreio " + entregaJson.path("codigoRastreio").asText() + " | Previsão: " + entregaJson.path("previsaoEntrega").asText());
            Unirest.post(BASE + "/email/enviar")
                .header("Content-Type", "application/json")
                .body(mapper.writeValueAsString(emailEntrega))
                .asString();
            etapas.add("E-mail com dados de entrega enviado");

            System.out.println("COMPRA CONCLUIDA: pedido #" + pedidoId + " | " + nomeProduto + " | R$ " + String.format("%.2f", valorTotal));

            CompraResponse response = new CompraResponse();
            response.setPedidoId(pedidoId);
            response.setStatus("CONCLUIDO");
            response.setProduto(nomeProduto);
            response.setQuantidade(request.getQuantidade());
            response.setValorTotal(valorTotal);
            response.setEndereco(endereco);
            response.setPagamento(pagamentoMap);
            response.setNotaFiscal(fiscalMap);
            response.setEntrega(entregaMap);
            response.setEtapas(etapas);
            return response;

        } catch (Exception e) {
            System.out.println("ERRO no fluxo de compra pedido #" + pedidoId + ": " + e.getMessage());
            CompraResponse response = new CompraResponse();
            response.setPedidoId(pedidoId);
            response.setStatus("ERRO");
            response.setEtapas(etapas);
            Map<String, Object> erroMap = new HashMap<>();
            erroMap.put("mensagem", e.getMessage());
            response.setNotaFiscal(erroMap);
            return response;
        }
    }
}
