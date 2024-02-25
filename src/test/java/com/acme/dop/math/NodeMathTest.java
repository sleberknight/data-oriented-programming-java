package com.acme.dop.math;

import static com.acme.dop.math.BinaryNode.AddNode;
import static com.acme.dop.math.BinaryNode.ConstNode;
import static com.acme.dop.math.BinaryNode.ExpNode;
import static com.acme.dop.math.BinaryNode.MulNode;
import static com.acme.dop.math.BinaryNode.NegNode;
import static com.acme.dop.math.BinaryNode.VarNode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.Function;

class NodeMathTest {

    @Test
    void shouldAdd() {
        var left = new ConstNode(20);
        var right = new ConstNode(22);
        var adder = new AddNode(left, right);

        var result = NodeMath.eval(adder, null);
        assertThat(result).isEqualTo(42.0);
    }

    @Test
    void shouldMultiply() {
        var left = new ConstNode(2);
        var right = new ConstNode(21);
        var multiplier = new MulNode(left, right);

        var result = NodeMath.eval(multiplier, null);
        assertThat(result).isEqualTo(42.0);
    }

    @Test
    void shouldPerformExponentiation() {
        var base = new ConstNode(2);
        var expNode = new ExpNode(base, 6);

        var result = NodeMath.eval(expNode, null);
        assertThat(result).isEqualTo(64.0);
    }

    @Test
    void shouldNegate() {
        var num = new ConstNode(42);
        var negNode = new NegNode(num);

        var result = NodeMath.eval(negNode, null);
        assertThat(result).isEqualTo(-42.0);
    }

    @Test
    void shouldUseVariables() {
        var left = new VarNode("a");
        var right = new VarNode("b");
        var vars = Map.of(
                "a", 2.0,
                "b", 5.0
        );
        Function<String, Double> fn = vars::get;

        assertAll(
                () -> assertThat(NodeMath.eval(new AddNode(left, right), fn)).isEqualTo(7.0),
                () -> assertThat(NodeMath.eval(new MulNode(left, right), fn)).isEqualTo(10.0),
                () -> assertThat(NodeMath.eval(new ExpNode(left, 3), fn)).isEqualTo(8.0),
                () -> assertThat(NodeMath.eval(new ExpNode(right, 2), fn)).isEqualTo(25.0),
                () -> assertThat(NodeMath.eval(new NegNode(left), fn)).isEqualTo(-2.0),
                () -> assertThat(NodeMath.eval(new NegNode(right), fn)).isEqualTo(-5.0)
        );
    }

    @Test
    void shouldDoComplexCalculations() {
        var a = new VarNode("a");
        var b = new VarNode("b");
        var c = new ConstNode(3);
        var d = new ConstNode(6);
        var vars = Map.of(
                "a", 2.0,
                "b", 5.0
        );
        Function<String, Double> fn = vars::get;

        var left = new MulNode(a, b);
        var right = new MulNode(new NegNode(c), d);
        var addNode = new AddNode(left, right);

        var result = NodeMath.eval(addNode, fn);
        assertThat(result).isEqualTo(-8.0);
    }

    @Test
    void shouldFormatAdditionExpressions() {
        var left = new ConstNode(20);
        var right = new ConstNode(22);
        var adder = new AddNode(left, right);

        var result = NodeMath.format(adder);
        assertThat(result).isEqualTo("(20.0 + 22.0)");
    }

    @Test
    void shouldFormatMultiplicationExpressions() {
        var left = new ConstNode(2);
        var right = new ConstNode(21);
        var multiplier = new MulNode(left, right);

        var result = NodeMath.format(multiplier);
        assertThat(result).isEqualTo("(2.0 * 21.0)");
    }

    @Test
    void shouldFormatExponentiationExpressions() {
        var base = new ConstNode(2);
        var expNode = new ExpNode(base, 6);

        var result = NodeMath.format(expNode);
        assertThat(result).isEqualTo("2.0^6");
    }

    @Test
    void shouldFormatNegationExpressions() {
        var num = new ConstNode(42);
        var negNode = new NegNode(num);

        var result = NodeMath.format(negNode);
        assertThat(result).isEqualTo("-42.0");
    }

    @Test
    void shouldFormatExpressionsWithVariables() {
        var left = new VarNode("a");
        var right = new VarNode("b");

        assertAll(
                () -> assertThat(NodeMath.format(new AddNode(left, right))).isEqualTo("(a + b)"),
                () -> assertThat(NodeMath.format(new MulNode(left, right))).isEqualTo("(a * b)"),
                () -> assertThat(NodeMath.format(new ExpNode(left, 3))).isEqualTo("a^3"),
                () -> assertThat(NodeMath.format(new ExpNode(right, 2))).isEqualTo("b^2"),
                () -> assertThat(NodeMath.format(new NegNode(left))).isEqualTo("-a"),
                () -> assertThat(NodeMath.format(new NegNode(right))).isEqualTo("-b")
        );
    }

    @Test
    void shouldFormatComplexExpressions() {
        var a = new VarNode("a");
        var b = new VarNode("b");
        var c = new ConstNode(3);
        var d = new ConstNode(6);

        var left = new MulNode(a, b);
        var right = new MulNode(new NegNode(c), d);
        var addNode = new AddNode(left, right);

        var result = NodeMath.format(addNode);
        assertThat(result).isEqualTo("((a * b) + (-3.0 * 6.0))");
    }
}
