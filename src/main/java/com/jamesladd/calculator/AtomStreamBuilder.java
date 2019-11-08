package com.jamesladd.calculator;

import java.util.stream.Stream;

public class AtomStreamBuilder {

    private final AtomFactory factory;

    public AtomStreamBuilder(AtomFactory factory) {
        this.factory = factory;
    }

    public AtomStream buildFrom(String input) throws CalculatorException {
        Stream.Builder<Atom> builder = Stream.builder();
        String source = input != null ? input : "";
        parseAtoms(source, builder);
        return new AtomStream(builder.build());
    }

    /**
    * TODO.JCL - cater for mal formed numbers ie: two periods '.' etc
    */
    private void parseAtoms(String input, Stream.Builder<Atom> builder) throws CalculatorException {
        String source = input.trim() + " ";
        StringBuffer buffer = new StringBuffer();
        int position = 1;
        for (int index = 0; index < source.length(); index++) {
            char ch = source.charAt(index);
            buffer.append(ch);
            if (ch == ' ') {
                builder.add(createAtom(position, buffer.toString()));
                position = index + 2;
                buffer.delete(0, buffer.length());
            }
        }
    }

    private Atom createAtom(int position, String value) throws CalculatorException {
        return factory.create(position, value.trim());
    }
}
