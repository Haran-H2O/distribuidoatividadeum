package br.com.utfpr.distribuidoatividadeum.pagamento.provider;

import br.com.utfpr.distribuidoatividadeum.mensagens.EmailMessage;
import br.com.utfpr.distribuidoatividadeum.mensagens.EntregaMessage;
import br.com.utfpr.distribuidoatividadeum.mensagens.FiscalMessage;
import br.com.utfpr.distribuidoatividadeum.mensagens.PedidoMessage;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PagamentoService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "fila.pagamento")
    public void processar(PedidoMessage pedido) {
        System.out.println("[← FILA] fila.pagamento | pedido #" + pedido.getPedidoId() + " | R$ " + String.format("%.2f", pedido.getValor()));

        if (pedido.getValor() < 0) {
            System.out.println("ERRO PAGAMENTO: valor negativo para pedido #" + pedido.getPedidoId() + " — enviando para DLQ");
            throw new AmqpRejectAndDontRequeueException("Valor de pagamento inválido: " + pedido.getValor());
        }

        String transacaoId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        System.out.println("PAGAMENTO APROVADO: pedido #" + pedido.getPedidoId() + " | transação " + transacaoId + " | R$ " + String.format("%.2f", pedido.getValor()));

        System.out.println("[→ FILA] fila.email | resultado do pagamento para " + pedido.getEmail());
        rabbitTemplate.convertAndSend("fila.email", new EmailMessage(
            "RESULTADO_PAGAMENTO",
            pedido.getEmail(),
            "Transação " + transacaoId + " — APROVADO"
        ));

        System.out.println("[→ FILA] fila.fiscal | pedido #" + pedido.getPedidoId() + " para emissão de NF");
        rabbitTemplate.convertAndSend("fila.fiscal", new FiscalMessage(
            pedido.getPedidoId(),
            pedido.getProdutoId(),
            pedido.getQuantidade(),
            pedido.getValor(),
            pedido.getEmail()
        ));

        System.out.println("[→ FILA] fila.entrega | pedido #" + pedido.getPedidoId() + " para logística");
        rabbitTemplate.convertAndSend("fila.entrega", new EntregaMessage(
            pedido.getPedidoId(),
            pedido.getProdutoId(),
            pedido.getQuantidade(),
            pedido.getEndereco(),
            pedido.getEmail()
        ));
    }
}
