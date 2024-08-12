package br.com.udemy.springexpert.service;

import br.com.udemy.springexpert.domain.entity.Pedido;
import br.com.udemy.springexpert.domain.entity.enums.StatusPedido;
import br.com.udemy.springexpert.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido incluirPedido(PedidoDTO pedido);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
