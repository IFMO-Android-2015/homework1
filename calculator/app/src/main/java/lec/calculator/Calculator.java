package lec.calculator;


public class Calculator {


    class Word {

        StringBuilder word = new StringBuilder();
        boolean hasComma = false;
        boolean sign = false;

        Word() {
        }

        Word(double d) {
            String str = Double.toString(d);

            int comma = str.indexOf(".");
            if (comma != -1 && comma + 5 < str.length())
                word.append(str.substring(0,comma + 5));
            else
                word.append(str);
            hasComma = word.indexOf(".") > -1;
            sign = d < 0;
        }

        void pushComma() {
            if (hasComma) return;

            hasComma = true;
            word.append('.');
        }

        void pushSymb(char c) {
            if (c < '0' || c > '9') return;

            word.append(c);
        }

        void pop() {
            if (word.charAt(word.length() - 1) == '.') hasComma = false;
            word.deleteCharAt(word.length() - 1);
        }

        void changeSign() {
            sign = !sign;
        }

        double getDouble() {
            return Double.valueOf(word.toString()) * (sign ? -1 : 1);
        }

        @Override
        public String toString() {
            return (sign ? "-" : "") + word.toString();
        }
    }

    interface Operation {
        double calc(double first, double second);
    }

    enum Operator {
        ADDITION("+", 0, new Operation() {
            @Override
            public double calc(double first, double second) {
                return first + second;
            }
        }),

        SUBTRACTION("-", 0, new Operation() {
            @Override
            public double calc(double first, double second) {
                return first-second;
            }
        }),

        MULTIPLICATION("*", 1, new Operation() {
            @Override
            public double calc(double first, double second) {
                return first*second;
            }
        }),

        DIVISION("/", 1, new Operation() {
            @Override
            public double calc(double first, double second) {
                return first / second;
            }
        }),

        PERCENTAGE("%", 1, new Operation() {
            @Override
            public double calc(double first, double second) {
                return first / 100 * second;
            }
        });

        private String str;
        private Operation op;
        private double def;

        Operator(String str,double def, Operation op) {
            this.str = str;
            this.op = op;
            this.def = def;
        }

        double calc(double f) {
            return op.calc(f,def);
        }

        double calc(double f, double s) {
            return op.calc(f, s);
        }
    }

    public Word fWord, sWord;

    final static int START      = 0;
    final static int READ_FWORD = 1;
    final static int READ_SWORD = 2;
    final static int READ_OP    = 3;
    final static int OP_CALCED  = 4;

    private  int state = 0;

    private Operator op;
    private double lastResult;

    public void pushSymb(char c) {
        if (c < '0' || c > '9') return;
        switch (state) {
            case START :
            case OP_CALCED :
                fWord = new Word();
                state = READ_FWORD;
            case READ_FWORD :
                fWord.pushSymb(c);
                break;
            case READ_OP :
                sWord = new Word();
                state = READ_SWORD;
            case READ_SWORD :
                sWord.pushSymb(c);

        }
    }

    public void pushComma() {
        switch (state) {
            case START :
            case OP_CALCED :
                fWord = new Word();
                state = READ_FWORD;
            case READ_FWORD :
                fWord.pushComma();
                break;
            case READ_OP :
                sWord = new Word();
                state = READ_SWORD;
            case READ_SWORD :
                sWord.pushComma();

        }
    }

    public void pushOp(Operator op) {
        switch (state) {
            case OP_CALCED :
            case READ_FWORD :
                state = READ_OP;
            case READ_OP :
                this.op = op;
                break;
            case READ_SWORD :
                calculate();
                this.op = op;
                state = READ_OP;
        }
    }

    public void  changeSign() {
        if (state == READ_FWORD || state == OP_CALCED)
            fWord.changeSign();
        if (state == READ_SWORD)
            sWord.changeSign();
    }


    public void calculate()  {
        double ans;
        if (state == READ_OP)
            ans = op.calc(fWord.getDouble());
        else if (state == READ_SWORD)
            ans =  op.calc(fWord.getDouble(), sWord.getDouble());
        else return;
        state = OP_CALCED;
        op = null;
        fWord = new Word(ans);
        sWord = null;
    }

    public void pop() {
        switch (state) {
            case OP_CALCED :
                fWord = new Word();
                state = START;
                break;
            case READ_FWORD :
                fWord.pop();
                if (fWord.word.length() == 0) state = START;
                fWord = null;
                break;
            case READ_OP :
                op = null;
                state = READ_FWORD;
                break;
            case READ_SWORD :
                sWord.pop();
                if (sWord.word.length() == 0) state = READ_OP;
                sWord = null;
                break;
        }
    }

    @Override
    public String toString() {
        return (fWord == null ? "" : fWord.toString()) +
                (op == null ? "" : op.str) +
                (sWord == null ? "" : sWord.toString());
    }
}
