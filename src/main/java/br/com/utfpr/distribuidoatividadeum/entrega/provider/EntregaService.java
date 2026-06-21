package br.com.utfpr.distribuidoatividadeum.entrega.provider;

import br.com.utfpr.distribuidoatividadeum.mensagens.EmailMessage;
import br.com.utfpr.distribuidoatividadeum.mensagens.EntregaMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class EntregaService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "fila.entrega")
    public void disponibilizar(EntregaMessage msg) {
        System.out.println("[← FILA] fila.entrega | pedido #" + msg.getPedidoId() + " | preparando envio");

        String codigo = "BR" + UUID.randomUUID().toString().replace("-", "").substring(0, 9).toUpperCase();
        String previsao = LocalDate.now().plusDays(7).toString();
        System.out.println("ENTREGA REGISTRADA: " + codigo + " | pedido #" + msg.getPedidoId() + " | previsão " + previsao);

        System.out.println("[→ FILA] fila.email | dados de entrega " + codigo + " para " + msg.getEmail());
        rabbitTemplate.convertAndSend("fila.email", new EmailMessage(
            "DADOS_ENTREGA",
            msg.getEmail(),
            "Rastreio " + codigo + " | Previsão: " + previsao
        ));
    }
}
