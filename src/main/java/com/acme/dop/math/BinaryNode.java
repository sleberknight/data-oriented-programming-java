package com.acme.dop.math;

public sealed interface BinaryNode extends Node {

    record AddNode(Node left, Node right) implements BinaryNode {
    }

    record MulNode(Node left, Node right) implements BinaryNode {
    }
}
