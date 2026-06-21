package br.com.utfpr.distribuidoatividadeum.loja.provider;

public class CompraRequest {
    private String produtoId;
    private Integer quantidade;
    private String cep;
    private String email;
    private String cartao;
    private Boolean simularErro;

    public CompraRequest() {}

    public String getProdutoId() { return produtoId; }
    public void setProdutoId(String produtoId) { this.produtoId = produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCartao() { return cartao; }
    public void setCartao(String cartao) { this.cartao = cartao; }
    public Boolean getSimularErro() { return simularErro; }
    public void setSimularErro(Boolean simularErro) { this.simularErro = simularErro; }
}
