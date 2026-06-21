package br.com.utfpr.distribuidoatividadeum.mensagens;

public class EmailMessage {
    private String tipo;
    private String destinatario;
    private String conteudo;

    public EmailMessage() {}

    public EmailMessage(String tipo, String destinatario, String conteudo) {
        this.tipo = tipo;
        this.destinatario = destinatario;
        this.conteudo = conteudo;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
}
