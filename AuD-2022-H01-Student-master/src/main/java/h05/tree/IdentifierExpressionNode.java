package h05.tree;

import h05.exception.IllegalIdentifierExceptions;
import h05.exception.UndefinedIdentifierException;
import h05.math.MyNumber;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents an identifier operand arithmetic expression node. An identifier operand is
 * a variable name.
 *
 * <p>Example:
 * <ul>
 *     <li>Identifier node with the identifier a</li>
 *     <li>Racket notation: (define a 2) - constant identifier a with the value 2</li>
 * </ul>
 *
 * <pre>{@code
 *    IdentifierExpressionNode node = new IdentifierExpressionNode("a");
 * }</pre>
 *
 * @author Nhan Huynh
 */
public class IdentifierExpressionNode extends OperandExpressionNode {
    /**
     * The identifier name.
     */
    private final String value;

    /**
     * Constructs and initializes an identifier expression node with the given value.
     *
     * @param value the identifier name
     * @throws IllegalArgumentException    if the identifier name is not valid
     * @throws IllegalIdentifierExceptions if the identifier name is not valid
     * @throws NullPointerException        if the identifier name is {@code null}
     */
    public IdentifierExpressionNode(String value) {
        if (value == null)  throw new NullPointerException();
        else if (value.isEmpty())   throw new IllegalArgumentException("empty string");


        Pattern p = Pattern.compile("((\\p{Alpha}|\\x2D)*\\p{Alpha}(\\p{Alpha}|\\x2D)*)");
        Matcher m = p.matcher(value);

        if (!m.matches())    throw new IllegalIdentifierExceptions(value);


        this.value = value;
    }

    /**
     * Returns the identifier name.
     *
     * @return the identifier name
     */
    public String getValue() {
        return value;
    }

    @Override
    public MyNumber evaluate(Map<String, MyNumber> identifiers) {
        if (identifiers.get(value) == null) throw new IllegalIdentifierExceptions(value);


        if (identifiers.get(value) == null) throw new UndefinedIdentifierException(value);


        return identifiers.get(value);
    }

    @Override
    public ArithmeticExpressionNode clone() {
        return new IdentifierExpressionNode(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
