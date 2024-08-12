package br.com.udemy.springexpert.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoItemPedidoDTO {

    private String descricao;
    private BigDecimal precoUnitario;
    private Integer quantidade;
}
