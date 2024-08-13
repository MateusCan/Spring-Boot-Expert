package br.com.udemy.springexpert.exception;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
    }

    public SenhaInvalidaException(String message) {
        super(message);
    }

    public SenhaInvalidaException(String message, Throwable cause) {
        super(message, cause);
    }

    public SenhaInvalidaException(Throwable cause) {
        super(cause);
    }

    public SenhaInvalidaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
