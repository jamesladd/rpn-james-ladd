package com.jamesladd.calculator;

public class AtomStackException extends RuntimeException {

    AtomStackException(Exception cause) {
        super(cause.getMessage());
    }
}
