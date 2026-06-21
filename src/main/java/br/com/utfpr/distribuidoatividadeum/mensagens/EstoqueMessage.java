package br.com.utfpr.distribuidoatividadeum.mensagens;

public class EstoqueMessage {
    private String produtoId;
    private Integer quantidade;

    public EstoqueMessage() {}

    public EstoqueMessage(String produtoId, Integer quantidade) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public String getProdutoId() { return produtoId; }
    public void setProdutoId(String produtoId) { this.produtoId = produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}
