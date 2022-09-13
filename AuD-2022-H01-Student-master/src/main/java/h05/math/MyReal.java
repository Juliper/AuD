package h05.math;

import h05.exception.Comparison;
import h05.exception.WrongOperandException;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Represents a real number in Racket.
 *
 * @author Nhan Huynh
 */
public final class MyReal extends MyNumber {

    /**
     * The scale of the real number for inexact numbers.
     */
    public static final int SCALE = 15;

    /**
     * The rounding mode of the real number for inexact numbers.
     */
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * The constant {@link MyNumber} 0 as a {@link MyReal}.
     */
    public static final MyNumber ZERO = new MyReal(BigDecimal.ZERO);

    /**
     * The constant {@link MyNumber} 1 as a {@link MyReal}.
     */
    public static final MyNumber ONE = new MyReal(BigDecimal.ONE);

    /**
     * The value of this real number.
     */
    private final BigDecimal value;

    /**
     * Constructs and initializes a real number with the specified value.
     *
     * @param value the value of the real number
     * @throws NullPointerException if the value is null
     */
    public MyReal(BigDecimal value) {
        Objects.requireNonNull(value, "value null");
        this.value = value.setScale(SCALE, ROUNDING_MODE);
    }

    @Override
    public BigInteger toInteger() {
        return value.toBigInteger();
    }

    @Override
    public Rational toRational() {
        BigDecimal factor = BigDecimal.TEN.pow(MyReal.SCALE);
        BigInteger numerator = value.multiply(factor).toBigInteger();

        return new Rational(numerator, factor.toBigInteger());
    }

    @Override
    public BigDecimal toReal() {
        return value;
    }

    @Override
    public boolean isZero() {
        return this.equals(ZERO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyReal number)) {
            return false;
        }
        return value.equals(number.value);
    }

    @Override
    public MyNumber negate() {
        return new MyReal(value.negate());
    }

    @Override
    public MyNumber plus(MyNumber other) {
        return checkRealToInt(value.add(other.toReal()));
    }

    @Override
    public MyNumber minus(MyNumber other) {
        if (other instanceof MyInteger) {
            return new MyReal(value.add(other.negate().toReal()).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE));
        }
        if (other instanceof MyReal)    {
            return checkRealToInt(value.add(other.negate().toReal()).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE));
        }

        return checkRealToInt(value.subtract(other.toReal()).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE));
    }

    @Override
    public MyNumber times(MyNumber other) {
        return checkRealToInt(value.multiply(other.toReal()).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE));
    }

    @Override
    public MyNumber divide() {
        return new MyReal(BigDecimal.ONE.divide(value, SCALE, ROUNDING_MODE));
    }

    @Override
    public MyNumber divide(MyNumber other) {
        if (other.isZero()) throw new WrongOperandException(other, Comparison.DIFFERENT_FROM, MyInteger.ZERO);

        return checkRealToInt(value.divide(other.toReal(), SCALE, ROUNDING_MODE));
    }

    @Override
    public String toString() {
        return value.stripTrailingZeros().toString();
    }
}
