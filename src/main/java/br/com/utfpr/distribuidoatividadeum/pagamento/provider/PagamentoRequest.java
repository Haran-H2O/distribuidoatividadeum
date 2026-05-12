package br.com.utfpr.distribuidoatividadeum.pagamento.provider;

public class PagamentoRequest {
    private Long pedidoId;
    private Double valor;
    private String cartao;
    private String email;

    public PagamentoRequest() {}

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    public String getCartao() { return cartao; }
    public void setCartao(String cartao) { this.cartao = cartao; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
