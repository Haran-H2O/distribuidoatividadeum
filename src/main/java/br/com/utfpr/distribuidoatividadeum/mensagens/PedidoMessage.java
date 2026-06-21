package br.com.utfpr.distribuidoatividadeum.mensagens;

public class PedidoMessage {
    private Long pedidoId;
    private String produtoId;
    private Integer quantidade;
    private Double valor;
    private String email;
    private String endereco;
    private Boolean simularErro;

    public PedidoMessage() {}

    public PedidoMessage(Long pedidoId, String produtoId, Integer quantidade, Double valor, String email, String endereco, Boolean simularErro) {
        this.pedidoId = pedidoId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.valor = valor;
        this.email = email;
        this.endereco = endereco;
        this.simularErro = simularErro;
    }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public String getProdutoId() { return produtoId; }
    public void setProdutoId(String produtoId) { this.produtoId = produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public Boolean getSimularErro() { return simularErro; }
    public void setSimularErro(Boolean simularErro) { this.simularErro = simularErro; }
}
