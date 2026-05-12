package br.com.utfpr.distribuidoatividadeum.loja.provider;

import java.util.List;
import java.util.Map;

public class CompraResponse {
    private Long pedidoId;
    private String status;
    private String produto;
    private Integer quantidade;
    private Double valorTotal;
    private String endereco;
    private Map<String, Object> pagamento;
    private Map<String, Object> notaFiscal;
    private Map<String, Object> entrega;
    private List<String> etapas;

    public CompraResponse() {}

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getProduto() { return produto; }
    public void setProduto(String produto) { this.produto = produto; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public Map<String, Object> getPagamento() { return pagamento; }
    public void setPagamento(Map<String, Object> pagamento) { this.pagamento = pagamento; }
    public Map<String, Object> getNotaFiscal() { return notaFiscal; }
    public void setNotaFiscal(Map<String, Object> notaFiscal) { this.notaFiscal = notaFiscal; }
    public Map<String, Object> getEntrega() { return entrega; }
    public void setEntrega(Map<String, Object> entrega) { this.entrega = entrega; }
    public List<String> getEtapas() { return etapas; }
    public void setEtapas(List<String> etapas) { this.etapas = etapas; }
}
