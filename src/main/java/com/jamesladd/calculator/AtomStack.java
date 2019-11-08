package com.jamesladd.calculator;

import java.io.PrintStream;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

class AtomStack {

    private Stack<Atom> stack = new Stack<>();
    private Stack<Atom> resetStack = new Stack<>();

    void applyAll(AtomStream atomStream, PrintStream out) {
        atomStream.applyAll(this, out);
    }

    void printOn(PrintStream out) {
        StringBuffer output = new StringBuffer();
        out.print("stack: ");
        stack.forEach(a -> output.append(a).append(' '));
        out.println(withoutTrailingSpace(output));
    }

    private String withoutTrailingSpace(StringBuffer output) {
        return (output.length() > 1) ? output.substring(0, output.length() - 1) : "";
    }

    void apply(Atom atom, PrintStream out) {
        atom.push(this, out);
    }

    void push(Atom atom) {
        stack.push(atom);
    }

    void clear() {
        stack.clear();
    }

    void applyOr(Consumer<NumberAtom> consumer, Function<Void, Void> otherwise) throws AtomStackException {
        resetResetStack();
        try {
            Atom top = pop();
            consumer.accept((NumberAtom) top);
        } catch (Exception e) {
            resetStack();
            otherwise.apply(null);
            throw new AtomStackException(e);
        }
    }

    void applyOr(BiConsumer<NumberAtom, NumberAtom> consumer, Function<Void, Void> otherwise) throws AtomStackException {
        resetResetStack();
        try {
            Atom first = pop();
            Atom second = pop();
            consumer.accept((NumberAtom) second, (NumberAtom) first);
        } catch (Exception e) {
            resetStack();
            otherwise.apply(null);
            throw new AtomStackException(e);
        }
    }

    private Atom pop() {
        Atom atom = stack.pop();
        resetStack.push(atom);
        return atom;
    }

    private void resetResetStack() {
        resetStack.clear();
    }

    private void resetStack() {
        while (resetStack.size() > 0) {
            stack.push(resetStack.pop());
        }
        resetResetStack();
    }

    void undo() {
        stack.pop();
        if (resetStack.size() > 0) {
            resetStack();
        }
    }
}
