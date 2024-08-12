package br.com.udemy.springexpert.domain.repository;

import br.com.udemy.springexpert.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClientesRepository extends JpaRepository<Cliente, Integer> {

    //@Query("SELECT u FROM CLIENTES u WHERE u.NOME = ?")
    //List<Clientes> findByNomeLike(String nome);

    //@Query("SELECT u FROM CLIENTES u WHERE u.NOME = ? OR u.ID = ?")
    //List<Cliente> findByNomeOrIdOrderBy(String nome, Integer id);

    @Query(value = "SELECT * FROM Cliente C WHERE C.nome LIKE '%:nome%'", nativeQuery = true)
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    @Query(value = "DELETE from Cliente c where c.nome =:nome ")
    @Modifying //Colocar anotação caso for update, insert ou delete
    @Transactional
    void deleteByNome(String nome);

    boolean existsByNome(String nome);

    @Query(value = "SELECT c FROM Cliente c left join fetch c.pedidos where c.id =:id ")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);

}
