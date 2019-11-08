package com.jamesladd.calculator;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * OperationAtom provides all the calculator operations.
 *
 * Operations are provided as Lambdas which ensure the Atom is immutable, and
 * to ensure where a value is needed the 'business' logic is captured in a single place.
 */
class OperationAtom extends Atom {

    private static final Map<String, TriConsumer<OperationAtom, AtomStack, PrintStream>> OPERATIONS;
    static {
        Map<String, TriConsumer<OperationAtom, AtomStack, PrintStream>> aMap = new HashMap<>();
        aMap.put("", OperationAtom::noOp);
        aMap.put("+", OperationAtom::addition);
        aMap.put("-", OperationAtom::subtraction);
        aMap.put("*", OperationAtom::multiplication);
        aMap.put("/", OperationAtom::division);
        aMap.put("sqrt", OperationAtom::squareRoot);
        aMap.put("undo", OperationAtom::undo);
        aMap.put("clear", OperationAtom::clear);
        OPERATIONS = Collections.unmodifiableMap(aMap);
    }

    private static void noOp(OperationAtom atom, AtomStack stack, PrintStream out) {
        out.print("WARNING: ");
        atom.printOn(out);
        out.println(" is unknown. Ignoring.");
    }

    private static void addition(OperationAtom atom, AtomStack stack, PrintStream out) {
        stack.applyOr((arg1, arg2) -> {
            Atom result = add(arg1, arg2);
            stack.push(result);
        }, (Void) -> insufficientParameters(stack, atom, out));
    }

    private static void subtraction(OperationAtom atom, AtomStack stack, PrintStream out) {
        stack.applyOr((arg1, arg2) -> {
            Atom result = subtract(arg1, arg2);
            stack.push(result);
        }, (Void) -> insufficientParameters(stack, atom, out));
    }

    private static void multiplication(OperationAtom atom, AtomStack stack, PrintStream out) {
        stack.applyOr((arg1, arg2) -> {
            Atom result = multiply(arg1, arg2);
            stack.push(result);
        }, (Void) -> insufficientParameters(stack, atom, out));
    }

    private static void division(OperationAtom atom, AtomStack stack, PrintStream out) {
        stack.applyOr((arg1, arg2) -> {
            Atom result = divide(arg1, arg2);
            stack.push(result);
        }, (Void) -> insufficientParameters(stack, atom, out));
    }

    private static void squareRoot(OperationAtom atom, AtomStack stack, PrintStream out) {
        stack.applyOr((arg) -> {
            Atom result = squareRoot(arg);
            stack.push(result);
        }, (Void) -> insufficientParameters(stack, atom, out));
    }

    private static Void insufficientParameters(AtomStack stack, OperationAtom atom, PrintStream out) {
        atom.printOn(out);
        out.println(": insufficient parameters");
        return null;
    }

    private static void undo(OperationAtom atom, AtomStack stack, PrintStream out) {
        stack.undo();
    }

    private static void clear(OperationAtom atom, AtomStack stack, PrintStream out) {
        stack.clear();
    }

    /**
     * actual operation is moved out of the above lambda to make both less dense and
     * easier to comprehend.
     */
    private static NumberAtom add(NumberAtom arg1, NumberAtom arg2) {
        return arg2.apply((right) -> arg1.xApply((left) -> left.add(right)));
    }

    private static NumberAtom subtract(NumberAtom arg1, NumberAtom arg2) {
        return arg2.apply((right) -> arg1.xApply((left) -> left.subtract(right)));
    }

    private static NumberAtom multiply(NumberAtom arg1, NumberAtom arg2) {
        return arg2.apply((right) -> arg1.xApply((left) -> left.multiply(right)));
    }

    private static NumberAtom divide(NumberAtom arg1, NumberAtom arg2) {
        return arg2.apply((right) -> arg1.xApply((left) -> left.divide(right, 10, RoundingMode.CEILING)));
    }

    private static NumberAtom squareRoot(NumberAtom arg) {
        return arg.apply((number) -> BigDecimal.valueOf(StrictMath.sqrt(number.doubleValue())));
    }

    private final String value;

    OperationAtom(int position, String value) {
        super(position);
        this.value = value;
    }

    @Override
    void push(AtomStack stack, PrintStream out) {
        OPERATIONS.getOrDefault(value, OPERATIONS.get(""))
                  .accept(this, stack, out);
    }

    @Override
    void printOn(PrintStream out) {
        out.print("operator ");
        out.print(value);
        out.print(" (position: ");
        out.print(position());
        out.print(")");
    }
}
