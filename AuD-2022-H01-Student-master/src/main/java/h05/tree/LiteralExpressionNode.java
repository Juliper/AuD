package h05.tree;

import h05.math.MyInteger;
import h05.math.MyNumber;
import h05.math.MyRational;
import h05.math.MyReal;

import java.util.Map;
import java.util.Objects;

/**
 * This class represents a literal operand arithmetic expression node. A literal operand is a {@link
 * MyNumber}.
 *
 * <p>Example:
 * <ul>
 *     <li>Literal node with the number 2.5/li>
 * </ul>
 *
 * <pre>{@code
 *    LiteralExpressionNode node = new LiteralExpressionNode(new MyReal(2.5));
 * }</pre>
 *
 * @author Nhan Huynh
 */
public class LiteralExpressionNode extends OperandExpressionNode {

    /**
     * The literal operand.
     */
    private final MyNumber value;

    /**
     * Constructs and initializes a literal operand arithmetic expression node with the given
     * value.
     *
     * @param value the literal operand
     * @throws NullPointerException if the value is {@code null}
     */
    public LiteralExpressionNode(MyNumber value) {
        Objects.requireNonNull(value, "value null");

        this.value = value;
    }

    /**
     * Returns the literal operand.
     *
     * @return the literal operand
     */
    public MyNumber getValue() {
        return value;
    }

    @Override
    public MyNumber evaluate(Map<String, MyNumber> identifiers) {
        return value;
    }

    @Override
    public ArithmeticExpressionNode clone() {
        MyNumber number;

        if (value instanceof MyInteger) number = new MyInteger(value.toInteger());
        else if (value instanceof MyReal)   number = new MyReal(value.toReal());
        else number = new MyRational(value.toRational());

        return new LiteralExpressionNode(number);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
