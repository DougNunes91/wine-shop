package com.example.wineshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produto {
    private String codigo;
    private String produto;
    private String variedade;
    private String pais;
    private String categoria;
    private String safra;
    private String anoCompra;
    private double preco;

}