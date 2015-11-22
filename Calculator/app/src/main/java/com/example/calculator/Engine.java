package com.example.calculator;

import java.util.HashMap;
import java.util.Map;

public class Engine {
    private final static Map<Operation, Character> operationToCharacter = new HashMap<>();
    private final static Map<Character, Operation> characterToOperation = new HashMap<>();

    static {
        operationToCharacter.put(Operation.ADD, '+');
        operationToCharacter.put(Operation.SUBTRACT, '-');
        operationToCharacter.put(Operation.MULTIPLY, '*');
        operationToCharacter.put(Operation.DIVIDE, '/');
        characterToOperation.put('+', Operation.ADD);
        characterToOperation.put('-', Operation.SUBTRACT);
        characterToOperation.put('*', Operation.MULTIPLY);
        characterToOperation.put('/', Operation.DIVIDE);
    }

    private StringBuilder first;
    private StringBuilder second;
    private Operation operation;
    private Boolean isEvaluated;

    public Engine() {
        clear();
    }

    public void clear() {
        first = new StringBuilder("0");
        second = null;
        operation = Operation.NONE;
        isEvaluated = false;
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        ans.append(first.toString().replaceFirst("^0+(?!$)", "")); // delete leading zeros
        if (operation != Operation.NONE && !isEvaluated) {
            ans.append(operationToCharacter.get(operation));
            if (second != null) {
                ans.append(second.toString().replaceFirst("^0+(?!$)", "")); // delete leading zeros
            }
        }
        return ans.toString();
    }

    public void evaluate() {
        isEvaluated = true;
        final double a = Double.parseDouble(first.toString());
        final double b = Double.parseDouble(second.toString());
        final double ans;
        if (operation == Operation.ADD) {
            ans = a + b;
        } else if (operation == Operation.SUBTRACT) {
            ans = a - b;
        } else if (operation == Operation.MULTIPLY) {
            ans = a * b;
        } else if (operation == Operation.DIVIDE) {
            ans = a / b;
        } else {
            ans = a;
        }
        first = new StringBuilder(String.valueOf(ans));
    }

    public boolean isCorrectNumber(StringBuilder s, char token) {
        try {
            Double.parseDouble(s.toString() + token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void put(char token) {
        if (token == 'C') {
            clear();
        } else if (Character.isDigit(token) || token == '.') {
            if (isEvaluated) {
                clear();
            }
            if (operation == Operation.NONE) {
                if (isCorrectNumber(first, token)) {
                    first.append(token);
                }
            } else {
                if (second == null) {
                    second = new StringBuilder("0");
                }
                if (isCorrectNumber(second, token)) {
                    second.append(token);
                }
            }
        } else if (token == '=') {
            if (operation != Operation.NONE) {
                evaluate();
            }
        } else if ("+-*/".indexOf(token) >= 0) {
            if (isEvaluated) {
                isEvaluated = false;
                second = null;
                operation = Operation.NONE;
            }
            if (operation != Operation.NONE && second != null) {
                evaluate();
                isEvaluated = false;
                second = null;
            }
            operation = characterToOperation.get(token);
        } else if (token == '±') {
            // TODO: Negate Operation
        }
    }

    private enum Operation {ADD, SUBTRACT, MULTIPLY, DIVIDE, NONE}
}
