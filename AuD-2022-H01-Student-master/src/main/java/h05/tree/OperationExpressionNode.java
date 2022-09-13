package h05.tree;

import h05.exception.WrongNumberOfOperandsException;
import h05.math.MyInteger;
import h05.math.MyNumber;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

/**
 * This class represents an operation arithmetic expression node. An operation expression node
 * contains and operator followed by
 * <it>n</it> operands depending on its arity.
 *
 * <p>Example:
 * <ul>
 *     <li>Operator node with the operation "+" and the operands 2, 3, 4/li>
 *     <li>Racket notation: (+ 2 3 4)</li>
 * </ul>
 *
 * <pre>{@code
 *    ListItem<ArithmeticExpressionNode> operands = new ListItem<>();
 *    operands.key = new LiteralExpressionNode(new MyInteger(2));
 *    operands.next = new ListItem<>();
 *    operands.next.key = new LiteralExpressionNode(new MyInteger(3));
 *    operands.next.next = new ListItem<>();
 *    operands.next.next.key = new LiteralExpressionNode(new MyInteger(4));
 *    OperationExpressionNode node = new OperationExpressionNode(Operator.ADD, operands);
 * }</pre>
 *
 * @author Nhan Huynh
 * @see Operator
 * @see ListItem
 */
public class OperationExpressionNode implements ArithmeticExpressionNode {

    /**
     * The operator of this node.
     */
    private final Operator operator;

    /**
     * The operands of this node.
     */
    private final @Nullable ListItem<ArithmeticExpressionNode> operands;

    /**
     * Contracts and initializes an operation expression node with the given operator and operands.
     *
     * @param operator the operator of this node
     * @param operands the operands of this node
     * @throws NullPointerException if the operator is {@code null}
     */
    public OperationExpressionNode(Operator operator, @Nullable ListItem<ArithmeticExpressionNode> operands) {
        Objects.requireNonNull(operator, "operator null");


        ListItem<ArithmeticExpressionNode> p = operands;
        int numberOfOperands = 0;
        while (p != null)   {
            numberOfOperands++;
            p = p.next;
        }

        if (operator == Operator.SUB || operator == Operator.DIV)   {
            if (!(numberOfOperands > 0))    throw new WrongNumberOfOperandsException(numberOfOperands, 1, Integer.MAX_VALUE);
        }

        if (operator == Operator.LN || operator == Operator.EXP || operator == Operator.SQRT)  {
            if (numberOfOperands != 1)  throw  new WrongNumberOfOperandsException(numberOfOperands, 1, 1);
        }

        if (operator == Operator.EXPT || operator == Operator.LOG)  {
            if (numberOfOperands != 2)  throw  new WrongNumberOfOperandsException(numberOfOperands, 2, 2);
        }


        this.operator = operator;
        this.operands = operands;
    }

    /**
     * Returns the operator of this node.
     *
     * @return the operator of this node
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * Returns the operands of this node.
     *
     * @return the operands of this node
     */
    public ListItem<ArithmeticExpressionNode> getOperands() {
        return operands;
    }

    @Override
    public MyNumber evaluate(Map<String, MyNumber> identifiers) {

        ListItem<ArithmeticExpressionNode> node = operands;
        int length = 0;

        ListItem<ArithmeticExpressionNode> it = operands;
        while (it != null)  {
            it = it.next;
            length++;
        }



        //evaluates additions
        if (operator == Operator.ADD)   {


            //when there are no operands
            if (length == 0)   return MyInteger.ZERO;

            if (length == 1)  return node.key.evaluate(identifiers).plus();


            MyNumber result = node.key.evaluate(identifiers);

            while (node.next != null)   {
                node = node.next;
                result = result.plus(node.key.evaluate(identifiers));
            }


            return result;
        }






        //evaluate multiplications
        if (operator == Operator.MUL)   {

            //when there are no operands
            if (length == 0)   return MyInteger.ONE;

            if (length == 1)  return node.key.evaluate(identifiers).times();


            MyNumber result = node.key.evaluate(identifiers);

            while (node.next != null)   {
                node = node.next;
                result = result.times(node.key.evaluate(identifiers));
            }


            return result;
        }




        if (operator == Operator.SUB)   {

            if (length == 1)  return node.key.evaluate(identifiers).minus();


            MyNumber result = node.key.evaluate(identifiers);

            while (node.next != null)   {
                node = node.next;
                result = result.minus(node.key.evaluate(identifiers));
            }


            return result;
        }



        if (operator == Operator.DIV)   {

            if (length == 1)    return node.key.evaluate(identifiers).divide();


            MyNumber result = node.key.evaluate(identifiers);

            while (node.next != null)   {
                node = node.next;
                result = result.divide(node.key.evaluate(identifiers));
            }


            return result;
        }



        if (operator == Operator.EXP)   {
            return node.key.evaluate(identifiers).exp();
        }



        if (operator == Operator.EXPT)   {
            return node.key.evaluate(identifiers).expt(node.next.key.evaluate(identifiers));
        }



        if (operator == Operator.LN)   {
            return node.key.evaluate(identifiers).ln();
        }



        if (operator == Operator.LOG)   {
            return node.key.evaluate(identifiers).log(node.next.key.evaluate(identifiers));
        }



        return node.key.evaluate(identifiers).sqrt();
    }

    @Override
    public boolean isOperand() {
        return false;
    }

    @Override
    public boolean isOperation() {
        return true;
    }

    @Override
    public ArithmeticExpressionNode clone() {

        ListItem<ArithmeticExpressionNode> pointer = operands;

        ListItem<ArithmeticExpressionNode> newOperands = null;
        ListItem<ArithmeticExpressionNode> newOperandsTail = null;


        while (pointer != null) {

            if (newOperands == null)    {
                newOperands = new ListItem<>();
                newOperandsTail = newOperands;
            }else {
                newOperandsTail.next = new ListItem<>();
                newOperandsTail = newOperandsTail.next;
            }



            newOperandsTail.key = pointer.key.clone();


            pointer = pointer.next;
        }

        return new OperationExpressionNode(operator, newOperands);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(LEFT_BRACKET);
        sb.append(operator);
        for (ListItem<ArithmeticExpressionNode> node = operands; node != null; node = node.next) {
            sb.append(" ").append(node.key);
        }
        sb.append(RIGHT_BRACKET);
        return sb.toString();
    }
}
