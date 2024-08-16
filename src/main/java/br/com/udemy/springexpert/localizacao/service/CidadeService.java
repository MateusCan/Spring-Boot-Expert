package br.com.udemy.springexpert.localizacao.service;

import br.com.udemy.springexpert.localizacao.domain.entity.Cidade;
import br.com.udemy.springexpert.localizacao.domain.repository.CidadeRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import static br.com.udemy.springexpert.localizacao.domain.repository.specs.CidadeSpecs.*;
import java.util.List;


@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    public CidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    public void listarCidadesPorNome() {
        cidadeRepository.findByNome("Porto Alegre").forEach(System.out::println);
        cidadeRepository.findByNomeStartingWith("Porto").forEach(System.out::println);
        cidadeRepository.findByNomeEndingWith("ia").forEach(System.out::println);
        cidadeRepository.findByNomeContaining("a").forEach(System.out::println);
    }

    public void listarCidadesPorNomeLike(){
        Pageable pageable = PageRequest.of(0, 5);

        cidadeRepository.findByNomeLike("%a%", pageable)
                .forEach(System.out::println);

        /*cidadeRepository.findByNomeLike("%a%", Sort.by("nome"))
                .forEach(System.out::println);*/
    }

    public Specification<Cidade> nomeLike(String nome) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get("nome"), "%" + nome + "%");
    }

    public void listarCidades() {
        cidadeRepository.findAll().forEach(System.out::println);
    }

    public void listarCidadesPorQtdHabitantesMenor() {
        cidadeRepository.findByHabitantesLessThan(2521564L).forEach(System.out::println);
    }

    public void listarCidadesPorQtdHabitantesMaior() {
        cidadeRepository.findByHabitantesGreaterThan(2521564L).forEach(System.out::println);
    }

    public void listarCidadesPorQtdHabitantesMaiorIgual() {
        cidadeRepository.findByHabitantesGreaterThanEqual(2521564L).forEach(System.out::println);
    }

    public void listarCidadesPorHabitantes() {
        cidadeRepository.findByHabitantes(1332570L).forEach(System.out::println);
    }

    public List<Cidade> filtroDinamico(Cidade cidade){

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
        Example<Cidade> example = Example.of(cidade, matcher);
        return cidadeRepository.findAll(example);
    }

    public void listarCidadesByNomeSpecs(){
        cidadeRepository.findAll(nomeEquals("São Paulo")
                .or(habitantesGreaterThan(10000L))).forEach(System.out::println);
    }

    public void listarCidadesPorNomeSQL(){
        cidadeRepository.findByNomeSqlNativo("São Paulo")
                .stream().map(cidadeProjection -> new Cidade(cidadeProjection.getId(), cidadeProjection.getNome(), null, null))
                .forEach(System.out::println);
    }

    public void listarCidadesSpecsFiltroDinamico(Cidade filtro){
        Specification<Cidade> specs = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.conjunction());

        if(StringUtils.hasText(filtro.getNome())){
            specs = specs.and(nomeLike(filtro.getNome()));
        }

        if(filtro.getHabitantes()!=null){
            specs = specs.and(habitantesGreaterThan(filtro.getHabitantes()));
        }

        cidadeRepository.findAll(specs).forEach(System.out::println);
    }
}
