package br.com.udemy.springexpert.domain.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "id_cliente") caso o campo tenha nome diferente da base
    private Integer id;

    //@Column(name = "nome_cliente", lenght = 100) caso o campo tenha nome diferente da base
    private String nome;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY) //Referencia ao ManyToOne da entidade Pedido
    private Set<Pedido> pedidos;

    public Cliente(String nome) {
        this.nome = nome;
    }

    public Cliente(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Cliente() {
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
