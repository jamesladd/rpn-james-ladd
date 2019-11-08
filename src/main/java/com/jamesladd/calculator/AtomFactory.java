package com.jamesladd.calculator;

public class AtomFactory {

    Atom create(int position, String value) throws CalculatorException {
        String sanitizedValue = sanitize(value);
        if (startsWithNumber(sanitizedValue)) {
            return createNumberAtom(position, sanitizedValue);
        }
        return createOperationAtom(position, sanitizedValue);
    }

    private static String sanitize(String value) {
        return value == null ? "" : value;
    }

    private static OperationAtom createOperationAtom(int position, String value) {
        return new OperationAtom(position, value);
    }

    private static NumberAtom createNumberAtom(int position, String value) throws CalculatorException {
        try {
            return new NumberAtom(position, value);
        } catch (NumberFormatException e) {
            throw new CalculatorException(e);
        }
    }

    private static boolean startsWithNumber(String value) {
        if ("".endsWith(value)) {
            return false;
        }
        char firstChar = value.charAt(0);
        return "0123456789".indexOf(firstChar) != -1;
    }
}
