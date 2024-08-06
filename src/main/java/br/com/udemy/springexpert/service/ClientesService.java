package br.com.udemy.springexpert.service;

import br.com.udemy.springexpert.model.Cliente;
import br.com.udemy.springexpert.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientesService {

    //@Autowired
    private ClientesRepository repository; //INJEÇÃO DE DEPENDENCIAS PODE SER FEITO DIRETO NA INSTANCIA OU NO CONSTRUTOR

    @Autowired //Não precisa do autowired, ele ja compreende que é uma injeção
    public ClientesService(ClientesRepository repository) {
        this.repository = repository;}

    public void salvarCliente(Cliente cliente){
        validarCliente(cliente);
        this.repository.persistir(cliente);
    }

    public void validarCliente(Cliente cliente){

    }
}
