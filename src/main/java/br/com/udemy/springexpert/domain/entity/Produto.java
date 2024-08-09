package br.com.udemy.springexpert.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id") //Usar apenas se nome da coluna for diferente
    private Integer id;

    @Column(name = "descricao") //Usar apenas se nome da coluna for diferente
    private String descricao;

    @Column(name = "preco_unitario") //Usar apenas se nome da coluna for diferente
    private BigDecimal preco;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
