package com.acme.dop.math;

import static com.acme.dop.math.BinaryNode.*;
import static com.acme.dop.math.Node.ConstNode;
import static com.acme.dop.math.Node.ExpNode;
import static com.acme.dop.math.Node.NegNode;
import static com.acme.dop.math.Node.VarNode;

import java.util.function.Function;

public class NodeMath {
    private NodeMath() {
    }

    static double eval(Node n, Function<String, Double> vars) {
        return switch (n) {
            case AddNode(var left, var right) -> eval(left, vars) + eval(right, vars);
            case MulNode(var left, var right) -> eval(left, vars) * eval(right, vars);
            case ExpNode(var node, int exp) -> Math.pow(eval(node, vars), exp);
            case NegNode(var node) -> -eval(node, vars);
            case ConstNode(double val) -> val;
            case VarNode(String name) -> vars.apply(name);
        };
    }

    static String format(Node n) {
        return switch (n) {
            case AddNode(var left, var right) -> String.format("(%s + %s)", format(left), format(right));
            case MulNode(var left, var right) -> String.format("(%s * %s)", format(left), format(right));
            case ExpNode(var node, int exp) -> String.format("%s^%d", format(node), exp);
            case NegNode(var node) -> String.format("-%s", format(node));
            case ConstNode(double val) -> Double.toString(val);
            case VarNode(String name) -> name;
        };
    }
}
