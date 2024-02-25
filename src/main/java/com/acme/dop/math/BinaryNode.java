package com.acme.dop.math;

public sealed interface BinaryNode extends Node {

    record AddNode(Node left, Node right) implements BinaryNode {
    }

    record MulNode(Node left, Node right) implements BinaryNode {
    }

    static AddNode add(Node left, Node right) {
        return new AddNode(left, right);
    }

    static MulNode mul(Node left, Node right) {
        return new MulNode(left, right);
    }
}
