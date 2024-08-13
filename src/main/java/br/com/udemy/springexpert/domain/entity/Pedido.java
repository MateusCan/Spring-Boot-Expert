package br.com.udemy.springexpert.domain.entity;

import br.com.udemy.springexpert.domain.entity.enums.StatusPedido;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne //Identifica que é Muitos para 1, muitos pedidos para 1 cliente
    @JoinColumn(name = "cliente_id") //Faz com que identifique que é um join entre Cliente e Pedido
    private Cliente cliente;

    @Column(name = "data_pedido") //Usar apenas se nome da coluna for diferente
    private LocalDate dataPedido;

    @Column (name = "total", precision = 20, scale = 2) //Coluna total = (20,2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedido status;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;
}
