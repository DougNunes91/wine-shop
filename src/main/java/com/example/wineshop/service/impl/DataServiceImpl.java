package com.example.wineshop.service.impl;

import com.example.wineshop.model.Cliente;
import com.example.wineshop.model.Compra;
import com.example.wineshop.model.Produto;
import com.example.wineshop.model.dto.ClienteDTO;
import com.example.wineshop.model.dto.CompraDto;
import com.example.wineshop.model.dto.ProdutoDto;
import com.example.wineshop.service.DataService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataServiceImpl implements DataService {
    private List<Produto> produtos;
    private List<Cliente> clientes;
    private List<Compra> compras;
    private Map<String, Produto> produtoMap;

    @PostConstruct
    @Override
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        URL produtosUrl = new URL("https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com/produtos-mnboX5IPl6VgG390FECTKqHsD9SkLS.json");
        List<ProdutoDto> produtosDto = mapper.readValue(produtosUrl, new TypeReference<List<ProdutoDto>>(){});

        produtos = produtosDto.stream().map(this::convertToProduto).collect(Collectors.toList());
        produtoMap = produtos.stream().collect(Collectors.toMap(Produto::getCodigo, p -> p));

        URL clientesComprasUrl = new URL("https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com/clientes-Vz1U6aR3GTsjb3W8BRJhcNKmA81pVh.json");
        List<ClienteDTO> clientesDto = mapper.readValue(clientesComprasUrl, new TypeReference<List<ClienteDTO>>(){});

        clientes = new ArrayList<>();
        compras = new ArrayList<>();

        for (int i = 0; i < clientesDto.size(); i++) {
            ClienteDTO clienteDto = clientesDto.get(i);
            String codigoCliente = String.valueOf(i + 1);

            Cliente cliente = new Cliente();
            cliente.setCodigo(codigoCliente);
            cliente.setNome(clienteDto.getNome());
            cliente.setCpf(clienteDto.getCpf());
            clientes.add(cliente);

            for (CompraDto compraDto : clienteDto.getCompras()) {
                Produto produto = produtoMap.get(compraDto.getCodigo());
                if (produto != null) {
                    Compra compra = new Compra();
                    compra.setCodigo(generateCompraCode(codigoCliente, compraDto.getCodigo()));
                    compra.setQuantidade(compraDto.getQuantidade());
                    compra.setCliente(codigoCliente);
                    compra.setProduto(produto.getCodigo());
                    compra.setAnoCompra(Integer.parseInt(produto.getAnoCompra()));
                    compra.setValorTotal(produto.getPreco() * compraDto.getQuantidade());
                    compras.add(compra);
                }
            }
        }
    }

    private String generateCompraCode(String clienteCodigo, String produtoCodigo) {
        return clienteCodigo + "-" + produtoCodigo;
    }

    private Produto convertToProduto(ProdutoDto dto) {
        Produto produto = new Produto();
        produto.setCodigo(String.valueOf(dto.getCodigo()));
        produto.setProduto(dto.getTipoVinho());
        produto.setVariedade(dto.getTipoVinho());
        produto.setPais("N/A");
        produto.setCategoria("N/A");
        produto.setSafra(dto.getSafra());
        produto.setPreco(dto.getPreco());
        produto.setAnoCompra(String.valueOf(dto.getAnoCompra()));

        return produto;
    }

    public Compra getMaiorCompraDoAno(int ano) {
        return compras.stream()
                .filter(compra -> compra.getAnoCompra() == ano)
                .max(Comparator.comparingDouble(Compra::getValorTotal))
                .orElseThrow(() -> new RuntimeException("Nenhuma compra encontrada para o ano " + ano));
    }

    @Override
    public List<Produto> getProdutos() {
        return produtos;
    }

    @Override
    public List<Cliente> getClientes() {
        return clientes;
    }

    @Override
    public List<Compra> getCompras() {
        return compras;
    }
}