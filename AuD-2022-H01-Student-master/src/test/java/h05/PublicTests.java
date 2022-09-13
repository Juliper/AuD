package h05;

import h05.math.*;
import h05.tree.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PublicTests {

    private static final BigDecimal EPSILON = real("0.0000000000001");

    @Test
    void printRoot() {
        System.out.println(getRoot());
    }

    @Nested
    class RationalTest {

        @Test
        void testConstructor() {
            var minusTwoThirds = ratio(10, -15);
            assertEquals(BigInteger.valueOf(-2), minusTwoThirds.getNumerator());
            assertEquals(BigInteger.valueOf(3), minusTwoThirds.getDenominator());
        }
    }

    @Nested
    class MyIntegerTest {

        @Test
        void testToRational() {
            assertEquals(
                mratio(69, 1).toRational(),
                getSixtyNine().toRational());
        }

        @Test
        void testToReal() {
            assertEquals(
                real("69"),
                getSixtyNine().toReal());
        }

        @Test
        void testMinus() {
            assertEquals(
                mint(-69),
                getSixtyNine().minus());
        }

        @Test
        void testMinusWithOperand() {
            assertEquals(
                mreal("55.63"),
                getSixtyNine().minus(mreal("13.37")));
        }

        @Test
        void testDivide() {
            assertEquals(
                mratio(1, 69),
                getSixtyNine().divide());
        }

        @Test
        void testDivideWithOperand() {
            assertEquals(
                mint(23),
                getSixtyNine().divide(mint(3)));
        }

        @Test
        void testSqrt() {
            assertEquals(
                mint(4),
                mint(16).sqrt());
        }

        @Test
        void testExpt() {
            assertEquals(
                mint(4761),
                getSixtyNine().expt(mint(2)));
        }

        @Test
        void testExp() {
            assertAlmostEquals(
                real("54.59815003314423"),
                mint(4).exp().toReal());
        }

        @Test
        void testLn() {
            assertAlmostEquals(
                real("4.23410650459726"),
                getSixtyNine().ln().toReal());
        }

        @Test
        void testLog() {
            assertEquals(
                mint(2),
                mint(4761).log(getSixtyNine()));
        }


        private static MyInteger getSixtyNine() {
            return mint(69);
        }
    }

    @Nested
    class MyRationalTest {

        private static final MyRational minusTwoThirds = mratio(-2, 3);
        private static final MyRational threeHalf = mratio(3, 2);

        @Test
        void testToRational() {
            var rational = minusTwoThirds.toRational();
            assertEquals(BigInteger.valueOf(-2), rational.getNumerator());
            assertEquals(BigInteger.valueOf(3), rational.getDenominator());
        }

        @Test
        void testToReal() {
            assertAlmostEquals(
                real("-0.666666666666667"),
                minusTwoThirds.toReal());
        }

        @Test
        void testMinus() {
            assertEquals(
                mratio(2, 3),
                minusTwoThirds.minus());
        }

        @Test
        void testMinusWithOperand() {
            assertEquals(
                mratio(5, 3),
                MyRational.ONE.minus(minusTwoThirds));
        }

        @Test
        void testDivide() {
            assertEquals(
                mratio(-3, 2).toRational(),
                minusTwoThirds.divide().toRational());
        }

        @Test
        void testDivideWithOperand() {
            assertEquals(
                mratio(-8, 9),
                minusTwoThirds.divide(mratio(3, 4)));
        }

        @Test
        void testSqrt() {
            assertAlmostEquals(
                real("1.224744871391589"),
                threeHalf.sqrt().toReal());
        }

        @Test
        void testExpt() {
            assertEquals(
                mreal("2.25"),
                threeHalf.expt(mint(2)));
        }

        @Test
        void testExp() {
            assertAlmostEquals(
                real("4.4816890703380645"),
                mratio(3, 2).exp().toReal());
        }

        @Test
        void testLn() {
            assertAlmostEquals(
                real("0.4054651081081644"),
                threeHalf.ln().toReal());
        }

        @Test
        void testLog() {
            assertEquals(
                mint(-2),
                mratio(1, 25).log(mint(5)));
        }
    }


    @Nested
    class MyRealTest {

        private final MyNumber pi = Identifier.PI.getValue();

        @Test
        void testToRational() {
            assertEquals(
                ratio(3141592653589793L, 1000000000000000L),
                pi.toRational());
        }

        @Test
        void testToReal() {
            assertEquals(
                real("3.14159265358979323846"),
                pi.toReal());
        }

        @Test
        void testMinus() {
            assertEquals(
                mreal("-3.14159265358979323846"),
                pi.minus());
        }

        @Test
        void testMinusWithOperand() {
            assertEquals(
                mreal("2.14159265358979323846"),
                pi.minus(MyReal.ONE));
        }

        @Test
        void testDivide() {
            assertEquals(
                mreal("4"),
                mreal("0.25").divide());
        }

        @Test
        void testDivideWithOperand() {
            assertEquals(
                mint(8),
                mreal("2").divide(mreal("0.25")));
        }

        @Test
        void testSqrt() {
            assertEquals(
                mreal("0.5"),
                mreal("0.25").sqrt());
        }

        @Test
        void testExpt() {
            assertAlmostEquals(
                mreal("0.8325532074018731").toReal(),
                mreal("0.4").expt(mreal("0.2")).toReal());
        }

        @Test
        void testExp() {
            assertAlmostEquals(
                mreal("23.140692632779267").toReal(),
                pi.exp().toReal());
        }

        @Test
        void testLn() {
            assertAlmostEquals(
                pi.toReal(),
                mreal("23.140692632779267").ln().toReal());
        }

        @Test
        void testLog() {
            assertAlmostEquals(
                mreal("-4").toReal(),
                mreal("16").log(mreal("0.5")).toReal());
        }
    }

    @Nested
    class ArithmeticExpressionEvaluatorTest {

        private final ArithmeticExpressionEvaluator evaluator =
            new ArithmeticExpressionEvaluator(getRoot(), getIdentifiersIncludingPredefined());

        private Map<String, MyNumber> getIdentifiersIncludingPredefined() {
            var m = new HashMap<>(getIdentifiers());
            m.put(Identifier.E.getName(), Identifier.E.getValue());
            m.put(Identifier.PI.getName(), Identifier.PI.getValue());
            return m;
        }

        @Test
        void testNextStep() {
            assertNextStep(
                "(+ 2/3 (/ 8 (* 1 2.5)))",
                "(", "+", "2/3", "(", "/", "8", "(", "*", "1", "2.5", ")", ")", ")");
            assertNextStep(
                "(+ 2/3 (/ 8 2.5))",
                "(", "+", "2/3", "(", "/", "8", "2.5", ")", ")");
            assertNextStep(
                "(+ 2/3 3.2)",
                "(", "+", "2/3", "3.2", ")");
            assertNextStep(
                "3.866666666666667",
                "3.866666666666667");
            assertNextStep(
                "3.866666666666667",
                "3.866666666666667");
        }

        private void assertNextStep(String expectedRootString, String... expectedExpression) {
            assertIterableEquals(
                List.of(expectedExpression), evaluator.nextStep());
            assertEquals(expectedRootString, evaluator.getRoot().toString());
        }
    }

    @Nested
    class ExpressionTreeHandlerTest {

        private static final List<String> EXPRESSION = List.of(
            "(", "+", "a", "(", "/", "(", "expt", "2", "b", ")", "(", "*", "(", "ln", "e", ")", "c", ")", ")", ")");

        @Test
        void testBuildRecursively() {
            var actual = ExpressionTreeHandler.buildRecursively(EXPRESSION.iterator());
            assertEquals(getRoot().toString(), actual.toString());
        }

        @Test
        void testBuildIteratively() {
            var actual = ExpressionTreeHandler.buildIteratively(EXPRESSION.iterator());
            assertEquals(getRoot().toString(), actual.toString());
        }

        @Test
        void testReconstruct() {
            var actual = ExpressionTreeHandler.reconstruct(getRoot());
            assertIterableEquals(EXPRESSION, actual);
        }
    }

    @Nested
    class IdentifierExpressionNodeTest {

        private final IdentifierExpressionNode node = new IdentifierExpressionNode("nice");

        @Test
        void testConstructor() {
            assertEquals("nice", node.getValue());
        }

        @Test
        void testEvaluate() {
            var result = node.evaluate(getIdentifiers());
            assertEquals(MyIntegerTest.getSixtyNine(), result);
        }

        @Test
        void testClone() {
            var clone = node.clone();
            assertInstanceOf(IdentifierExpressionNode.class, clone);
            assertNotSame(clone, node);
            assertEquals(node.getValue(), ((IdentifierExpressionNode) clone).getValue());
        }
    }


    @Nested
    class LiteralExpressionNodeTest {

        private final LiteralExpressionNode node = new LiteralExpressionNode(MyIntegerTest.getSixtyNine());

        @Test
        void testEvaluate() {
            assertEquals(MyIntegerTest.getSixtyNine(), node.evaluate(getIdentifiers()));
        }

        @Test
        void testClone() {
            var clone = node.clone();
            assertInstanceOf(LiteralExpressionNode.class, clone);
            assertNotSame(clone, node);
            assertEquals(node.getValue(), ((LiteralExpressionNode) clone).getValue());
        }
    }

    @Nested
    class OperationExpressionNodeTest {

        private final OperationExpressionNode node = add(
            aint(69), aint(69));

        @Test
        void testConstructor() {
            assertEquals(Operator.ADD, node.getOperator());

            var operands = node.getOperands();

            assertNotNull(operands);
            assertInstanceOf(LiteralExpressionNode.class, operands.key);
            assertEquals(MyIntegerTest.getSixtyNine(), ((LiteralExpressionNode) operands.key).getValue());

            assertNotNull(operands.next);
            assertInstanceOf(LiteralExpressionNode.class, operands.next.key);
            assertEquals(MyIntegerTest.getSixtyNine(), ((LiteralExpressionNode) operands.next.key).getValue());

            assertNull(operands.next.next);
        }

        @Test
        void testEvaluate() {
            var result = node.evaluate(getIdentifiers());
            assertEquals(mint(69 * 2), result);
        }

        @Test
        void testClone() {
            var clone = node.clone();
            assertInstanceOf(OperationExpressionNode.class, clone);
            assertNotSame(clone, node);
            assertNotSame(node.getOperands(), ((OperationExpressionNode) clone).getOperands());
        }
    }

    private static OperationExpressionNode add(ArithmeticExpressionNode... operands) {
        return new OperationExpressionNode(Operator.ADD, operands2list(operands));
    }

    private static OperationExpressionNode mul(ArithmeticExpressionNode... operands) {
        return new OperationExpressionNode(Operator.MUL, operands2list(operands));
    }

    private static OperationExpressionNode div(ArithmeticExpressionNode... operands) {
        return new OperationExpressionNode(Operator.DIV, operands2list(operands));
    }

    private static OperationExpressionNode expt(ArithmeticExpressionNode... operands) {
        return new OperationExpressionNode(Operator.EXPT, operands2list(operands));
    }

    private static OperationExpressionNode ln(ArithmeticExpressionNode... operands) {
        return new OperationExpressionNode(Operator.LN, operands2list(operands));
    }

    private static IdentifierExpressionNode identifier(String identifier) {
        return new IdentifierExpressionNode(identifier);
    }

    private static ListItem<ArithmeticExpressionNode> operands2list(ArithmeticExpressionNode... operands) {
        if (operands.length == 0) {
            return null;
        }

        ListItem<ArithmeticExpressionNode> head, tail;
        head = tail = new ListItem<>();

        for (int i = 0; i < operands.length; i++) {
            tail.key = operands[i];

            if (i < operands.length - 1) {
                tail = tail.next = new ListItem<>();
            }
        }

        return head;
    }

    private static Map<String, MyNumber> getIdentifiers() {
        return Map.of(
            "a", mratio(2, 3),
            "b", mint(3),
            "c", mreal("2.5"),
            "nice", MyIntegerTest.getSixtyNine()
        );
    }

    private static ArithmeticExpressionNode getRoot() {
        return add(
            identifier("a"),
            div(
                expt(aint(2), identifier("b")),
                mul(
                    ln(identifier("e")),
                    identifier("c"))));
    }

    private static ArithmeticExpressionNode aint(long value) {
        return new LiteralExpressionNode(mint(value));
    }

    private static MyInteger mint(long value) {
        return new MyInteger(integer(value));
    }

    private static BigInteger integer(long value) {
        return BigInteger.valueOf(value);
    }

    private static ArithmeticExpressionNode aratio(long numerator, long denominator) {
        return new LiteralExpressionNode(mratio(numerator, denominator));
    }

    private static MyRational mratio(long numerator, long denominator) {
        return new MyRational(ratio(numerator, denominator));
    }

    private static Rational ratio(long numerator, long denominator) {
        return new Rational(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    private static MyReal mreal(String value) {
        return new MyReal(real(value));
    }

    private static BigDecimal real(String value) {
        return new BigDecimal(value).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);
    }

    private static void assertAlmostEquals(BigDecimal expected, BigDecimal actual) {
        var delta = expected.subtract(actual).abs();

        if (EPSILON.compareTo(delta) < 0) {
            assertEquals(expected, actual);
        }
    }
}

