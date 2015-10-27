package com.example.artem.calculator;

import java.math.BigDecimal;

/**
 * Created by artem on 10/27/15.
 */
public class Functions {
    public interface Function {
        BigDecimal call(BigDecimal a, BigDecimal b);
    }

    public static class FlipConst implements Function {
        @Override
        public BigDecimal call(BigDecimal a, BigDecimal b) {
            return b;
        }
    }

    public static class Add implements Function {
        @Override
        public BigDecimal call(BigDecimal a, BigDecimal b) {
            return a.add(b);
        }
    }

    public static class Subtract implements Function {
        @Override
        public BigDecimal call(BigDecimal a, BigDecimal b) {
            return a.subtract(b);
        }
    }

    public static class Multiply implements Function {
        @Override
        public BigDecimal call(BigDecimal a, BigDecimal b) {
            return a.multiply(b);
        }
    }

    public static class Divide implements Function {
        @Override
        public BigDecimal call(BigDecimal a, BigDecimal b) {
            return a.divide(b, BigDecimal.ROUND_CEILING);
        }
    }

    public static class Modulo implements Function {
        @Override
        public BigDecimal call(BigDecimal a, BigDecimal b) {
            return a.remainder(b);
        }
    }
}
