package com.jamesladd.calculator;

/**
 * TODO.JCL - discuss making this a runtime exception so the 'throws' is not needed on every signature.
 */
public class CalculatorException extends Exception {

    CalculatorException(Exception cause) {
        super(cause.getMessage());
    }
}
