package com.jamesladd;

import com.jamesladd.calculator.Calculator;
import com.jamesladd.calculator.CalculatorException;
import com.jamesladd.support.CapturingPrintStream;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * EdgeCase tests for RPN Calculator App
 *
 * This is not an exhaustive list.
 */
public class EdgeCasesTest {

    private Calculator calculator;
    private CapturingPrintStream capturedOutput;

    @Before
    public void setup() {
        capturedOutput = new CapturingPrintStream(System.out);
        calculator = new Calculator();
    }

    @Test(expected = CalculatorException.class)
    public void shouldHandleInvalidNumbers() throws CalculatorException {
        String input = "5.4g 2";
        calculate(input);
    }

    @Test
    public void shouldHandleEmptyInput() throws CalculatorException {
        String input = "";
        String expected = "WARNING: operator  (position: 1) is unknown. Ignoring.\nstack: \n";
        calculate(input);
        assertEquals(expected, capturedOutput.toString());
    }

    private void calculate(String inputource) throws CalculatorException {
        calculator.calculate(inputource, capturedOutput);
    }
}
