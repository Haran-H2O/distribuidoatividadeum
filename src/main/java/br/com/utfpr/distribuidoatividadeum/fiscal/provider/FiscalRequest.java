package br.com.utfpr.distribuidoatividadeum.fiscal.provider;

public class FiscalRequest {
    private Long pedidoId;
    private Long produtoId;
    private Integer quantidade;
    private Double valorTotal;
    private String email;

    public FiscalRequest() {}

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
