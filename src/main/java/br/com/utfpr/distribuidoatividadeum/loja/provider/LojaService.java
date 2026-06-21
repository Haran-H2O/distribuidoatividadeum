package br.com.utfpr.distribuidoatividadeum.loja.provider;

import br.com.utfpr.distribuidoatividadeum.cep.provider.Endereco;
import br.com.utfpr.distribuidoatividadeum.mensagens.EmailMessage;
import br.com.utfpr.distribuidoatividadeum.mensagens.PedidoMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.Unirest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LojaService {

    private static final String BASE = "http://localhost:8080/api";
    private final AtomicLong pedidoCounter = new AtomicLong(1000);
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Endereco consultarCep(String cep) {
        System.out.println("[→ FILA] fila.cep | consultando CEP " + cep + " via RPC");
        Object result = rabbitTemplate.convertSendAndReceive("fila.cep", cep);
        if (result == null) return null;
        return mapper.convertValue(result, Endereco.class);
    }

    public CompraResponse realizarCompra(CompraRequest request) {
        Long pedidoId = pedidoCounter.incrementAndGet();
        try {
            String produtoBody = Unirest.get(BASE + "/produtos/{id}")
                .routeParam("id", request.getProdutoId())
                .asString().getBody();
            JsonNode produtoJson = mapper.readTree(produtoBody);

            if (produtoJson.has("erro")) {
                throw new RuntimeException("Produto não encontrado: " + request.getProdutoId());
            }

            String nomeProduto = produtoJson.get("nome").asText();
            double preco = produtoJson.get("preco").asDouble();
            double valorTotal = preco * request.getQuantidade();

            Endereco endereco = consultarCep(request.getCep());
            String enderecoStr = endereco != null
                ? endereco.getLogradouro() + ", " + endereco.getBairro() + " - " + endereco.getLocalidade() + "/" + endereco.getUf()
                : "CEP " + request.getCep() + " (não localizado)";

            System.out.println("[→ FILA] fila.email | confirmação de compra para " + request.getEmail());
            rabbitTemplate.convertAndSend("fila.email", new EmailMessage(
                "CONFIRMACAO_COMPRA",
                request.getEmail(),
                "Pedido #" + pedidoId + " confirmado: " + nomeProduto + " x" + request.getQuantidade()
            ));

            double valorPagamento = Boolean.TRUE.equals(request.getSimularErro()) ? -valorTotal : valorTotal;
            System.out.println("[→ FILA] fila.pagamento | pedido #" + pedidoId + " | R$ " + String.format("%.2f", valorPagamento));
            rabbitTemplate.convertAndSend("fila.pagamento", new PedidoMessage(
                pedidoId,
                request.getProdutoId(),
                request.getQuantidade(),
                valorPagamento,
                request.getEmail(),
                enderecoStr,
                request.getSimularErro()
            ));

            System.out.println("COMPRA INICIADA: pedido #" + pedidoId + " | " + nomeProduto + " | R$ " + String.format("%.2f", valorTotal));

            CompraResponse response = new CompraResponse();
            response.setPedidoId(pedidoId);
            response.setStatus("PROCESSANDO");
            response.setProduto(nomeProduto);
            response.setQuantidade(request.getQuantidade());
            response.setValorTotal(valorTotal);
            response.setEndereco(enderecoStr);
            response.setMensagem("Pedido recebido. O pagamento e demais etapas estão sendo processados via fila.");
            return response;

        } catch (Exception e) {
            System.out.println("ERRO no fluxo de compra pedido #" + pedidoId + ": " + e.getMessage());
            CompraResponse response = new CompraResponse();
            response.setPedidoId(pedidoId);
            response.setStatus("ERRO");
            response.setMensagem(e.getMessage());
            return response;
        }
    }

    @RabbitListener(queues = "fila.pagamento.dlq")
    public void processarDlq(PedidoMessage pedido) {
        System.out.println("[← FILA] fila.pagamento.dlq | pedido #" + pedido.getPedidoId() + " rejeitado");
        String msg = "ERRO PAGAMENTO DLQ: pedido #" + pedido.getPedidoId() + " | valor: " + pedido.getValor();
        System.out.println(msg);
        try {
            File dir = new File("logs");
            dir.mkdirs();
            try (FileWriter fw = new FileWriter("logs/pagamento-errors.log", true)) {
                fw.write(LocalDateTime.now() + " | " + msg + "\n");
            }
            System.out.println("LOG DLQ: gravado em logs/pagamento-errors.log");
        } catch (Exception e) {
            System.out.println("ERRO ao gravar log DLQ: " + e.getMessage());
        }
    }
}
