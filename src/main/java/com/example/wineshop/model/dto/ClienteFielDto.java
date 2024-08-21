package com.example.wineshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteFielDto {
    private String nome;
    private String cpf;
    private double valorTotalCompras;
    private int quantidadeCompras;

    public ClienteFielDto(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.valorTotalCompras = 0.0;
        this.quantidadeCompras = 0;
    }

    public void adicionarCompra(double valor) {
        this.valorTotalCompras += valor;
        this.quantidadeCompras++;
    }
}
