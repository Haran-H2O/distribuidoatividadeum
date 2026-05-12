package br.com.utfpr.distribuidoatividadeum.entrega.provider;

public class EntregaRequest {
    private Long pedidoId;
    private String enderecoEntrega;
    private Long produtoId;
    private Integer quantidade;
    private String email;

    public EntregaRequest() {}

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public String getEnderecoEntrega() { return enderecoEntrega; }
    public void setEnderecoEntrega(String enderecoEntrega) { this.enderecoEntrega = enderecoEntrega; }
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
