package com.acme.dop.btree;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

class TreeTest {

    @Test
    void shouldCheckTreeContains() {
        var B = Tree.leaf(20);
        var C = Tree.leaf(22);
        var A = Tree.full(B, 42, C);

        assertAll(
            () -> assertThat(A.contains(42)).isTrue(),
            () -> assertThat(A.contains(20)).isTrue(),
            () -> assertThat(A.contains(22)).isTrue(),
            () -> assertThat(A.contains(84)).isFalse(),
            () -> assertThat(A.contains(4)).isFalse()
        );
    }

    @Test
    void shouldPerformInOrderOperation() {
        var D = Tree.leaf(12);
        var E = Tree.leaf(8);
        var F = Tree.leaf(22);

        var B = Tree.full(D, 20, E);
        var C = Tree.right(22, F);

        var A = Tree.full(B, 42, C);

        var output = new StringBuilder();
        A.inorder(value -> output.append(value + " "));

        assertThat(output).contains("12 20 8 42 22 22 ");
    }
}
