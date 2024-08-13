package br.com.udemy.springexpert.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") //Usar apenas se nome da coluna for diferente
    private Integer id;

    @Column(name = "descricao") //Usar apenas se nome da coluna for diferente
    @NotEmpty(message = "{campo.descricao.obrigatorio}")
    private String descricao;

    @Column(name = "preco_unitario") //Usar apenas se nome da coluna for diferente
    @NotNull(message = "{campo.preco.obrigatorio}")
    private BigDecimal preco;
}
