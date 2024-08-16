package br.com.udemy.springexpert.localizacao;

import br.com.udemy.springexpert.localizacao.domain.entity.Cidade;
import br.com.udemy.springexpert.localizacao.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LocalizacaoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LocalizacaoApplication.class, args);
    }

    @Autowired
    //private CidadeRepository cidadeRepository;
    private CidadeService cidadeService;

    @Override
    public void run(String... args) throws Exception {
        cidadeService.listarCidadesPorNomeSQL();
    }

}
