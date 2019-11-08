package com.jamesladd.calculator;

import java.io.PrintStream;

public class Calculator {

    private final AtomStack atomStack;
    private final AtomFactory atomFactory;

    /**
     * Default constructor for typical use.
     */
    public Calculator() {
        this(new AtomStack(), new AtomFactory());
    }

    /**
     * Non-Default constructor for testing use. ie: dependencies.
     */
    public Calculator(AtomStack atomStack, AtomFactory atomFactory) {
        this.atomStack = atomStack;
        this.atomFactory = atomFactory;
    }

    public void calculate(String input, PrintStream out) throws CalculatorException {
        AtomStream atomStream = new AtomStreamBuilder(atomFactory).buildFrom(input);
        atomStack.applyAll(atomStream, out);
        atomStack.printOn(out);
    }
}
