package com.example.wineshop.model.dto;

import com.example.wineshop.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraFielDto {
    private String nomeCliente;
    private String cpfCliente;
    private Produto produto;
    private int quantidade;
    private double valorTotal;
}
