package com.jamesladd.calculator;

import java.io.PrintStream;

abstract class Atom {

    private final int position;

    Atom(int position) {
        this.position = position;
    }

    abstract void push(AtomStack stack, PrintStream out);

    abstract void printOn(PrintStream out);

    int position() {
        return position;
    }
}
