package br.com.udemy.springexpert.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id_cliente") caso o campo tenha nome diferente da base
    private Integer id;

    //@Column(name = "nome_cliente", lenght = 100) caso o campo tenha nome diferente da base
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private String nome;

    @Column(length = 11)
    @NotEmpty(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")
    private String cpf;

    //@JsonIgnore Anotação para ignorar o campo no response.
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY) //Referencia ao ManyToOne da entidade Pedido
    private Set<Pedido> pedidos;
}
