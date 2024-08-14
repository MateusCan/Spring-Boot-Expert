package br.com.udemy.springexpert.controller;

import br.com.udemy.springexpert.domain.entity.Cliente;
import br.com.udemy.springexpert.domain.repository.ClientesRepository;
import br.com.udemy.springexpert.exception.RegraNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
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
    @Operation(summary = "Obter detalhes de um cliente", description = "Retorna as informações de um cliente específico pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")})
    public Cliente getClienteById(@PathVariable @Parameter(description = "id do cliente") Integer id) {
        return clientes.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Cliente com ID: "+id+" Não encontrado"));
    }

    @PostMapping("inclui-cliente")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar um cliente", description = "Cria um cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")})
    public Cliente criarCliente(@RequestBody @Valid Cliente cliente) {
        return clientes.save(cliente);
    }

    @DeleteMapping("deleta-cliente/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deleta um cliente", description = "Deleta um cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")})
    public void deletarCliente(@PathVariable Integer id) {
        clientes.findById(id).map(cliente -> {
            clientes.delete(cliente);
            return cliente;
        }).orElseThrow(() -> new RegraNegocioException("Cliente com ID: "+id+" Não encontrado"));
    }

    @PutMapping("atualiza-cliente")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Atualiza um cliente", description = "Atualiza um cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")})
    public void atualizarCliente(@RequestBody @Valid Cliente cliente) {
        clientes.findById(cliente.getId()).map(cliente1 -> {
            cliente.setId(cliente1.getId());
            clientes.save(cliente);
            return cliente1;
        }).orElseThrow(() -> new RegraNegocioException("Cliente com ID: "+cliente.getId()+" Não encontrado"));
    }

    @GetMapping("busca-cliente")
    @Operation(summary = "Lista um cliente", description = "Lista um cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cliente"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")})
    public List<Cliente> listar(Cliente cliente) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                                                .withIgnoreCase()
                                                .withStringMatcher(ExampleMatcher
                                                        .StringMatcher.CONTAINING);
        Example example = Example.of(cliente, matcher);
        return clientes.findAll(example);
    }
}
