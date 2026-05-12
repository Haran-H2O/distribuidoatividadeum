package br.com.utfpr.distribuidoatividadeum.loja.provider;

public class CompraRequest {
    private Long produtoId;
    private Integer quantidade;
    private String cep;
    private String email;
    private String cartao;

    public CompraRequest() {}

    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCartao() { return cartao; }
    public void setCartao(String cartao) { this.cartao = cartao; }
}
