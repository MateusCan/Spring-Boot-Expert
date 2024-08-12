package br.com.udemy.springexpert.exception;

public class PedidoNaoEncontradoException extends RuntimeException {

    public PedidoNaoEncontradoException(String s) {
        super(s);
    }
}
