package com.aleksej.calculator.calculator;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Алексей on 27.10.2015.
 */
public class Parser  {
    public Double parse(String exp) throws ParseException {
        exp = check(exp);
        Stack<Character> stack = new Stack<Character>();
        ArrayList<String> lst = new ArrayList<String>();
        int j = 0;
        boolean flage = true;
        for (int i = 0; i < exp.length(); i++) {
            char ch = exp.charAt(i);
            if (checkAction(ch)) {
                if (!(flage && unar(exp, i))) {
                    lst.add(exp.substring(j, i));
                    j = i + 1;
                    while (!stack.isEmpty() && priority(stack.peek()) >= priority(ch)) {
                        lst.add(stack.pop() + "");
                    }
                    stack.push(ch);
                    flage = true;
                    continue;
                } else {
                    flage = true;
                    if (ch == '-') {
                        if (isNumber(exp.charAt(i + 1))) {
                            continue;
                        }
                        stack.push('#');
                    } else
                        stack.push(ch);
                    j++;
                    continue;
                }
            }
            flage = false;
        }
        if (!exp.substring(j).isEmpty()) {
            lst.add(exp.substring(j));
        }
        while (!stack.isEmpty()) {
            lst.add(stack.pop() + "");
        }
        Stack<Double> stack1 = new Stack<Double>();
        for (int i = 0; i < lst.size(); i++) {
            String str = lst.get(i);
            if (str.isEmpty())
                continue;
            if (checkNumber(str)) {
                stack1.push(constOrVariable(lst.get(i)));
                continue;
            }
            Double action = null, action1, action2;
            action1 = pop(stack1);
            switch (str.charAt(0)) {
                case '+':
                    action2 = pop(stack1);
                    action = action1+action2;
                    break;
                case '-':
                    action2 = pop(stack1);
                    action = action2-action1;
                    break;
                case '*':
                    action2 = pop(stack1);
                    action = action2*action1;
                    break;
                case '/':
                    action2 = pop(stack1);
                    action = action2/action1;
                    break;
                case '#':
                    action = -action1;
                    break;
            }
            stack1.push(action);
        }
        return pop(stack1);
    }

    private boolean unar(String str, int i) {
        if (str.charAt(i) == 'a' || str.charAt(i) == 's')
            return true;
        if (str.charAt(i) == '-') {
            if (i == 0 || str.charAt(i - 1) == '(') {
                return true;
            }
            if (str.charAt(i - 1) == ')') {
                return false;
            }
            return true;
        } else
            return false;
    }

    private Double constOrVariable(String str) {
        try {
            Double a=Double.parseDouble(str);
            return a;
        } catch (Exception e) {
            return 0.0;
        }
    }

    private boolean checkAction(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private boolean checkNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private int priority(char ch) {
        switch (ch) {
            case '*':
            case '/':
                return 3;
            case '+':
            case '-':
                return 2;
            case '#':
                return 5;
            default:
                return -1;
        }
    }

    private boolean isNumber(char ch) {
        try {
            if ((ch >= '0' && ch <= '9') || ch=='.')
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    private String check(String exp) throws ParseException{
        // System.out.println(exp);
        int n = exp.length();
        char[] mass = new char[n];
        int j = 0;
        if (n == 0)
          return "0";

        boolean expectNumber = true;
        for (int i = 0; i < n; i++) {
            char ch = exp.charAt(i);
            if (!checkAction(ch)) {
                if ((ch >= '0' && ch <= '9') || ch=='.' ) {
                    expectNumber = false;
                    mass[j++] = ch;
                    continue;
                }
            } else {
                if (expectNumber && ch != '-') {
                    throw new ParseException();
                }
                mass[j++] = ch;
                expectNumber = true;
            }
        }
        if (expectNumber) {
           throw new ParseException();
        }
        char[] back = new char[j];
        for (int i = 0; i < j; i++) {
            back[i] = mass[i];
        }
        return String.valueOf(back);
    }

    private void check(Stack<Double> stack) throws ParseException {
        if (stack.isEmpty())
            throw new ParseException();
    }

    private Double pop(Stack<Double> stack) throws ParseException {
        check(stack);
        return stack.pop();
    }
}

class ParseException extends Exception
{
    public ParseException()
    {
        super("Error in Expression");
    }
}
