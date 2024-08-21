package com.example.wineshop.controller;

import com.example.wineshop.model.dto.ClienteFielDto;
import com.example.wineshop.model.dto.CompraFielDto;
import com.example.wineshop.service.WineShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class WineShopController {

    @Autowired
    private WineShopService wineShopService;

    @GetMapping("/compras")
    public List<CompraFielDto> getCompras() {
        return wineShopService.getCompras();
    }

    @GetMapping("/maior-compra/{ano}")
    public Map<String, Object> getMaiorCompra(@PathVariable int ano) {
        return wineShopService.getMaiorCompra(ano);
    }

    @GetMapping("/clientes-fieis")
    public List<ClienteFielDto> getClientesFieis() {
        return wineShopService.getClientesFieis();
    }

    @GetMapping("/recomendacao/cliente/{clientId}")
    public String getRecomendacao(@PathVariable String clientId) {return wineShopService.getRecomendacao(clientId);}
}