package br.com.udemy.springexpert.controller;

import br.com.udemy.springexpert.domain.entity.ItemPedido;
import br.com.udemy.springexpert.domain.entity.Pedido;
import br.com.udemy.springexpert.domain.entity.enums.StatusPedido;
import br.com.udemy.springexpert.dto.AtualizaStatusPedidoDTO;
import br.com.udemy.springexpert.dto.InfoItemPedidoDTO;
import br.com.udemy.springexpert.dto.InfoPedidoDTO;
import br.com.udemy.springexpert.dto.PedidoDTO;
import br.com.udemy.springexpert.exception.RegraNegocioException;
import br.com.udemy.springexpert.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping("incluir-pedido")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer criarPedido(@RequestBody @Valid PedidoDTO dto) {
        Pedido pedido = service.incluirPedido(dto);
        return pedido.getId();}

    @GetMapping("buscar-pedido/{id}")
    public InfoPedidoDTO buscarPedido(@PathVariable Integer id) {
        return service.obterPedidoCompleto(id).map(this::converter)
                .orElseThrow(() -> new RegraNegocioException("Pedido não encontrado"));
    }

    @PatchMapping("atualiza-status/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarStatus(@PathVariable Integer id, @RequestBody @Valid AtualizaStatusPedidoDTO dto) {
        service.atualizaStatus(id, StatusPedido.valueOf(dto.getStatus()));
    }

    private InfoPedidoDTO converter(Pedido pedido) {
        return InfoPedidoDTO.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .itens(converter(pedido.getItens())).build();
    }

    private List<InfoItemPedidoDTO> converter(List<ItemPedido> itens) {
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }

        return itens.stream().map(itemPedido -> InfoItemPedidoDTO.builder()
                .descricao(itemPedido.getProduto().getDescricao())
                .precoUnitario(itemPedido.getProduto().getPreco())
                .quantidade(itemPedido.getQuantidade())
                .build()).collect(Collectors.toList());

    }


}
