package ite.jp.ak.lab03.client.exceptions;

public class FxmlLoadException extends Exception {
    public FxmlLoadException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
