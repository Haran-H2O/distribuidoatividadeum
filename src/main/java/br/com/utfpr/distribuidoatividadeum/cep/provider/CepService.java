package br.com.utfpr.distribuidoatividadeum.cep.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.Unirest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class CepService {

    private final ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = "fila.cep")
    public Endereco consultarViaFila(String cep) {
        System.out.println("[← FILA] fila.cep | consultando CEP " + cep + " no ViaCEP");
        Endereco result = consultar(cep);
        System.out.println("[→ RPC] fila.cep | respondendo com endereço para CEP " + cep);
        return result;
    }

    public Endereco consultar(String cep) {
        try {
            String body = Unirest.get("https://viacep.com.br/ws/{cep}/json/")
                .routeParam("cep", cep)
                .asString()
                .getBody();
            Endereco endereco = mapper.readValue(body, Endereco.class);
            if (Boolean.TRUE.equals(endereco.getErro())) {
                return null;
            }
            return endereco;
        } catch (Exception e) {
            System.out.println("ERRO ao consultar CEP " + cep + ": " + e.getMessage());
            return null;
        }
    }
}
