package br.com.udemy.springexpert.controller;

import br.com.udemy.springexpert.domain.entity.Produto;
import br.com.udemy.springexpert.domain.repository.ProdutosRepository;
import br.com.udemy.springexpert.exception.RegraNegocioException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private ProdutosRepository repository;

    public ProdutoController(ProdutosRepository produtos) {
        this.repository = produtos;
    }

    @GetMapping("busca-produto/{id}")
    public Produto buscaProduto(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() -> new RegraNegocioException("Produto do ID: "+id+" não encontrado"));
    }

    @PostMapping("inclui-produto")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto criaProduto(@RequestBody @Valid Produto produto) {
        return repository.save(produto);
    }

    @DeleteMapping("deleta-produto/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletaProduto(@PathVariable Integer id) {
        repository.findById(id).map(produto ->{
            repository.deleteById(id);
                return repository;
        }).orElseThrow(() -> new RegraNegocioException("Produto do ID: "+id+" não encontrado"));
    }

    @PutMapping("atualiza-produto")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizaProduto(@RequestBody @Valid Produto produto) {
        repository.findById(produto.getId()).map(produtoExistente -> {
            produto.setId(produtoExistente.getId());
            return repository.save(produto);
        }).orElseThrow(() -> new RegraNegocioException("Produto do ID: "+produto.getId()+" não encontrado"));
    }

    @GetMapping("listar-produto")
    public List<Produto> listaProduto(Produto produto) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Produto> example = Example.of(produto, exampleMatcher);
        return repository.findAll(example);
    }
}
