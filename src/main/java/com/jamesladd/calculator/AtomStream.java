package com.jamesladd.calculator;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

class AtomStream {

    private final List<Atom> list = new ArrayList<>();

    AtomStream(Stream<Atom> stream) {
        stream.forEach(list::add);
    }

    void applyAll(AtomStack stack, PrintStream out) {
        this.forEach((atom) -> stack.apply(atom, out));
    }

    private void forEach(Consumer<? super Atom> consumer) {
        try {
            list.forEach(consumer);
        } catch (Exception e) {
            // Ignored.
        }
    }
}
