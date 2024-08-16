package br.com.udemy.springexpert.localizacao.domain.repository;

import br.com.udemy.springexpert.localizacao.domain.entity.Cidade;
import br.com.udemy.springexpert.localizacao.domain.repository.projctions.CidadeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CidadeRepository extends JpaRepository<Cidade, Long>, JpaSpecificationExecutor<Cidade> {

    @Query(nativeQuery = true, value = "select c.id_cidade as id, c.nome from cidade as c where c.nome =:nome ")
    List<CidadeProjection> findByNomeSqlNativo(@Param("nome") String nome);

    /*@Query(nativeQuery = true, value = "select * from cidade as c where c.nome =:nome ")
    List<Cidade> findByNomeSqlNativo(@Param("nome") String nome);*/

    //Busca pelo Nome Correto
    List<Cidade> findByNome(String nome);

    //Busca pelo Nome com like ORDENADO
    List<Cidade> findByNomeLike(String nome, Sort sorte);

    //Busca pelo Nome com like PAGINADO
    Page<Cidade> findByNomeLike(String nome, Pageable pageable);

    //Busca pelo nome começando por determinada letra
    List<Cidade> findByNomeStartingWith(String nome);

    //Busca pelo nome terminando por determinada letra
    List<Cidade> findByNomeEndingWith(String nome);

    //Busca pelo nome contendo a letra
    List<Cidade> findByNomeContaining(String nome);

    List<Cidade> findByHabitantes(Long habitantes);

    List<Cidade> findByHabitantesLessThan(Long habitantes);

    List<Cidade> findByHabitantesGreaterThan(Long habitantes);

    List<Cidade> findByHabitantesGreaterThanEqual(Long habitantes);

    List<Cidade> findByHabitantesLessThanAndNomeLike(Long habitantes, String nome);
}
