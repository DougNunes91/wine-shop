package com.example.wineshop.service;


import com.example.wineshop.model.dto.ClienteFielDto;
import com.example.wineshop.model.dto.CompraFielDto;

import java.util.List;
import java.util.Map;

public interface WineShopService {
    List<CompraFielDto> getCompras();
    Map<String, Object> getMaiorCompra(int ano);
    List<ClienteFielDto> getClientesFieis();
    String getRecomendacao(String clientId);
}