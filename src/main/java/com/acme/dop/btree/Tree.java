package com.acme.dop.btree;

import static java.util.Objects.isNull;

import java.util.function.Consumer;

public sealed interface Tree<T> {

    record Nil<T>() implements Tree<T> { }

    record Node<T>(Tree<T> left, T val, Tree<T> right) implements Tree<T> {

        public Node {
            if (isNull(left) || isNull(right)) {
                throw new IllegalArgumentException("left and right must not be null");
            }
        }
    }

    static <T> Nil<T> nil() {
        return new Nil<>();
    }

    static <T> Node<T> leaf(T value) {
        return new Node<>(nil(), value, nil());
    }

    static <T> Node<T> left(Tree<T> left, T val) {
        return new Node<>(left, val, nil());
    }

    static <T> Node<T> right(T val, Tree<T> right) {
        return new Node<>(nil(), val, right);
    }

    static <T> Node<T> full(Tree<T> left, T val, Tree<T> right) {
        return new Node<>(left, val, right);
    }

    default boolean contains(T target) {
        return contains(this, target);
    }

    static <T> boolean contains(Tree<T> tree, T target) {
        return switch (tree) {
            case Nil<T>() -> false;
            case Node<T>(var left, var val, var right) ->
                target.equals(val) || contains(left, target) || contains(right, target);
        };
    }

    default void inorder(Consumer<T> c) {
        inorder(this, c);
    }

    static <T> void inorder(Tree<T> tree, Consumer<T> c) {
        switch (tree) {
            case Nil():
                break;

            case Node(var left, var val, var right):
                inorder(left, c);
                c.accept(val);
                inorder(right, c);
                break;
        }
    }
}
