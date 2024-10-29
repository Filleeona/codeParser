package com.example.codeexecutor;

import java.util.HashMap;
import java.util.Map;

public class Parser {

    private String expression;
    private int index;
    private Map<String, Integer> variables = new HashMap<>();

    public Parser(String expression) {
        this.expression = expression.replaceAll("\\s+", ""); // Удаляем пробелы
        this.index = 0;
    }

    public boolean parseAndEvaluate() {
        boolean valid = parseExpression();
        return valid && index == expression.length();
    }

    public int getVariableValue(String variable) {
        return variables.getOrDefault(variable, 0);
    }

    private boolean parseExpression() {
        if (index < expression.length() && Character.isLetter(expression.charAt(index))) {
            String variable = parseVariable();
            if (index < expression.length() && expression.charAt(index) == '=') {
                index++;
                int value = evaluateMathExpression();
                variables.put(variable, value); // Сохраняем значение переменной
                return true;
            }
        } else {
            evaluateMathExpression(); // Выполняем только математическое выражение
        }
        return true;
    }

    private String parseVariable() {
        StringBuilder variable = new StringBuilder();
        while (index < expression.length() && Character.isLetter(expression.charAt(index))) {
            variable.append(expression.charAt(index));
            index++;
        }
        return variable.toString();
    }

    private int evaluateMathExpression() {
        int value = parseTerm();
        while (index < expression.length()) {
            char operator = expression.charAt(index);
            if (operator == '+' || operator == '-') {
                index++;
                int nextValue = parseTerm();
                if (operator == '+') {
                    value += nextValue;
                } else {
                    value -= nextValue;
                }
            } else {
                break;
            }
        }
        return value;
    }

    private int parseTerm() {
        int value = parseFactor();
        while (index < expression.length()) {
            char operator = expression.charAt(index);
            if (operator == '*' || operator == '/') {
                index++;
                int nextValue = parseFactor();
                if (operator == '*') {
                    value *= nextValue;
                } else if (nextValue != 0) {
                    value /= nextValue;
                } else {
                    throw new ArithmeticException("Division by zero");
                }
            } else {
                break;
            }
        }
        return value;
    }

    private int parseFactor() {
        if (index < expression.length()) {
            if (expression.charAt(index) == '-') {
                index++;
                return -parsePrimary();
            }
            return parsePrimary();
        }
        throw new IllegalArgumentException("Invalid expression");
    }

    private int parsePrimary() {
        if (index < expression.length()) {
            char current = expression.charAt(index);
            if (Character.isDigit(current)) {
                return parseNumber();
            } else if (Character.isLetter(current)) {
                return parseVariableValue(); // Возвращаем значение переменной
            } else if (current == '(') {
                index++; // Пропустить открывающую скобку
                int value = evaluateMathExpression();
                if (index >= expression.length() || expression.charAt(index) != ')') {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
                index++; // Пропустить закрывающую скобку
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid character in expression");
    }

    private int parseNumber() {
        int start = index;
        while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
            index++;
        }
        return Integer.parseInt(expression.substring(start, index));
    }

    private int parseVariableValue() {
        String variable = parseVariable();
        return variables.getOrDefault(variable, 0); // Возвращаем значение переменной
    }
}


