package br.com.udemy.springexpert.exception;

public class RegraNegocioException extends RuntimeException{

    public RegraNegocioException(String message) {
        super(message);
    }
}
