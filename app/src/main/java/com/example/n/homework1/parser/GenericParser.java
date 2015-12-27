package com.example.n.homework1.parser;

public class GenericParser<T extends GenericNumber<T>> implements Parser<T> {

    private Lexeme curLexeme;
    private Lexeme prevLexeme;
    private String expression;
    private String name;
    private T value;
    private int point;
    private int open;

    public GenericExpression<T> parse(String expression, T value) throws Exception {
        this.expression = expression;
        point = 0;
        open = 0;
        curLexeme = null;
        this.value = value;
        nextLexeme();
        if (curLexeme.type == Lexeme.Type.BINARY) {
            throw new Exception("No first argument");
        }
        GenericExpression<T> expr = expression();
        if (curLexeme != Lexeme.END) {
            throw new Exception("No open parenthesis");
        }
        return expr;
    }

    private void nextLexeme() throws Exception {
        skipSpaces();
        if (point >= expression.length()) {
            switch (curLexeme.type) {
                case BINARY:
                    throw new Exception("No last argument");
                case UNARY:
                    throw new Exception("No first argument");
                default:
                    if (curLexeme == Lexeme.OPEN) {
                        throw new Exception("Missing expression in the brackets");
                    }
                    if (open != 0) {
                        throw new Exception("No closing parenthesis");
                    }
                    curLexeme = Lexeme.END;
                    return;
            }
        }
        if (Character.isDigit(expression.charAt(point))) {
            curLexeme = Lexeme.NUM;
            parseDigit(false);
            return;
        }
        if (Character.isLetter(expression.charAt(point))) {
            parseWord();
            return;
        }
        switch (expression.charAt(point)) {
            case '+':
                curLexeme = Lexeme.PLUS;
                break;
            case '−':
                if (curLexeme == null || curLexeme.type != Lexeme.Type.ARGUMENT && curLexeme != Lexeme.CLOSE) {
                    point++;
                    skipSpaces();
                    if (Character.isDigit(expression.charAt(point))) {
                        curLexeme = Lexeme.NUM;
                        parseDigit(true);
                        return;
                    }
                    point--;
                }
                if (curLexeme == null || curLexeme.type != Lexeme.Type.ARGUMENT && curLexeme != Lexeme.CLOSE) {
                    curLexeme = Lexeme.UNARY_MINUS;
                    break;
                }
                curLexeme = Lexeme.MINUS;
                break;
            case '(':
                curLexeme = Lexeme.OPEN;
                break;
            case ')':
                curLexeme = Lexeme.CLOSE;
                break;
            case '×':
                if (point + 1 < expression.length() && expression.charAt(point + 1) == '×') {
                    curLexeme = Lexeme.POW;
                    point++;
                    break;
                }
                curLexeme = Lexeme.MUL;
                break;
            case '÷':
                if (point + 1 < expression.length() && expression.charAt(point + 1) == '÷') {
                    curLexeme = Lexeme.LOG;
                    point++;
                    break;
                }
                curLexeme = Lexeme.DIV;
                break;
            default:
                if (curLexeme == null) {
                    throw new Exception("Start symbol");
                }
                point++;
                skipSpaces();
                if (point == expression.length()) {
                    throw new Exception("End symbol");
                } else {
                    throw new Exception("Middle symbol");
                }


        }
        point++;
    }

    private void skipSpaces() {
        while (point < expression.length() && Character.isWhitespace(expression.charAt(point))) {
            point++;
        }
    }

    private void parseWord() throws Exception {
        int start = point;
        while (point < expression.length() && Character.isLetter(expression.charAt(point))) {
            point++;
        }
        switch (expression.substring(start, point)) {
            case "mod":
                curLexeme = Lexeme.MOD;
                return;
            case "abs":
                curLexeme = Lexeme.ABS;
                return;
            case "sqrt":
                curLexeme = Lexeme.SQRT;
                return;
            case "square":
                curLexeme = Lexeme.SQUARE;
                return;
            default:
                curLexeme = Lexeme.VAR;
                name = expression.substring(start, point);
                if (!name.equals("x") && !name.equals("y") && !name.equals("z")) {
                    throw new Exception("Wrong variable");
                }
                break;
        }
    }

    private void parseDigit(boolean sign) {
        int start = point;
        while (point < expression.length() && (Character.isDigit(expression.charAt(point)) || expression.charAt(point) == '.' || expression.charAt(point) == 'E' ||  (expression.charAt(point) == '−' && point - 1 >= 0 && expression.charAt(point - 1) == 'E'))) {
            point++;
        }
        try {
            if (sign) {
                value = value.parse("-" + expression.substring(start, point));
            } else {
                String s = expression.substring(start, point);
                s = s.replace("−","-");
                value = value.parse(s);
            }
        } catch (Exception e) {
            value = null;
        }
    }

    private GenericExpression<T> expression() throws Exception {
        GenericExpression<T> expr = add();
        while (curLexeme.priority == Lexeme.Priority.FIRST) {
            prevLexeme = curLexeme;
            nextLexeme();
            parseCheck();
            if (prevLexeme == Lexeme.PLUS) {
                expr = new Add<>(expr, add());
            } else {
                expr = new Subtract<>(expr, add());
            }
        }
        return expr;
    }

    private GenericExpression<T> add() throws Exception {
        GenericExpression<T> expr = mul();
        while (curLexeme.priority == Lexeme.Priority.SECOND) {
            prevLexeme = curLexeme;
            nextLexeme();
            parseCheck();
            switch (prevLexeme) {
                case MUL:
                    expr = new Multiply<>(expr, mul());
                    break;
                case DIV:
                    expr = new Divide<>(expr, mul());
                    break;
                case MOD:
                    expr = new Mod<>(expr, mul());
                    break;
            }
        }
        return expr;
    }

    private void parseCheck() throws Exception {
        if (curLexeme == Lexeme.CLOSE || curLexeme.type == Lexeme.Type.BINARY) {
            throw new Exception("No middle argument");
        }
    }

    private GenericExpression<T> mul() throws Exception {
        prevLexeme = curLexeme;
        nextLexeme();
        switch (prevLexeme) {
            case UNARY_MINUS:
                parseCheck();
                return new Negate<>(mul());
            case SQUARE:
                parseCheck();
                return new Square<>(mul());
            case ABS:
                parseCheck();
                return new Abs<>(mul());
            case NUM:
                switch (curLexeme) {
                    case OPEN:
                        if (open > 0) {
                            throw new Exception("No closing parenthesis");
                        }
                        throw new Exception("No middle argument");
                    case CLOSE:
                        if (open <= 0) {
                            throw new Exception("No opening parenthesis");
                        }
                    default:
                        return new Const<>(value);
                }
            case VAR:
                switch (curLexeme) {
                    case OPEN:
                        throw new Exception("No closing parenthesis");
                    case CLOSE:
                        if (open <= 0) {
                            throw new Exception("No opening parenthesis");
                        }
                    default:
                        return new Variable<>(name);
                }
            default:
                parseCheck();
                open++;
                GenericExpression<T> expr = expression();
                open--;
                nextLexeme();
                return expr;
        }
    }

    private enum Lexeme {
        NUM(Type.ARGUMENT, Priority.THIRD), PLUS(Type.BINARY, Priority.FIRST), MINUS(Type.BINARY, Priority.FIRST),
        MUL(Type.BINARY, Priority.SECOND), DIV(Type.BINARY, Priority.SECOND), OPEN(Type.BRACKET, Priority.THIRD),
        CLOSE(Type.BRACKET, Priority.THIRD), VAR(Type.ARGUMENT, Priority.THIRD), ABS(Type.UNARY, Priority.THIRD),
        SQUARE(Type.UNARY, Priority.THIRD), MOD(Type.BINARY, Priority.SECOND), SQRT(Type.UNARY, Priority.THIRD),
        POW(Type.BINARY, Priority.SECOND), LOG(Type.BINARY, Priority.SECOND), UNARY_MINUS(Type.UNARY, Priority.THIRD), END(null, null);
        private Type type;
        private Priority priority;

        Lexeme(Type type, Priority priority) {
            this.type = type;
            this.priority = priority;
        }

        private enum Type {
            BINARY, UNARY, BRACKET, ARGUMENT
        }

        private enum Priority {
            FIRST, SECOND, THIRD
        }
    }


}