package br.com.udemy.springexpert.domain.repository;

import br.com.udemy.springexpert.domain.entity.Cliente;
import br.com.udemy.springexpert.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PedidosRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

    @Query("SELECT p FROM Pedido p LEFT JOIN FETCH p.itens WHERE p.id =:id")
    Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);
}
