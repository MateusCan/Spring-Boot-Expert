package br.com.udemy.springexpert.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoPedidoDTO {

    private Integer codigo;
    private String cpf;
    private String nomeCliente;
    private BigDecimal total;
    private String dataPedido;
    private String status;
    private List<InfoItemPedidoDTO> itens;
}
