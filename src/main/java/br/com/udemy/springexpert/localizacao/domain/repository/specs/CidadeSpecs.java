package br.com.udemy.springexpert.localizacao.domain.repository.specs;

import br.com.udemy.springexpert.localizacao.domain.entity.Cidade;
import org.springframework.data.jpa.domain.Specification;

public class CidadeSpecs {

    public static Specification<Cidade> nomeEquals(String nome) {
        return (cidadeRoot, query, criteriaBuilder) ->
                criteriaBuilder.equal(cidadeRoot.get("nome"), nome);
    }

    public static Specification<Cidade> habitantesGreaterThan(Long value) {
        return (cidadeRoot, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(cidadeRoot.get("habitantes"), value);
    }

    public static Specification<Cidade> habitantesBetwenn(Long min, Long max) {
        return (cidadeRoot, query, criteriaBuilder) ->
                criteriaBuilder.between(cidadeRoot.get("habitantes"), min, max);
    }
}
