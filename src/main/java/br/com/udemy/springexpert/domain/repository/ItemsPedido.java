package br.com.udemy.springexpert.domain.repository;

import br.com.udemy.springexpert.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedido extends JpaRepository<ItemPedido, Integer> {
}
