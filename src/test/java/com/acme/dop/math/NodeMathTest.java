package com.acme.dop.math;

import static com.acme.dop.math.BinaryNode.add;
import static com.acme.dop.math.BinaryNode.mul;
import static com.acme.dop.math.Node.exp;
import static com.acme.dop.math.Node.neg;
import static com.acme.dop.math.Node.val;
import static com.acme.dop.math.Node.variable;
import static com.acme.dop.math.NodeMath.eval;
import static com.acme.dop.math.NodeMath.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.Function;

class NodeMathTest {

    @Test
    void shouldAdd() {
        var left = val(20);
        var right = val(22);
        var adder = add(left, right);

        var result = eval(adder);
        assertThat(result).isEqualTo(42.0);
    }

    @Test
    void shouldMultiply() {
        var left = val(2);
        var right = val(21);
        var multiplier = mul(left, right);

        var result = eval(multiplier);
        assertThat(result).isEqualTo(42.0);
    }

    @Test
    void shouldPerformExponentiation() {
        var base = val(2);
        var expNode = exp(base, 6);

        var result = eval(expNode);
        assertThat(result).isEqualTo(64.0);
    }

    @Test
    void shouldNegate() {
        var num = val(42);
        var negNode = neg(num);

        var result = eval(negNode);
        assertThat(result).isEqualTo(-42.0);
    }

    @Test
    void shouldUseVariables() {
        var left = variable("a");
        var right = variable("b");
        var vars = Map.of(
                "a", 2.0,
                "b", 5.0
        );
        Function<String, Double> fn = vars::get;

        assertAll(
                () -> assertThat(eval(add(left, right), fn)).isEqualTo(7.0),
                () -> assertThat(eval(mul(left, right), fn)).isEqualTo(10.0),
                () -> assertThat(eval(exp(left, 3), fn)).isEqualTo(8.0),
                () -> assertThat(eval(exp(right, 2), fn)).isEqualTo(25.0),
                () -> assertThat(eval(neg(left), fn)).isEqualTo(-2.0),
                () -> assertThat(eval(neg(right), fn)).isEqualTo(-5.0)
        );
    }

    @Test
    void shouldDoComplexCalculations() {
        var a = variable("a");
        var b = variable("b");
        var c = val(3);
        var d = val(6);
        var vars = Map.of(
                "a", 2.0,
                "b", 5.0
        );
        Function<String, Double> fn = vars::get;

        var left = mul(a, b);
        var right = mul(neg(c), d);
        var addNode = add(left, right);

        var result = eval(addNode, fn);
        assertThat(result).isEqualTo(-8.0);
    }

    @Test
    void shouldFormatAdditionExpressions() {
        var left = val(20);
        var right = val(22);
        var adder = add(left, right);

        var result = format(adder);
        assertThat(result).isEqualTo("(20.0 + 22.0)");
    }

    @Test
    void shouldFormatMultiplicationExpressions() {
        var left = val(2);
        var right = val(21);
        var multiplier = mul(left, right);

        var result = format(multiplier);
        assertThat(result).isEqualTo("(2.0 * 21.0)");
    }

    @Test
    void shouldFormatExponentiationExpressions() {
        var base = val(2);
        var expNode = exp(base, 6);

        var result = format(expNode);
        assertThat(result).isEqualTo("2.0^6");
    }

    @Test
    void shouldFormatNegationExpressions() {
        var num = val(42);
        var negNode = neg(num);

        var result = format(negNode);
        assertThat(result).isEqualTo("-42.0");
    }

    @Test
    void shouldFormatExpressionsWithVariables() {
        var left = variable("a");
        var right = variable("b");

        assertAll(
                () -> assertThat(format(add(left, right))).isEqualTo("(a + b)"),
                () -> assertThat(format(mul(left, right))).isEqualTo("(a * b)"),
                () -> assertThat(format(exp(left, 3))).isEqualTo("a^3"),
                () -> assertThat(format(exp(right, 2))).isEqualTo("b^2"),
                () -> assertThat(format(neg(left))).isEqualTo("-a"),
                () -> assertThat(format(neg(right))).isEqualTo("-b")
        );
    }

    @Test
    void shouldFormatComplexExpressions() {
        var a = variable("a");
        var b = variable("b");
        var c = val(3);
        var d = val(6);

        var left = mul(a, b);
        var right = mul(neg(c), d);
        var addNode = add(left, right);

        var result = format(addNode);
        assertThat(result).isEqualTo("((a * b) + (-3.0 * 6.0))");
    }
}
