package br.com.udemy.springexpert.service.impl;

import br.com.udemy.springexpert.domain.entity.Cliente;
import br.com.udemy.springexpert.domain.entity.ItemPedido;
import br.com.udemy.springexpert.domain.entity.Pedido;
import br.com.udemy.springexpert.domain.entity.Produto;
import br.com.udemy.springexpert.domain.entity.enums.StatusPedido;
import br.com.udemy.springexpert.domain.repository.ClientesRepository;
import br.com.udemy.springexpert.domain.repository.ItemsPedidoRepository;
import br.com.udemy.springexpert.domain.repository.PedidosRepository;
import br.com.udemy.springexpert.domain.repository.ProdutosRepository;
import br.com.udemy.springexpert.dto.ItemPedidoDTO;
import br.com.udemy.springexpert.dto.PedidoDTO;
import br.com.udemy.springexpert.exception.PedidoNaoEncontradoException;
import br.com.udemy.springexpert.exception.RegraNegocioException;
import br.com.udemy.springexpert.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//Construtor necesario para não precisar dar injeção de dependencia em construtor
public class PedidoServiceImpl implements PedidoService {

    private final PedidosRepository pedidosRepository;
    private final ClientesRepository  clientesRepository;
    private final ProdutosRepository produtosRepository;
    private final ItemsPedidoRepository itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido incluirPedido(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository.findById(idCliente).orElseThrow(() ->
                new RegraNegocioException("Código do cliente inválido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemPedidos = converterItems(pedido, dto.getItems());
        pedidosRepository.save(pedido);
        itemsPedidoRepository.saveAll(itemPedidos);
        pedido.setItens(itemPedidos);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidosRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    @Modifying
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidosRepository.findById(id).map(pedido -> {
            pedido.setStatus(statusPedido);
            return pedidosRepository.save(pedido);
        }).orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado"));
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
        if (items.isEmpty()) {
            throw new RegraNegocioException("Não é possivel realizar um pedido sem items");
        }

        return items.stream().map(dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto = produtosRepository.findById(idProduto)
                    .orElseThrow(() -> new RegraNegocioException("Código de produto Inválido: "+idProduto));


            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;
        }).collect(Collectors.toList());
    }
}
