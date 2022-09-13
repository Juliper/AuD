package h05.tree;

/**
 * This class represents an abstract operand arithmetic expression node.
 *
 * @author Nhan Huynh
 */
public abstract class OperandExpressionNode implements ArithmeticExpressionNode {

    // We need to declare the visibility the clone method to be public since Object declares it as protected but the interface
    // specify it as public

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract ArithmeticExpressionNode clone();

    @Override
    public boolean isOperand() {
        return true;
    }

    @Override
    public boolean isOperation() {
        return false;
    }
}
