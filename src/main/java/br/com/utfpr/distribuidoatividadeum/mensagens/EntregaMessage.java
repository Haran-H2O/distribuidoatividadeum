package br.com.utfpr.distribuidoatividadeum.mensagens;

public class EntregaMessage {
    private Long pedidoId;
    private String produtoId;
    private Integer quantidade;
    private String endereco;
    private String email;

    public EntregaMessage() {}

    public EntregaMessage(Long pedidoId, String produtoId, Integer quantidade, String endereco, String email) {
        this.pedidoId = pedidoId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.endereco = endereco;
        this.email = email;
    }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public String getProdutoId() { return produtoId; }
    public void setProdutoId(String produtoId) { this.produtoId = produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
