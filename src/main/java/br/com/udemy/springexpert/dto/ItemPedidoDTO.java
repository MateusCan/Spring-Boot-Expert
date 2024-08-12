package br.com.udemy.springexpert.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemPedidoDTO {

    private Integer produto;
    private Integer quantidade;
}
