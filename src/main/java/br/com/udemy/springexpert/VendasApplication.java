package br.com.udemy.springexpert;

import br.com.udemy.springexpert.domain.entity.Cliente;
import br.com.udemy.springexpert.domain.entity.Pedido;
import br.com.udemy.springexpert.domain.repository.Clientes;
import br.com.udemy.springexpert.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes,
                                  @Autowired Pedidos pedidos) {
        return args -> {
            Cliente cliente1 = new Cliente("Mateus");
            clientes.save(cliente1);

            Pedido p = new Pedido();
            p.setCliente(cliente1);
            p.setDataPedido(LocalDate.now());
            p.setTotal(BigDecimal.valueOf(100));

            pedidos.save(p);

            Cliente cliente = clientes.findClienteFetchPedidos(cliente1.getId());
            System.out.println(cliente);
            System.out.println(cliente.getPedidos());

            pedidos.findByCliente(cliente1).forEach(System.out::println);

            /*List<Cliente> listaTodosCliente = clientes.encontrarPorNome("Douglas");
            listaTodosCliente.forEach(System.out::println);*/

            //System.out.println(clientes.existsByNome("Rodrigo"));

            /*clientes.findAll().forEach(c ->{
                clientes.save(c);
            });

            clientes.findByNomeLike("Cli").forEach(System.out::println);

            clientes.findAll().forEach(c ->{
                clientes.delete(c);
            });

            listaTodosCliente = clientes.findAll();
            if(listaTodosCliente.isEmpty()) {
                System.out.println("Nenhum cliente encontrado!");
            }else{
                listaTodosCliente.forEach(System.out::println);
            }*/
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
