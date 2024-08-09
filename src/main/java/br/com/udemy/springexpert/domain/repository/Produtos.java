package br.com.udemy.springexpert.domain.repository;

import br.com.udemy.springexpert.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
