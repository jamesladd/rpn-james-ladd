package com.jamesladd;

import com.jamesladd.calculator.Calculator;
import com.jamesladd.calculator.CalculatorException;
import com.jamesladd.support.CapturingPrintStream;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Acceptance tests for RPN Calculator App
 *
 * Typically I would use a Dependency Injection framework like Dagger2 / Spring.
 * Doing it manually here example / exercise purposes.
 * 
 */
public class AcceptanceTest {

    private Calculator calculator;
    private CapturingPrintStream capturedOutput;

    @Before
    public void setup() {
        capturedOutput = new CapturingPrintStream(System.out);
        calculator = new Calculator();
    }

    @Test
    public void shouldHandleExample1() throws CalculatorException {
        String input = "5 2";
        String expected = "stack: 5 2\n";
        calculate(input);
        assertEquals(expected, capturedOutput.toString());
    }

    @Test
    public void shouldHandleExample2() throws CalculatorException {
        String[] input = {
            "2 sqrt",
            "clear 9 sqrt"
        };
        String[] expected = {
            "stack: 1.4142135623\n",
            "stack: 3\n"
        };
        calculate(input[0]);
        assertEquals(expected[0], capturedOutput.toString());
        clearCaputredOutput();
        calculate(input[1]);
        assertEquals(expected[1], capturedOutput.toString());
    }

    private void clearCaputredOutput() {
        capturedOutput.clear();
    }

    @Test
    public void shouldHandleExample3() throws CalculatorException {
        String[] input = {
            "5 2 -",
            "3 -",
            "clear"
        };
        String[] expected = {
            "stack: 3\n",
            "stack: 0\n",
            "stack: \n"
        };
        calculate(input[0]);
        assertEquals(expected[0], capturedOutput.toString());
        clearCaputredOutput();
        calculate(input[1]);
        assertEquals(expected[1], capturedOutput.toString());
        clearCaputredOutput();
        calculate(input[2]);
        assertEquals(expected[2], capturedOutput.toString());
    }

    @Test
    public void shouldHandleExample4() throws CalculatorException {
        String[] input = {
            "5 4 3 2",
            "undo undo *",
            "5 *",
            "undo"
        };
        String[] expected = {
            "stack: 5 4 3 2\n",
            "stack: 20\n",
            "stack: 100\n",
            "stack: 20 5\n"
        };
        calculate(input[0]);
        assertEquals(expected[0], capturedOutput.toString());
        clearCaputredOutput();
        calculate(input[1]);
        assertEquals(expected[1], capturedOutput.toString());
        clearCaputredOutput();
        calculate(input[2]);
        assertEquals(expected[2], capturedOutput.toString());
        clearCaputredOutput();
        calculate(input[3]);
        assertEquals(expected[3], capturedOutput.toString());
    }

    @Test
    public void shouldHandleExample5() throws CalculatorException {
        String[] input = {
            "7 12 2 /",
            "*",
            "4 /"
        };
        String[] expected = {
            "stack: 7 6\n",
            "stack: 42\n",
            "stack: 10.5\n"
        };
        calculate(input[0]);
        assertEquals(expected[0], capturedOutput.toString());
        clearCaputredOutput();
        calculate(input[1]);
        assertEquals(expected[1], capturedOutput.toString());
        clearCaputredOutput();
        calculate(input[2]);
        assertEquals(expected[2], capturedOutput.toString());
    }

    @Test
    public void shouldHandleExample6() throws CalculatorException {
        String[] input = {
            "1 2 3 4 5",
            "*",
            "clear 3 4 -"
        };
        String[] expected = {
            "stack: 1 2 3 4 5\n",
            "stack: 1 2 3 20\n",
            "stack: -1\n"
        };
        calculate(input[0]);
        assertEquals(expected[0], capturedOutput.toString());
        clearCaputredOutput();
        calculate(input[1]);
        assertEquals(expected[1], capturedOutput.toString());
        clearCaputredOutput();
        calculate(input[2]);
        assertEquals(expected[2], capturedOutput.toString());
    }

    @Test
    public void shouldHandleExample7() throws CalculatorException {
        String[] input = {
            "1 2 3 4 5",
            "* * * *"
        };
        String[] expected = {
            "stack: 1 2 3 4 5\n",
            "stack: 120\n"
        };
        calculate(input[0]);
        assertEquals(expected[0], capturedOutput.toString());
        clearCaputredOutput();
        calculate(input[1]);
        assertEquals(expected[1], capturedOutput.toString());
    }

    @Test
    public void shouldHandleExample8() throws CalculatorException {
        String input = "1 2 3 * 5 + * * 6 5";
        String expected =
                "operator * (position: 15): insufficient parameters\n" +
                "stack: 11\n";
        calculate(input);
        assertEquals(expected, capturedOutput.toString());
    }

    private void calculate(String inputource) throws CalculatorException {
        calculator.calculate(inputource, capturedOutput);
    }
}
