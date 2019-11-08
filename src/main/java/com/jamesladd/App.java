package com.jamesladd;

import com.jamesladd.calculator.*;

import java.util.Scanner;

/**
 * A Command Line RPN Calculator.
 */
public class App {
    public static void main(String[] args) {
        System.out.println( "Calculator running...Press Ctrl-C to end." );
        System.out.println( "Enter your calculation and press RETURN to calculate." );
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();
        try {
            while (true) {
                String input = scanner.nextLine();
                calculator.calculate(input, System.out);
            }
        } catch (CalculatorException e) {
            e.printStackTrace(System.err);
        }
    }
}
