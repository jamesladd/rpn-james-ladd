package com.jamesladd.calculator;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

class NumberAtom extends Atom {

    private BigDecimal number;

    NumberAtom(int position, String number) throws NumberFormatException {
        this(position, new BigDecimal(number));
    }

    private NumberAtom(int position, BigDecimal number) {
        super(position);
        this.number = number;
    }

    public String toString() {
        // TODO.JCL - Lookup proper way to round BigDecimal to
        BigDecimal temp = new BigDecimal(number.toPlainString());
        return truncate(temp.setScale(10, RoundingMode.DOWN).toPlainString());
    }

    private String truncate(String string) {
        int decimal = string.indexOf('.');
        if (decimal != -1) {
            // TODO.JCL - THIS IS NOT RIGHT!! BUT I WANT TO HAND THIS IN A>S>A>P
            String truncated = string;
            while (truncated.endsWith("0")) {
                truncated = truncated.substring(0, truncated.length() -1);
            }
            if (truncated.endsWith(".")) {
                truncated = truncated.substring(0, truncated.length() -1);
            }
            return truncated;
        }
        return string;
    }

    @Override
    void push(AtomStack stack, PrintStream out) {
        stack.push(this);
    }

    NumberAtom apply(Function<BigDecimal, BigDecimal> consumer) {
        return new NumberAtom(position(), consumer.apply(number));
    }

    BigDecimal xApply(Function<BigDecimal, BigDecimal> consumer) {
        return consumer.apply(number);
    }

    @Override
    void printOn(PrintStream out) {
        out.print(toString());
    }
}
