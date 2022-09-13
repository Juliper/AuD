package h05.tree;

import h05.exception.*;
import h05.math.MyInteger;
import h05.math.MyRational;
import h05.math.MyReal;
import h05.math.Rational;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * This class is used to parse an expression and build a tree out of it.
 *
 * @author Nhan Huynh
 */
public final class ExpressionTreeHandler {

    /**
     * Don't let anyone instantiate this class.
     */
    private ExpressionTreeHandler() {
    }

    /**
     * Builds an arithmetic expression tree from a string recursively.
     *
     * @param expression the string representation of the arithmetic expression to parse
     * @return the root node of the arithmetic expression tree
     * @throws BadOperationException        if the iterator has no more tokens
     * @throws ParenthesesMismatchException if the parentheses are mismatched
     * @throws UndefinedOperatorException   if the operator is not defined
     */
    public static ArithmeticExpressionNode buildRecursively(Iterator<String> expression) {
        if (!expression.hasNext())  throw new BadOperationException("No expression");

        OperationExpressionNode main;


        String ex = expression.next();
        if (!expression.hasNext())  {
            return stringToOperand(ex);
        }


        if (ex.equals("("))    {
            ex = expression.next();
        }


        if (stringToOperator(ex) == null)   throw new UndefinedOperatorException(ex);


        main = new OperationExpressionNode(stringToOperator(ex), buildRecursivelyHelper(expression, 0, stringToOperator(ex)));

        return main;
    }



    private static ListItem<ArithmeticExpressionNode> buildRecursivelyHelper(Iterator<String> expression, int numberOfOperands, Operator operator)  {
        if (!expression.hasNext())  throw new BadOperationException("No expression");


        ListItem<ArithmeticExpressionNode> operand = new ListItem<>();
        String current = expression.next();


        if (current.equals(")"))   {

            if (operator == Operator.SUB || operator == Operator.DIV)   {
                if (!(numberOfOperands > 0))    throw new WrongNumberOfOperandsException(numberOfOperands, 1, Integer.MAX_VALUE);
            }

            if (operator == Operator.LN || operator == Operator.EXP || operator == Operator.SQRT)  {
                if (numberOfOperands != 1)  throw  new WrongNumberOfOperandsException(numberOfOperands, 1, 1);
            }

            if (operator == Operator.EXPT || operator == Operator.LOG)  {
                if (numberOfOperands != 2)  throw  new WrongNumberOfOperandsException(numberOfOperands, 2, 2);
            }


            return null;
        }
        else if (current.equals("("))  operand.key = buildRecursively(expression);
        else if (stringToOperator(current) != null) throw new ParenthesesMismatchException();
        else operand.key = stringToOperand(current);


        if (expression.hasNext())   {
            operand.next = buildRecursivelyHelper(expression, ++numberOfOperands, operator);
        }else {
            if (!current.equals(")"))   throw new ParenthesesMismatchException();
        }




        return operand;
    }



    /**
     * Builds an arithmetic expression tree from a string iteratively.
     *
     * @param expression the string representation of the arithmetic expression to parse
     * @return the root node of the arithmetic expression tree
     * @throws BadOperationException        if the iterator has no more tokens
     * @throws ParenthesesMismatchException if the parentheses are mismatched
     * @throws UndefinedOperatorException   if the operator is not defined
     */
    public static ArithmeticExpressionNode buildIteratively(Iterator<String> expression) {
        if (!expression.hasNext())  throw new BadOperationException("No expression");

        Stack<ListItem<ArithmeticExpressionNode>> stack = new Stack<>();
        Stack<Operator> operators = new Stack<>();
        String current = expression.next();




        //when the expression only contains one symbol
        if (!current.equals("(")) {
            return stringToOperand(current);
        }












        current = expression.next();
        if (stringToOperator(current) == null)  throw new UndefinedOperatorException(current);

        operators.push(stringToOperator(current));
        stack.push(new ListItem<>());


        while (expression.hasNext())    {

            current = expression.next();

            if (current.equals("("))   {

                current = expression.next();
                if (stringToOperator(current) == null)  throw new UndefinedOperatorException(current);


                operators.push(stringToOperator(current));
                stack.push(new ListItem<>());


                continue;
            }

            if (current.equals(")"))   {

                if (!expression.hasNext())  break;


                ListItem<ArithmeticExpressionNode> merge = stack.pop();
                Operator op = operators.pop();


                //countOperands
                ListItem<ArithmeticExpressionNode> p = merge;
                int number = 0;

                while (p != null && p.key != null)  {
                    p = p.next;
                    number++;
                }


                //Exception guard
                if (op == Operator.SUB || op == Operator.DIV)   {

                    if (!(number > 0))    throw new WrongNumberOfOperandsException(number, 1, Integer.MAX_VALUE);
                }

                if (op == Operator.LN || op == Operator.EXP || op == Operator.SQRT)  {
                    if (number != 1)  throw  new WrongNumberOfOperandsException(number, 1, 1);
                }

                if (op == Operator.EXPT || op == Operator.LOG)  {
                    if (number != 2)  throw  new WrongNumberOfOperandsException(number, 2, 2);
                }




                ListItem<ArithmeticExpressionNode> tail = stack.lastElement();
                while (tail.next != null)   tail = tail.next;


                if (merge.key == null)  {

                    merge.key = new OperationExpressionNode(op, null);
                    merge.next = null;

                    tail.next = merge;

                }else {

                    ListItem<ArithmeticExpressionNode> node = new ListItem<>();
                    node.key = new OperationExpressionNode(op, merge);


                    if (tail.key == null)   {
                        tail.key = node.key;
                        tail.next = node.next;
                    }else tail.next = node;

                }


                continue;

            }


            if (stringToOperator(current) != null)  throw new ParenthesesMismatchException();





            ListItem<ArithmeticExpressionNode> tail = stack.lastElement();
            while (tail.next != null)   tail = tail.next;
            if (tail.key != null)   {
                tail.next = new ListItem<>();
                tail = tail.next;
            }


            tail.key = stringToOperand(current);


            if (!expression.hasNext()   &&  !current.equals(")"))   throw new ParenthesesMismatchException();
        }


        ListItem<ArithmeticExpressionNode> merge = stack.pop();
        Operator op = operators.pop();

        if (merge.key == null)  return new OperationExpressionNode(op , null);
        return new OperationExpressionNode(op , merge);
    }


    private static Operator stringToOperator(String string)  {
        if (string.equals("+"))    return Operator.ADD;

        if (string.equals("-"))    return Operator.SUB;

        if (string.equals("*"))    return Operator.MUL;

        if (string.equals("/"))    return Operator.DIV;

        if (string.equals("exp"))    return Operator.EXP;

        if (string.equals("expt"))    return Operator.EXPT;

        if (string.equals("ln"))    return Operator.LN;

        if (string.equals("log"))    return Operator.LOG;

        if (string.equals("sqrt"))     return Operator.SQRT;


        return null;
    }



    private static ArithmeticExpressionNode stringToOperand(String string)  {
        Pattern integer = Pattern.compile("\\p{Digit}+");
        Pattern decimal = Pattern.compile("\\p{Digit}+\\.\\p{Digit}+");
        Pattern rational = Pattern.compile("\\p{Digit}+/\\p{Digit}+");
        Pattern identifier = Pattern.compile("((\\p{Alpha}|\\x2D)*\\p{Alpha}(\\p{Alpha}|\\x2D)*)");


        if (decimal.matcher(string).matches())  {

            return new LiteralExpressionNode(new MyReal(new BigDecimal(string)));

        }
        else if (rational.matcher(string).matches()) {

            return new LiteralExpressionNode(new MyRational(new Rational(new BigInteger(string.substring(0, string.indexOf("/"))), new BigInteger(string.substring(string.indexOf("/") + 1)))));

        }
        else if (integer.matcher(string).matches())    {

            return new LiteralExpressionNode(new MyInteger(new BigInteger(string)));

        }
        else if (identifier.matcher(string).matches())  return new IdentifierExpressionNode(string);
        else throw new IllegalIdentifierExceptions(string);
    }



    /**
     * Reconstructs the string representation of the arithmetic expression tree.
     *
     * @param root the root node of the arithmetic expression tree
     * @return the string representation of the arithmetic expression tree
     */
    public static List<String> reconstruct(ArithmeticExpressionNode root) {

        List<String> result = new ArrayList<>();


        if (root.isOperand())   {
            result.add(root.toString());
            return result;
        }



        OperationExpressionNode current = (OperationExpressionNode) root;
        result.add("(");
        result.add(current.getOperator().toString());

        if (current.getOperands() == null)  {
            result.add(")");
            return result;
        }else {
            result.addAll(reconstructHelper(current.getOperands()));
        }


        result.add(")");



        return result;
    }


    public static List<String> reconstructHelper(ListItem<ArithmeticExpressionNode> node) {
        List<String> result = new ArrayList<>();


        if (node.key.isOperand())   {
            result.add(node.key.toString());
        }else {
            result.addAll(reconstruct(node.key));
        }


        if (node.next != null)  {
            result.addAll(reconstructHelper(node.next));
        }


        return result;
    }
}
