package ru.azarn.calculator;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Azarn on 02.11.2015.
 */
public class Calculator implements Serializable {
    private BigDecimal res;

    Calculator() {
        clear();
    }

    void set(BigDecimal number) {
        res = number;
    }

    void add(BigDecimal number) {
        res = res.add(number);
    }

    void subtract(BigDecimal number) {
        res = res.subtract(number);
    }

    void multiply(BigDecimal number) {
        res = res.multiply(number);
    }

    void divide(BigDecimal number) {
        res = res.divide(number, 10, BigDecimal.ROUND_CEILING);
    }

    void clear() {
        res = new BigDecimal(0);
    }

    BigDecimal get_result() {
        // Java bug? O_O
        if (res.compareTo(BigDecimal.ZERO) == 0)
            res = BigDecimal.ZERO;
        else
            res = res.stripTrailingZeros();
        return res;
    }
}
