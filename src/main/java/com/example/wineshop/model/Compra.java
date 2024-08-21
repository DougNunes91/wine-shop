package com.example.wineshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compra {
    private String codigo;
    private String cliente;
    private Date data;
    private String produto;
    private Double valorTotal;
    private int anoCompra;
    private int quantidade;
}
