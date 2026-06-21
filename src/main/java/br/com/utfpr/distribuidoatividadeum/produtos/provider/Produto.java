package br.com.utfpr.distribuidoatividadeum.produtos.provider;

public class Produto {
    private String id;
    private String nome;
    private String urlImagem;
    private Double preco;
    private Integer quantidadeEmEstoque;

    public Produto() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getUrlImagem() { return urlImagem; }
    public void setUrlImagem(String urlImagem) { this.urlImagem = urlImagem; }
    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }
    public Integer getQuantidadeEmEstoque() { return quantidadeEmEstoque; }
    public void setQuantidadeEmEstoque(Integer quantidadeEmEstoque) { this.quantidadeEmEstoque = quantidadeEmEstoque; }
}
