package br.com.utfpr.distribuidoatividadeum.email.provider;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void enviar(EmailRequest request) {
        System.out.println("EMAIL ENVIADO: [" + request.getTipo() + "] → " + request.getDestinatario());
    }
}
