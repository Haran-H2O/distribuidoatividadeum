package br.com.utfpr.distribuidoatividadeum.entrega.provider;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class EntregaService {

    public EntregaResponse disponibilizar(EntregaRequest request) {
        String codigo = "BR" + UUID.randomUUID().toString().replace("-", "").substring(0, 9).toUpperCase();
        String previsao = LocalDate.now().plusDays(7).toString();
        EntregaResponse response = new EntregaResponse(codigo, "AGUARDANDO_COLETA", previsao, request.getPedidoId());
        System.out.println("ENTREGA REGISTRADA: " + codigo + " | pedido #" + request.getPedidoId() + " | previsão " + previsao);
        return response;
    }
}
