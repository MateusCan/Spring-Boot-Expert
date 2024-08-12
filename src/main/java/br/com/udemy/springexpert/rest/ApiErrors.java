package br.com.udemy.springexpert.rest;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class ApiErrors {

    private List<String> erros;

    public ApiErrors(List<String> erros) {
        this.erros = erros;
    }

    public ApiErrors(String MensagemErro) {
        this.erros = Collections.singletonList(MensagemErro);
    }
}
