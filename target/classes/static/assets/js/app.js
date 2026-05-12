let carrinho = [];
let produtos = [];

async function carregarProdutos() {
    try {
        const resp = await fetch('/api/loja/produtos');
        produtos = await resp.json();
        renderProdutos();
    } catch (e) {
        document.getElementById('produtos-grid').innerHTML = '<p style="color:#c62828">Erro ao carregar produtos</p>';
    }
}

function renderProdutos() {
    const grid = document.getElementById('produtos-grid');
    grid.innerHTML = produtos.map(p => `
        <div class="produto-card">
            <h3>${p.nome}</h3>
            <p class="desc">${p.descricao}</p>
            <p class="preco">R$ ${p.preco.toFixed(2).replace('.', ',')}</p>
            <p class="estoque">Estoque: ${p.estoque} un.</p>
            <button class="btn btn-primary" onclick="adicionarAoCarrinho(${p.id})" ${p.estoque === 0 ? 'disabled' : ''}>
                ${p.estoque === 0 ? 'Sem estoque' : 'Adicionar ao Carrinho'}
            </button>
        </div>
    `).join('');
}

function adicionarAoCarrinho(produtoId) {
    const produto = produtos.find(p => p.id === produtoId);
    if (!produto) return;
    const existente = carrinho.find(c => c.id === produtoId);
    if (existente) {
        if (existente.quantidade < produto.estoque) existente.quantidade++;
    } else {
        carrinho.push({ ...produto, quantidade: 1 });
    }
    renderCarrinho();
}

function alterarQuantidade(produtoId, delta) {
    const item = carrinho.find(c => c.id === produtoId);
    if (!item) return;
    item.quantidade += delta;
    if (item.quantidade <= 0) carrinho = carrinho.filter(c => c.id !== produtoId);
    renderCarrinho();
}

function renderCarrinho() {
    const lista = document.getElementById('carrinho-lista');
    const totalEl = document.getElementById('carrinho-total');
    const badge = document.getElementById('cart-count-badge');

    if (carrinho.length === 0) {
        lista.innerHTML = '<p class="empty-cart">Nenhum produto adicionado</p>';
        totalEl.style.display = 'none';
        badge.innerHTML = '';
        return;
    }

    const total = carrinho.reduce((s, c) => s + c.preco * c.quantidade, 0);
    const count = carrinho.reduce((s, c) => s + c.quantidade, 0);
    badge.innerHTML = `<span class="cart-badge">${count}</span>`;

    lista.innerHTML = carrinho.map(c => `
        <div class="carrinho-item">
            <span class="nome">${c.nome}</span>
            <div class="qtd">
                <button onclick="alterarQuantidade(${c.id}, -1)">−</button>
                <span>${c.quantidade}</span>
                <button onclick="alterarQuantidade(${c.id}, +1)">+</button>
            </div>
            <span class="total-item">R$ ${(c.preco * c.quantidade).toFixed(2).replace('.', ',')}</span>
        </div>
    `).join('');

    document.getElementById('total-valor').textContent = 'R$ ' + total.toFixed(2).replace('.', ',');
    totalEl.style.display = 'flex';
}

async function buscarCep() {
    const cep = document.getElementById('cep').value.replace(/\D/g, '');
    if (cep.length !== 8) {
        alert('Informe um CEP válido com 8 dígitos');
        return;
    }
    try {
        const resp = await fetch(`/api/loja/cep/${cep}`);
        const data = await resp.json();
        const info = document.getElementById('cep-info');
        if (data.erro) {
            info.textContent = 'CEP não encontrado';
            info.style.display = 'block';
        } else {
            info.textContent = `${data.logradouro}, ${data.bairro} — ${data.localidade}/${data.uf}`;
            info.style.display = 'block';
        }
    } catch (e) {
        alert('Erro ao consultar CEP');
    }
}

async function realizarCompra() {
    if (carrinho.length === 0) {
        alert('Adicione pelo menos um produto ao carrinho');
        return;
    }

    const email = document.getElementById('email').value.trim();
    const cep = document.getElementById('cep').value.replace(/\D/g, '');
    const cartaoNumero = document.getElementById('cartao-numero').value.replace(/\s/g, '');

    if (!email) { alert('Informe seu e-mail'); return; }
    if (cep.length !== 8) { alert('Informe um CEP válido'); return; }
    if (cartaoNumero.length < 13) { alert('Informe o número do cartão'); return; }

    const primeiroItem = carrinho[0];
    const btn = document.getElementById('btn-comprar');
    btn.disabled = true;
    btn.innerHTML = '<span class="spinner"></span>Processando...';

    const payload = {
        produtoId: primeiroItem.id,
        quantidade: primeiroItem.quantidade,
        cep: cep,
        email: email,
        cartao: cartaoNumero
    };

    try {
        const resp = await fetch('/api/loja/compra', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });
        const data = await resp.json();
        exibirResultado(data);
    } catch (e) {
        alert('Erro ao processar compra: ' + e.message);
    } finally {
        btn.disabled = false;
        btn.innerHTML = 'Confirmar Compra';
    }
}

function exibirResultado(data) {
    const resultado = document.getElementById('resultado');
    const header = document.getElementById('resultado-header');
    const titulo = document.getElementById('resultado-titulo');
    const subtitulo = document.getElementById('resultado-subtitulo');
    const info = document.getElementById('resultado-info');
    const etapasList = document.getElementById('etapas-list');

    const concluido = data.status === 'CONCLUIDO';
    header.className = 'resultado-header' + (concluido ? '' : ' erro');
    titulo.textContent = concluido
        ? 'Pedido #' + data.pedidoId + ' Concluído'
        : 'Erro no Pedido #' + data.pedidoId;
    subtitulo.textContent = concluido
        ? data.produto + ' — R$ ' + data.valorTotal.toFixed(2).replace('.', ',')
        : (data.notaFiscal && data.notaFiscal.mensagem) || 'Erro durante o processamento';

    if (concluido) {
        info.innerHTML = `
            <div class="info-box">
                <div class="label">Pagamento</div>
                <div class="value">${data.pagamento.status}</div>
                <div style="font-size:0.75rem;color:#888;margin-top:2px">${data.pagamento.transacaoId}</div>
            </div>
            <div class="info-box">
                <div class="label">Nota Fiscal</div>
                <div class="value">${data.notaFiscal.chaveNF}</div>
                <div style="font-size:0.75rem;color:#888;margin-top:2px">${data.notaFiscal.status}</div>
            </div>
            <div class="info-box">
                <div class="label">Rastreio</div>
                <div class="value">${data.entrega.codigoRastreio}</div>
                <div style="font-size:0.75rem;color:#888;margin-top:2px">Previsão: ${data.entrega.previsaoEntrega}</div>
            </div>
            <div class="info-box">
                <div class="label">Endereço</div>
                <div class="value" style="font-size:0.78rem">${data.endereco}</div>
            </div>
        `;
    } else {
        info.innerHTML = '';
    }

    etapasList.innerHTML = (data.etapas || []).map(e => `<li>${e}</li>`).join('');
    resultado.className = 'resultado show';
    resultado.scrollIntoView({ behavior: 'smooth', block: 'start' });
}

document.getElementById('cep').addEventListener('input', function () {
    let v = this.value.replace(/\D/g, '');
    if (v.length > 5) v = v.slice(0, 5) + '-' + v.slice(5, 8);
    this.value = v;
});

document.getElementById('cartao-numero').addEventListener('input', function () {
    let v = this.value.replace(/\D/g, '').slice(0, 16);
    v = v.replace(/(.{4})/g, '$1 ').trim();
    this.value = v;
});

carregarProdutos();
