package expression;

/**
 * Created by daniil on 24.03.15.
 */

import expression.exceptions.IntegerOverflowException;
import expression.exceptions.ParserException;
import expression.exceptions.UnexpectedEndOfExpressionException;
import expression.exceptions.UnexpectedTermException;
import expression.types.MyNumber;

public class CheckedParser<T extends MyNumber<T>> implements Parser<T> {
    private String myExpression;
    private int positionInExp;
    private String curTerm;
    private T example;

    public CheckedParser(T example) {
        this.example = example;
    }

    private void nextTerm() throws ParserException {
        StringBuilder sub = new StringBuilder();
        Character x = myExpression.charAt(positionInExp);
        int j;
        if (Character.isWhitespace(x)) {
            j = positionInExp;
            x = myExpression.charAt(j);
            while (Character.isWhitespace(x)) {
                j++;
                x = myExpression.charAt(j);
            }
            positionInExp = j;
        }

        x = myExpression.charAt(positionInExp);
        if (x == '+' || x == '-' || x == '*' || x == '/' || x == '^') {
            if (x == '*' || x == '/') {
                j = positionInExp;
                x = myExpression.charAt(j);
                while (x == '*' || x == '/') {
                    sub.append(x);
                    j++;
                    x = myExpression.charAt(j);
                }
                curTerm = sub.toString();
                x = curTerm.charAt(0);
                positionInExp = j;
                if (curTerm.length() != 1) {
                    throw new UnexpectedTermException("Unexpected term: " + curTerm + ".");
                }
            } else {
                curTerm = x.toString();
                positionInExp++;
            }
        } else if (x == '(' || x == ')') {
            curTerm = x.toString();
            positionInExp++;
        } else if (Character.isAlphabetic(x) || Character.isDigit(x)) {
            j = positionInExp;
            boolean correct = true;
            boolean havePoint = false;
            while (!Character.isWhitespace(x) && x != '#' && x != '-' && x != '(' && x != ')' && x != '+' && x != '*'
                    && x != '/' && x != '^') {
                sub.append(x);
                if (sub.length() > 1) {
                    if (Character.isAlphabetic(myExpression.charAt(j - 1))) {
                        if (!Character.isAlphabetic(myExpression.charAt(j))) {
                            correct = false;
                        }
                    }
                    if (Character.isDigit(myExpression.charAt(j - 1))) {
                        if (!Character.isDigit(myExpression.charAt(j))) {
                            if (myExpression.charAt(j) == '.') {
                                if (havePoint) {
                                    correct = false;
                                } else {
                                    havePoint = true;
                                }
                            }

                        }
                    }
                    if(myExpression.charAt(j - 1) == '.' && myExpression.charAt(j) == '.') {
                        correct = false;
                    }
                }
                j++;
                x = myExpression.charAt(j);
            }
            curTerm = sub.toString();
            positionInExp = j;
            if (!correct) {
                throw new UnexpectedTermException("Unexpected term: " + curTerm + ".");
            }
        } else if (x == '#') {
            curTerm = "#";
        } else {
            while (!Character.isWhitespace(myExpression.charAt(positionInExp)) && myExpression.charAt(positionInExp) != '#') {
                sub.append(myExpression.charAt(positionInExp));
                positionInExp++;
            }
            curTerm = sub.toString();
            throw new UnexpectedTermException("Unexpected term: " + curTerm + ".");
        }
    }

    public Expression<T> parse(String expression) throws ParserException {
        myExpression = expression + "#";
        positionInExp = 0;
        nextTerm();
        Expression<T> result = getThirdPriorityResult();
        if (!curTerm.equals("#")) {
            throw new UnexpectedTermException("Unexpected term: " + curTerm + ".");
        }
        return result;
    }


    private Expression<T> getThirdPriorityResult() throws ParserException {
        Expression<T> left = getSecondPriorityResult();
        while (curTerm.equals("+") || curTerm.equals("-")) {
            String term = curTerm;
            nextTerm();
            char x = curTerm.charAt(0);
            if (!(Character.isDigit(x) || Character.isAlphabetic(x) || x == '(' || x == '-')) {
                if (x == '#') {
                    throw new UnexpectedEndOfExpressionException("Unexpected end of expression");
                } else {
                    throw new UnexpectedTermException("Unexpected term: " + curTerm + ".");
                }
            }
            Expression<T> right = getSecondPriorityResult();
            switch (term) {
                case "+":
                    left = new CheckedAdd<>(left, right);
                    break;
                case "-":
                    left = new CheckedSubtract<>(left, right);
                    break;
            }

        }
        return left;
    }

    private Expression<T> getSecondPriorityResult() throws ParserException {
        Expression<T> left = getFirstPriorityResult();
        while (curTerm.equals("*") || curTerm.equals("/") || curTerm.equals("^")) {
            String term = curTerm;
            nextTerm();
            char x = curTerm.charAt(0);
            if (!(Character.isDigit(x) || Character.isAlphabetic(x) || x == '(' || x == '-'
                    || x == '*' || x == '/')) {
                if (x == '#') {
                    throw new UnexpectedEndOfExpressionException("Unexpected end of expression.");
                } else {
                    throw new UnexpectedTermException("Unexpected term: " + curTerm + ".");
                }
            }
            Expression<T> right = getFirstPriorityResult();
            switch (term) {
                case "*":
                    left = new CheckedMultiply<>(left, right);
                    break;
                case "/":
                    left = new CheckedDivide<>(left, right);
                    break;
                case "^":
                    left = new CheckedPow<>(left, right);
                    break;
            }
        }
        return left;
    }

    private Expression<T> getFirstPriorityResult() throws ParserException {
        Expression<T> left;
        char x;
        switch (curTerm) {
            case "(":
                nextTerm();
                left = getThirdPriorityResult();
                if (!curTerm.equals(")")) {
                    if (curTerm.equals("#")) {
                        throw new UnexpectedTermException("Unexpected end of expression. ')' expected.");
                    } else {
                        throw new UnexpectedTermException("Unexpected term: " + curTerm + ". ')' expected.");
                    }
                }
                nextTerm();
                break;
            case "-":
                nextTerm();
                x = curTerm.charAt(0);
                if (Character.isDigit(x) || Character.isAlphabetic(x) || x == '('
                        || x == '-') {
                    left = new CheckedNegate<>(getFirstPriorityResult());
                } else {
                    throw new UnexpectedTermException("Unexpected term: " + curTerm + ".");
                }
                break;
            case "abs":
                nextTerm();
                left = new CheckedABS<>(getFirstPriorityResult());
                break;
            case "square":
                nextTerm();
                left = new CheckedSquare<>(getFirstPriorityResult());
                break;
            case "#":
                throw new UnexpectedEndOfExpressionException("Unexpected end of expression");
            default:
                try {
                    example = example.newNumber(curTerm);
                    left = new Const<>(example.newNumber(curTerm));
                } catch (Exception e) {
                    throw new IntegerOverflowException("Bad number");
                }
                nextTerm();
                break;
        }
        return left;
    }

}

