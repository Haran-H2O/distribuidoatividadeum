package br.com.utfpr.distribuidoatividadeum.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages(
            "br.com.utfpr.distribuidoatividadeum.produtos.provider",
            "br.com.utfpr.distribuidoatividadeum.cep.provider",
            "br.com.utfpr.distribuidoatividadeum.pagamento.provider",
            "br.com.utfpr.distribuidoatividadeum.email.provider",
            "br.com.utfpr.distribuidoatividadeum.fiscal.provider",
            "br.com.utfpr.distribuidoatividadeum.entrega.provider",
            "br.com.utfpr.distribuidoatividadeum.loja.provider"
        );
        register(JacksonFeature.class);
    }
}
