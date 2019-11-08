package com.jamesladd.support;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Captures output written to the stream.
 * toString() returns the captured data as a String.
 *
 * NOTE: May not handle unicode characters - see write(int i)
 */

public class CapturingPrintStream extends PrintStream {

    private StringBuffer buffer = new StringBuffer();

    public CapturingPrintStream(OutputStream out) {
        super(out);
    }

    public void print(String s) {
        buffer.append(s);
    }

    public void print(int i) {
        buffer.append(i);
    }

    public void println(String s) {
        buffer.append(s).append('\n');
    }

    public String toString() {
        return buffer.toString();
    }

    public void clear() {
        buffer = new StringBuffer();
    }
}
