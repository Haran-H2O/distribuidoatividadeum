package br.com.utfpr.distribuidoatividadeum.entrega.provider;

public class EntregaResponse {
    private String codigoRastreio;
    private String status;
    private String previsaoEntrega;
    private Long pedidoId;

    public EntregaResponse() {}

    public EntregaResponse(String codigoRastreio, String status, String previsaoEntrega, Long pedidoId) {
        this.codigoRastreio = codigoRastreio;
        this.status = status;
        this.previsaoEntrega = previsaoEntrega;
        this.pedidoId = pedidoId;
    }

    public String getCodigoRastreio() { return codigoRastreio; }
    public void setCodigoRastreio(String codigoRastreio) { this.codigoRastreio = codigoRastreio; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPrevisaoEntrega() { return previsaoEntrega; }
    public void setPrevisaoEntrega(String previsaoEntrega) { this.previsaoEntrega = previsaoEntrega; }
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
}
