package com.acme.dop.btree;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
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
        A.inorder(value -> output.append(value).append(" "));

        assertThat(output).contains("12 20 8 42 22 22 ");
    }

    @Test
    void shouldNotAllowNullNodes() {
        assertAll(
                () -> assertThatIllegalArgumentException().isThrownBy(() ->
                        Tree.full(Tree.leaf(42), 84, null)),
                () -> assertThatIllegalArgumentException().isThrownBy(() ->
                        Tree.full(Tree.nil(), 24, null))
        );
    }

    @Test
    void shouldCreateTrees() {
        assertAll(
                // full
                () -> assertThat(Tree.full(Tree.leaf(20), 42, Tree.leaf(22)).left()).isEqualTo(Tree.leaf(20)),
                () -> assertThat(Tree.full(Tree.leaf(10), 42, Tree.leaf(32)).right()).isEqualTo(Tree.leaf(32)),
                () -> assertThat(Tree.full(Tree.nil(), 42, Tree.nil()).val()).isEqualTo(42),

                // left
                () -> assertThat(Tree.left(Tree.leaf(20), 84).left()).isEqualTo(Tree.leaf(20)),
                () -> assertThat(Tree.left(Tree.leaf(42), 84).right()).isEqualTo(Tree.nil()),
                () -> assertThat(Tree.left(Tree.leaf(60), 84).val()).isEqualTo(84),

                // right
                () -> assertThat(Tree.right(126, Tree.leaf(42)).left()).isEqualTo(Tree.nil()),
                () -> assertThat(Tree.right(126, Tree.leaf(84)).right()).isEqualTo(Tree.leaf(84)),
                () -> assertThat(Tree.right(126, Tree.leaf(20)).val()).isEqualTo(126),

                // leaf
                () -> assertThat(Tree.leaf(21).left()).isEqualTo(Tree.nil()),
                () -> assertThat(Tree.leaf(21).right()).isEqualTo(Tree.nil()),
                () -> assertThat(Tree.leaf(21).val()).isEqualTo(21)
        );
    }
}
