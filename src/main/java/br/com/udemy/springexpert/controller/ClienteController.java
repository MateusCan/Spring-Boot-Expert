package br.com.udemy.springexpert.controller;

import br.com.udemy.springexpert.domain.entity.Cliente;
import br.com.udemy.springexpert.domain.repository.ClientesRepository;
import br.com.udemy.springexpert.exception.RegraNegocioException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private ClientesRepository clientes;

    //Injeção de dependencias no construtor
    public ClienteController(ClientesRepository clientes) {
        this.clientes = clientes;}

    @GetMapping("busca-cliente/{id}")
    public Cliente getClienteById(@PathVariable Integer id) {
        return clientes.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Cliente com ID: "+id+" Não encontrado"));
    }

    @PostMapping("inclui-cliente")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente criarCliente(@RequestBody @Valid Cliente cliente) {
        return clientes.save(cliente);
    }

    @DeleteMapping("deleta-cliente/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCliente(@PathVariable Integer id) {
        clientes.findById(id).map(cliente -> {
            clientes.delete(cliente);
            return cliente;
        }).orElseThrow(() -> new RegraNegocioException("Cliente com ID: "+id+" Não encontrado"));
    }

    @PutMapping("atualiza-cliente")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarCliente(@RequestBody @Valid Cliente cliente) {
        clientes.findById(cliente.getId()).map(cliente1 -> {
            cliente.setId(cliente1.getId());
            clientes.save(cliente);
            return cliente1;
        }).orElseThrow(() -> new RegraNegocioException("Cliente com ID: "+cliente.getId()+" Não encontrado"));
    }

    @GetMapping("busca-cliente")
    public List<Cliente> listar(Cliente cliente) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                                                .withIgnoreCase()
                                                .withStringMatcher(ExampleMatcher
                                                        .StringMatcher.CONTAINING);
        Example example = Example.of(cliente, matcher);
        return clientes.findAll(example);
    }
}
