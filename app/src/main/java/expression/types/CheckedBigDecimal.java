package expression.types;


import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by daniil on 28.10.15.
 */
public class CheckedBigDecimal extends MyNumber<CheckedBigDecimal> {
    private final BigDecimal value;
    private boolean isError = false;
    private boolean isInfinity = false;

    public CheckedBigDecimal(String s) {
        if (s.equals("Infinity")) {
            isInfinity = true;
            value = new BigDecimal("1", MathContext.DECIMAL128);
        } else if (s.equals("Error")){
            isError = true;
            value = new BigDecimal("1", MathContext.DECIMAL128);
        } else if (s.equals("pi")){
            value = new BigDecimal(Math.PI, MathContext.DECIMAL128);
        } else if (s.equals("e")){
            value = new BigDecimal(Math.E, MathContext.DECIMAL128);
        }else {
            value = new BigDecimal(s, MathContext.DECIMAL128);
        }
    }

    public CheckedBigDecimal(BigDecimal n) {
        value = n;
    }

    public CheckedBigDecimal(boolean e, boolean i, BigDecimal n) {
        isError = e;
        isInfinity = i;
        value = n;
    }

    @Override
    public CheckedBigDecimal add(CheckedBigDecimal b) {
        if (isError || b.isError) {
            return new CheckedBigDecimal(true, false, value);
        } else if (isInfinity) {
            if (b.isInfinity) {
                return new CheckedBigDecimal(true, false, value);
            }
            return new CheckedBigDecimal(false, true, value);
        } else {
            return new CheckedBigDecimal(value.add(b.value));
        }
    }

    @Override
    public CheckedBigDecimal pow(CheckedBigDecimal b) {
        if (b.value.compareTo(new BigDecimal(2, MathContext.DECIMAL128)) == 0) {
            return (this.multiply(this));
        } else if (b.value.compareTo(new BigDecimal(3, MathContext.DECIMAL128)) == 0) {
            return (this.multiply(this)).multiply(this);
        } else {
            return new CheckedBigDecimal(true, false, value);
        }
    }

    @Override
    public CheckedBigDecimal divide(CheckedBigDecimal b) {
        if (isError || b.isError) {
            return new CheckedBigDecimal(true, false, value);
        } else if (isInfinity) {
            if (b.isInfinity) {
                return new CheckedBigDecimal(true, false, value);
            }
            return new CheckedBigDecimal(false, true, value.multiply(b.value));
        } else if (b.isInfinity) {
            return new CheckedBigDecimal(false, false, BigDecimal.ZERO);
        } else if (b.value.compareTo(BigDecimal.ZERO) == 0) {
            if (b.value.compareTo(value) == 0) {
                return new CheckedBigDecimal(true, false, value);
            } else {
                return new CheckedBigDecimal(false, true, value);
            }
        }
        return new CheckedBigDecimal(value.divide(b.value, MathContext.DECIMAL128));
    }


    @Override
    public CheckedBigDecimal multiply(CheckedBigDecimal b) {
        if (isError || b.isError) {
            return new CheckedBigDecimal(true, false, value);
        } else if (b.value.compareTo(BigDecimal.ZERO) == 0) {
            if (isInfinity) {
                return new CheckedBigDecimal(true, false, value);
            }
            return new CheckedBigDecimal(false, false, b.value);
        } else if (value.compareTo(BigDecimal.ZERO) == 0) {
            if (b.isInfinity) {
                return new CheckedBigDecimal(true, false, value);
            }
            return new CheckedBigDecimal(false, false, value);
        } if (isInfinity || b.isInfinity) {
            return new CheckedBigDecimal(false, true, value.multiply(b.value, MathContext.DECIMAL128));
        }
        return new CheckedBigDecimal(value.multiply(b.value, MathContext.DECIMAL128));
    }

    @Override
    public CheckedBigDecimal negate() {
        return new CheckedBigDecimal(isError, isInfinity, value.negate(MathContext.DECIMAL128));
    }

    @Override
    public CheckedBigDecimal subtract(CheckedBigDecimal b) {
        if (isError || b.isError) {
            return new CheckedBigDecimal(true, false, value);
        } else if (isInfinity) {
            if (b.isInfinity) {
                return new CheckedBigDecimal(true, false, value);
            }
            return new CheckedBigDecimal(false, true, value);
        } else {
            return new CheckedBigDecimal(value.subtract(b.value, MathContext.DECIMAL128));
        }
    }

    @Override
    public CheckedBigDecimal abs() {
        return new CheckedBigDecimal(isError, isInfinity, value.abs(MathContext.DECIMAL128));
    }

    @Override
    public CheckedBigDecimal square() {
        return new CheckedBigDecimal(isError, isInfinity, value.multiply(value, MathContext.DECIMAL128));
    }

    @Override
    public CheckedBigDecimal newNumber(String s) {
        return new CheckedBigDecimal(s);
    }

    @Override
    public String toString() {
        if (isError) {
            return "Error";
        } else if (isInfinity) {
            if (value.signum() < 0) {
                return "-Infinity";
            } else {
                return "Infinity";
            }
        } else {
            return value.toPlainString();
        }
    }
}
