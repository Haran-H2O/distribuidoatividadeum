package br.com.utfpr.distribuidoatividadeum.email.provider;

import br.com.utfpr.distribuidoatividadeum.mensagens.EmailMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @RabbitListener(queues = "fila.email")
    public void enviar(EmailMessage msg) {
        System.out.println("[← FILA] fila.email | [" + msg.getTipo() + "] para " + msg.getDestinatario());
        System.out.println("EMAIL ENVIADO: [" + msg.getTipo() + "] → " + msg.getDestinatario());
    }
}
