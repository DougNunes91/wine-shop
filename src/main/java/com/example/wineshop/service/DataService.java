package com.example.wineshop.service;

import com.example.wineshop.model.Cliente;
import com.example.wineshop.model.Compra;
import com.example.wineshop.model.Produto;

import java.util.List;

public interface DataService {
    void init() throws Exception;
    List<Produto> getProdutos();
    List<Cliente> getClientes();
    List<Compra> getCompras();
}
