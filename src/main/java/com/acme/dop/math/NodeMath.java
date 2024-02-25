package com.acme.dop.math;

import static com.acme.dop.math.BinaryNode.AddNode;
import static com.acme.dop.math.BinaryNode.MulNode;
import static com.acme.dop.math.BinaryNode.add;
import static com.acme.dop.math.BinaryNode.mul;
import static com.acme.dop.math.Node.ConstNode;
import static com.acme.dop.math.Node.ExpNode;
import static com.acme.dop.math.Node.NegNode;
import static com.acme.dop.math.Node.VarNode;
import static com.acme.dop.math.Node.exp;
import static com.acme.dop.math.Node.neg;
import static com.acme.dop.math.Node.val;
import static java.util.Objects.requireNonNull;

import java.util.function.Function;

public class NodeMath {
    private NodeMath() {
    }

    public static double eval(Node n) {
        return eval(n, null);
    }

    public static double eval(Node n, Function<String, Double> vars) {
        return switch (n) {
            case AddNode(var left, var right) -> eval(left, vars) + eval(right, vars);
            case MulNode(var left, var right) -> eval(left, vars) * eval(right, vars);
            case ExpNode(var node, int exp) -> Math.pow(eval(node, vars), exp);
            case NegNode(var node) -> -eval(node, vars);
            case ConstNode(double val) -> val;
            case VarNode(String name) -> apply(vars, name);
        };
    }

    private static double apply(Function<String, Double> vars, String name) {
        return requireNonNull(vars, "vars must not be null when VarNodes exist").apply(name);
    }

    public static String format(Node n) {
        return switch (n) {
            case AddNode(var left, var right) -> String.format("(%s + %s)", format(left), format(right));
            case MulNode(var left, var right) -> String.format("(%s * %s)", format(left), format(right));
            case ExpNode(var node, int exp) -> String.format("%s^%d", format(node), exp);
            case NegNode(var node) -> String.format("-%s", format(node));
            case ConstNode(double val) -> Double.toString(val);
            case VarNode(String name) -> name;
        };
    }

    /**
     * Differentiate with respect to a single variable.
     *
     * @param n       the expression to differentiate as a Node
     * @param varName the variable, e.g., x or y
     * @return the differentiation result as a Node
     */
    public static Node diff(Node n, String varName) {
        return switch (n) {

            case AddNode(var left, var right) -> add(diff(left, varName), diff(right, varName));

            // From the article, the following two MulNode cases handle the special cases of k*node and node*k
            // where k is a constant.

            // NOTE: the sample article code shows using an identifier 'k' after ConstNode(double val) but
            // this does not currently compile. When you try "ConstNode(double val) k", the compiler gives
            // three errors: " ')' expected ", "not a statement", and " ';' expected " at the position of "k".
            // In the editor, IntelliJ says "Identifier is now allowed here". Because of this, the return value
            // creates a new ConstNode(val) instead of using the (disallowed) "k" directly in the MulNode.
            case MulNode(var left, ConstNode(double val)) -> mul(val(val), diff(left, varName));
            case MulNode(ConstNode(double val), var right) -> mul(val(val), diff(right, varName));

            // This handles generic multiplication using the product rule
            case MulNode(var left, var right) -> add(
                    mul(left, diff(right, varName)),
                    mul(diff(left, varName), right)
            );

            case ExpNode(var node, int exp) -> mul(val(exp), mul(exp(node, exp - 1), diff(node, varName)));

            case NegNode(var node) -> neg(diff(node, varName));

            case ConstNode(double ignored) -> val(0);

            case VarNode(String name) -> name.equals(varName) ? val(1) : val(0);
        };
    }
}
