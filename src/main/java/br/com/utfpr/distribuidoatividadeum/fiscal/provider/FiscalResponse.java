package br.com.utfpr.distribuidoatividadeum.fiscal.provider;

public class FiscalResponse {
    private String chaveNF;
    private String status;
    private Long pedidoId;
    private Double valorTotal;

    public FiscalResponse() {}

    public FiscalResponse(String chaveNF, String status, Long pedidoId, Double valorTotal) {
        this.chaveNF = chaveNF;
        this.status = status;
        this.pedidoId = pedidoId;
        this.valorTotal = valorTotal;
    }

    public String getChaveNF() { return chaveNF; }
    public void setChaveNF(String chaveNF) { this.chaveNF = chaveNF; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
}
