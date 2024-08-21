package com.example.wineshop.service.impl;

import com.example.wineshop.model.Compra;
import com.example.wineshop.model.Cliente;
import com.example.wineshop.model.Produto;
import com.example.wineshop.model.dto.ClienteFielDto;
import com.example.wineshop.model.dto.CompraFielDto;
import com.example.wineshop.service.DataService;
import com.example.wineshop.service.WineShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WineShopServiceImpl implements WineShopService {

    @Autowired
    private DataService dataService;

    @Override
    public List<CompraFielDto> getCompras() {
        List<Compra> compras = dataService.getCompras();
        List<Cliente> clientes = dataService.getClientes();
        List<Produto> produtos = dataService.getProdutos();

        return compras.stream()
                .map(compra -> mapToCompraDto(compra, clientes, produtos))
                .sorted(Comparator.comparingDouble(CompraFielDto::getValorTotal))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getMaiorCompra(int ano) {
        try {
            List<Compra> compras = dataService.getCompras();
            List<Cliente> clientes = dataService.getClientes();
            List<Produto> produtos = dataService.getProdutos();

            Compra maiorCompra = compras.stream()
                    .filter(compra -> compra.getAnoCompra() == ano)
                    .max(Comparator.comparingDouble(Compra::getValorTotal))
                    .orElseThrow(() -> new RuntimeException("Nenhuma compra encontrada para o ano " + ano));

            Cliente cliente = findClienteByCodigo(clientes, maiorCompra.getCliente());
            Produto produto = findProdutoByCodigo(produtos, maiorCompra.getProduto());

            Map<String, Object> response = new HashMap<>();
            response.put("nomeCliente", cliente.getNome());
            response.put("cpfCliente", cliente.getCpf());
            response.put("dadoProduto", produto);
            response.put("quantidadeCompra", maiorCompra.getQuantidade());
            response.put("valorTotal", maiorCompra.getValorTotal());

            return response;
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar a maior compra: " + e.getMessage());
        }
    }

    @Override
    public List<ClienteFielDto> getClientesFieis() {
        List<Compra> compras = dataService.getCompras();
        List<Cliente> clientes = dataService.getClientes();
        List<Produto> produtos = dataService.getProdutos();

        Map<String, ClienteFielDto> clientesMap = new HashMap<>();

        for (Compra compra : compras) {
            Cliente cliente = findClienteByCodigo(clientes, compra.getCliente());
            Produto produto = findProdutoByCodigo(produtos, compra.getProduto());

            double valorCompra = produto.getPreco() * compra.getQuantidade();

            clientesMap.computeIfAbsent(cliente.getCodigo(), k -> new ClienteFielDto(cliente.getNome(), cliente.getCpf()))
                    .adicionarCompra(valorCompra);
        }

        return clientesMap.values().stream()
                .sorted(Comparator.comparingDouble(ClienteFielDto::getValorTotalCompras).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    @Override
    public String getRecomendacao(String clientId) {
        List<Compra> compras = dataService.getCompras();
        List<Produto> produtos = dataService.getProdutos();

        Map<String, Integer> tiposComprados = new HashMap<>();

        List<Compra> comprasDoCliente = compras.stream()
                .filter(compra -> compra.getCliente().equals(clientId))
                .collect(Collectors.toList());


        for (Compra compra : comprasDoCliente) {
            Produto produto = findProdutoByCodigo(produtos, compra.getProduto());
            if (produto != null) {
                String tipoVinho = produto.getVariedade();
                tiposComprados.merge(tipoVinho, compra.getQuantidade(), Integer::sum);
            }
        }

        if (tiposComprados.isEmpty()) {
            return "Nenhuma recomendação disponível";
        }

        String tipoMaisComprado = tiposComprados.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Nenhuma recomendação disponível");


        List<Produto> recomendacoes = produtos.stream()
                .filter(p -> p.getVariedade().equals(tipoMaisComprado))
                .filter(p -> comprasDoCliente.stream().noneMatch(c -> c.getProduto().equals(p.getCodigo())))
                .collect(Collectors.toList());

        if (!recomendacoes.isEmpty()) {
            Produto recomendacao = recomendacoes.get(new Random().nextInt(recomendacoes.size()));
            return "Recomendamos o vinho: " + recomendacao.getProduto();
        } else {
            return "Recomendamos explorar mais vinhos do tipo: " + tipoMaisComprado;
        }
    }


    private CompraFielDto mapToCompraDto(Compra compra, List<Cliente> clientes, List<Produto> produtos) {
        Cliente cliente = findClienteByCodigo(clientes, compra.getCliente());
        Produto produto = findProdutoByCodigo(produtos, compra.getProduto());

        double valorTotal = produto.getPreco() * compra.getQuantidade();

        return new CompraFielDto(
                cliente.getNome(),
                cliente.getCpf(),
                produto,
                compra.getQuantidade(),
                valorTotal
        );
    }

    private Cliente findClienteByCodigo(List<Cliente> clientes, String codigo) {
        return clientes.stream()
                .filter(c -> c.getCodigo().equals(codigo))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    private Produto findProdutoByCodigo(List<Produto> produtos, String codigo) {
        return produtos.stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }
}