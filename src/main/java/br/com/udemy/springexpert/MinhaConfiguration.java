package br.com.udemy.springexpert;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

//@Configuration
//@Profile("development")
@Development
public class MinhaConfiguration {

    @Bean(name = "applicationName")
    public CommandLineRunner executar(){
        return args -> System.out.println("Executando a config no ambiente de development...");
    }
}
