package br.com.utfpr.distribuidoatividadeum.produtos.provider;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutosService {

    private final List<Produto> produtos = new ArrayList<>();

    public ProdutosService() {
        produtos.add(new Produto(1L, "Notebook Dell XPS", "Notebook de alto desempenho 16GB RAM", 4999.99, 10));
        produtos.add(new Produto(2L, "Mouse Logitech MX Master", "Mouse ergonômico sem fio", 299.90, 25));
        produtos.add(new Produto(3L, "Teclado Mecânico Keychron K2", "Teclado mecânico compacto switches Red", 499.00, 15));
        produtos.add(new Produto(4L, "Monitor LG 27\" 4K", "Monitor 4K IPS 27 polegadas HDR", 2199.00, 8));
        produtos.add(new Produto(5L, "Headset Sony WH-1000XM5", "Fone com cancelamento de ruído", 1499.00, 12));
    }

    public List<Produto> listarTodos() {
        return produtos;
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public boolean baixarEstoque(Long id, Integer quantidade) {
        Optional<Produto> opt = buscarPorId(id);
        if (opt.isPresent() && opt.get().getEstoque() >= quantidade) {
            opt.get().setEstoque(opt.get().getEstoque() - quantidade);
            System.out.println("ESTOQUE ATUALIZADO: produto #" + id + " | novo estoque: " + opt.get().getEstoque());
            return true;
        }
        System.out.println("ERRO: Estoque insuficiente para produto #" + id);
        return false;
    }
}
