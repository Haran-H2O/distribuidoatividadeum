package br.com.utfpr.distribuidoatividadeum.produtos.provider;

import br.com.utfpr.distribuidoatividadeum.mensagens.EstoqueMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutosService {

    private final List<Produto> produtos = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void carregarProdutos() {
        try (InputStream is = getClass().getResourceAsStream("/produtos.json")) {
            produtos.addAll(mapper.readValue(is, new TypeReference<List<Produto>>() {}));
        } catch (Exception e) {
            System.out.println("ERRO ao carregar produtos.json: " + e.getMessage());
        }
    }

    public List<Produto> listarTodos() {
        return produtos;
    }

    public Optional<Produto> buscarPorId(String id) {
        return produtos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @RabbitListener(queues = "fila.estoque")
    public void baixarEstoque(EstoqueMessage msg) {
        System.out.println("[← FILA] fila.estoque | baixa de " + msg.getQuantidade() + "x " + msg.getProdutoId());
        buscarPorId(msg.getProdutoId()).ifPresent(p -> {
            int novo = p.getQuantidadeEmEstoque() - msg.getQuantidade();
            p.setQuantidadeEmEstoque(Math.max(0, novo));
            System.out.println("ESTOQUE ATUALIZADO: " + p.getNome() + " | novo estoque: " + p.getQuantidadeEmEstoque());
        });
    }
}
