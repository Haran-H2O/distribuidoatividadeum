package br.com.utfpr.distribuidoatividadeum.mensagens;

public class FiscalMessage {
    private Long pedidoId;
    private String produtoId;
    private Integer quantidade;
    private Double valorTotal;
    private String email;

    public FiscalMessage() {}

    public FiscalMessage(Long pedidoId, String produtoId, Integer quantidade, Double valorTotal, String email) {
        this.pedidoId = pedidoId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.email = email;
    }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public String getProdutoId() { return produtoId; }
    public void setProdutoId(String produtoId) { this.produtoId = produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
