package h05.tree;

import h05.exception.IllegalIdentifierExceptions;
import h05.math.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Evaluates an arithmetic expression by replacing the variables (identifiers) of the expression
 * with their values.
 *
 * @author Nhan Huynh
 */
public class ArithmeticExpressionEvaluator {

    /**
     * The arithmetic expression tree to evaluate.
     */
    private ArithmeticExpressionNode root;

    /**
     * The map of variables and their values.
     */
    private final Map<String, MyNumber> identifiers;

    /**
     * Constructs and initializes an arithmetic expression evaluator.
     *
     * @param root        the root of the arithmetic expression tree to evaluate
     * @param identifiers the map of variables and their values
     */
    public ArithmeticExpressionEvaluator(
        ArithmeticExpressionNode root,
        Map<String, MyNumber> identifiers) {
        this.root = root.clone();
        this.identifiers = identifiers;
    }

    /**
     * Returns the root of the arithmetic expression tree to evaluate.
     *
     * @return the root of the arithmetic expression tree to evaluate
     */
    public ArithmeticExpressionNode getRoot() {
        return root;
    }

    /**
     * Returns the map of variables and their values.
     *
     * @return the map of variables and their values
     */
    public Map<String, MyNumber> getIdentifiers() {
        return identifiers;
    }

    /**
     * Evaluates the arithmetic expression tree by replacing the variables (identifiers) of the
     * expression with their values and evaluates the most inner expressions.
     *
     * @return the list of tokens representing  the evaluation
     */
    public List<String> nextStep() {
        if (root instanceof LiteralExpressionNode)  {
            List<String> same = new ArrayList<>();
            same.add(root.toString());

            return same;
        }



        List<String> current = ExpressionTreeHandler.reconstruct(root);



        Pattern p = Pattern.compile("((\\p{Alpha}|\\x2D)*\\p{Alpha}(\\p{Alpha}|\\x2D)*)");
        Pattern n = Pattern.compile("\\+|-|\\*|/|exp|expt|ln|log|sqrt");


        for (int i = 0; i < current.size(); i++)    {

            if (p.matcher(current.get(i)).matches() && !n.matcher(current.get(i)).matches()) {
                if (identifiers.get(current.get(i)) != null) {

                    current.set(i, identifiers.get(current.get(i)).toString());

                } else {

                    current.set(i, "<unknown!>");

                }
            }

        }




        int possibleStart = 0;
        for (int i = 0; i < current.size(); i++)    {

            if (current.get(i).equals("("))    {
                possibleStart = i;
                continue;
            }


            if (current.get(i).equals(")")) {

                if (possibleStart < 0)  continue;


                List<String> operation = new ArrayList<>();
                for (int q = possibleStart; q <= i; q++) {
                    operation.add(current.get(q).toString());
                }


                ArithmeticExpressionNode newValue = ExpressionTreeHandler.buildRecursively(operation.iterator());
                MyNumber newNumber = newValue.evaluate(identifiers);


                current.set(possibleStart, newNumber.toString());


                for (int q = possibleStart + 1; q <= i; q++)    {
                    current.remove(possibleStart + 1);
                }



                i = i - operation.size();
                possibleStart = -1;
            }

        }


        if (current.size() == 1)    this.root = stringToOperand(current.get(0));
        else {
            this.root = ExpressionTreeHandler.buildRecursively(current.iterator());
        }


        return current;
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
}
