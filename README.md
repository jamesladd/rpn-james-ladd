# preferred-calculator

An RPN Calculator. 

Developed from bottom up with the idea of writing the simplest thing that could possibly work, and adding test
after. Typically I would have used Antlr for this to reduce code written to test and maintain.

NOTE: Example 8 is currently @Ignored in tests as I have a question about it.

To run tests:
```
mvn clean test
```

To run calculator:
```
mvn clean test
java -classpath target/classes com.jamesladd.App
```
