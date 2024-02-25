package com.acme.dop.math;

import static com.acme.dop.math.Node.ConstNode;
import static com.acme.dop.math.Node.ExpNode;
import static com.acme.dop.math.Node.NegNode;
import static com.acme.dop.math.Node.VarNode;

public sealed interface Node permits BinaryNode, ConstNode, ExpNode, NegNode, VarNode {

    record ExpNode(Node left, int exp) implements Node {
    }

    record NegNode(Node node) implements Node {
    }

    record ConstNode(double val) implements Node {
    }

    record VarNode(String name) implements Node {
    }
}
