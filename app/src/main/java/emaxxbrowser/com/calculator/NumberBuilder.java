package emaxxbrowser.com.calculator;

public class NumberBuilder {

    private static final char DECIMAL_MARK = '.';

    private String currentNumber;
    private boolean error;

    public NumberBuilder() {
        currentNumber = "";
        error = false;
    }

    public void append(char x) {
        if (error) {
            clear();
        }
        if (Character.isDigit(x)) {
        	if (currentNumber.equals("0")) {
        		currentNumber = "" + x;
        	} else {
        		currentNumber += x;
        	}
        } else {
            if (x != ',') {
                throw new AssertionError();
            }
            if (currentNumber.indexOf(DECIMAL_MARK) < 0) {
                if (currentNumber.isEmpty()) {
                    currentNumber = "0";
                }
                currentNumber += DECIMAL_MARK;
            }
        }
    }

    public void deleteLast() {
        if (error) {
            clear();
        }
        if (!currentNumber.isEmpty()) {
            currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
        }
    }

    public void setNumber(double number) {
        currentNumber = Double.toString(number);
        error = false;
        int index = currentNumber.indexOf(DECIMAL_MARK);
        if (index >= 0) {
            for (int i = index + 1; i < currentNumber.length(); i++) {
                if (currentNumber.charAt(i) != '0') {
                    return;
                }
            }
            currentNumber = currentNumber.substring(0, index);
        }
    }

    public double getNumber() {
        if (error || currentNumber.isEmpty()) {
        	return 0;
        }
        return Double.parseDouble(currentNumber);
    }

    public String getString() {
        if (error) {
            return "Error";
        }
        return currentNumber;
    }

    public boolean isEmpty() {
        return error || currentNumber.isEmpty();
    }

    public void setError() {
        error = true;
        currentNumber = "";
    }

    public void clear() {
        currentNumber = "";
        error = false;
    }
}
