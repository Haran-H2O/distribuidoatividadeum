package br.com.utfpr.distribuidoatividadeum.pagamento.provider;

public class PagamentoResponse {
    private String transacaoId;
    private String status;
    private Double valor;

    public PagamentoResponse() {}

    public PagamentoResponse(String transacaoId, String status, Double valor) {
        this.transacaoId = transacaoId;
        this.status = status;
        this.valor = valor;
    }

    public String getTransacaoId() { return transacaoId; }
    public void setTransacaoId(String transacaoId) { this.transacaoId = transacaoId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
}
