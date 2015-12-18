package ru.kozlov_ea.my_calculator;

public class ParserModel {

    private String inStr;
    private int count;

    private static final int maxStringLen = 15;

    ParserModel(String in) {
        inStr = in;
        count = 0;
    }


    //проверяем корретность строки на длину
    private boolean CheckStr(String cur) {
        return cur.length() > 0 && cur.length() < maxStringLen;
    }

    // если исходная строчка задана корректно, то разбираем её
    private String getCur() {
        String curS = "";
        int m = count;
        if (CheckStr(inStr)) {
            while (count < inStr.length()) {
                char ch = inStr.charAt(count);
                if ((ch == '.') || (ch >= '0' && ch <= '9')) {
                    count++;
                } else {
                    break;
                }
            }
        }
        if (m < count) {
            curS = inStr.substring(m, count);
        }
        return curS;
    }

    private double First() {
        double curAns = 0;
        boolean flag = false;

        if (inStr.charAt(count) == '-') {
            count++;
            flag= true;
        }

        String q = getCur();
        if (flag) {
            q = '-' + q;
        }

        if (CheckStr(q)) {
            curAns = Double.parseDouble(q);
        }
        return curAns;
    }

    private Double Second() {
        double t = First();
        while (count < inStr.length()) {
            char ch = inStr.charAt(count);
            switch (ch) {
                case '*':
                    count++;
                    t = t * First();
                    break;
                case '/':
                    count++;
                    t = t / First();
                    break;
                default:
                    return t;
            }
        }
        return t;
    }

    public Double Third() {
        double t = Second();
        while (count < inStr.length()) {
            char ch = inStr.charAt(count);
            switch (ch) {
                case '+':
                    count++;
                    t += Second();

                    break;
                case '-':
                    count++;
                    t -= Second();

                    break;
                default:
                    return t;
            }
        }
        return t;
    }
}
