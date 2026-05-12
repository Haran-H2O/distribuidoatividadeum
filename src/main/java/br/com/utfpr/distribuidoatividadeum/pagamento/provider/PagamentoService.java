package br.com.utfpr.distribuidoatividadeum.pagamento.provider;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PagamentoService {

    public PagamentoResponse processar(PagamentoRequest request) {
        String transacaoId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        PagamentoResponse response = new PagamentoResponse(transacaoId, "APROVADO", request.getValor());
        System.out.println("PAGAMENTO APROVADO: pedido #" + request.getPedidoId() + " | transação " + transacaoId + " | R$ " + String.format("%.2f", request.getValor()));
        return response;
    }
}
