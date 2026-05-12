package br.com.utfpr.distribuidoatividadeum.email.provider;

public class EmailRequest {
    private String tipo;
    private String destinatario;
    private String conteudo;

    public EmailRequest() {}

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
}
