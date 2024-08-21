package com.example.wineshop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDto {
    @JsonProperty("codigo")
    private int codigo;

    @JsonProperty("tipo_vinho")
    private String tipoVinho;

    @JsonProperty("preco")
    private double preco;

    @JsonProperty("safra")
    private String safra;

    @JsonProperty("ano_compra")
    private int anoCompra;
}
