package com.lightbox.jmkv.btree;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Test for {@link BtreeNode}.
 */
@SuppressWarnings("MagicNumber")
public final class BtreeNodeTest {

    /**
     * Test that keys are stored in sorted order.
     */
    @Test
    public void testSorted() {
        final BtreeNode node = new BtreeNode(6, 0);
        for (int i = 0; i < 6; i++) {
            if (i != 3) {
                node.addKey(i, "");
            }
        }
        node.addKey(3, "");
        for (int i = 0; i < 6; i++) {
            Assert.assertThat(node.key(i), CoreMatchers.is(new NodeKey(i, "")));
        }
    }

    /**
     * Test that keys are stored in sorted order.
     */
    @Test
    public void testKeysSize() {
        final BtreeNode node = this.createNode();
        Assert.assertThat(node.keys(), CoreMatchers.is(6));
    }

    /**
     * Test middle key remove.
     */
    @Test
    public void testRemoveMiddleKey() {
        final BtreeNode node = this.createNode();
        node.removeKey(2);
        final AtomicInteger cnt = new AtomicInteger(0);
        Arrays.asList(1, 2, 4, 5, 6)
                .forEach(value ->
                        Assert.assertThat(
                                value,
                                CoreMatchers.is(
                                        node.key(cnt.getAndIncrement()).key
                                )
                        )
                );

    }

    /**
     * Test first key remove.
     */
    @Test
    public void testRemoveFirstKey() {
        final BtreeNode node = createNode();
        node.removeKey(0);
        final AtomicInteger cnt = new AtomicInteger(0);
        Arrays.asList(2, 3, 4, 5, 6)
                .forEach(value ->
                        Assert.assertThat(
                                value,
                                CoreMatchers.is(
                                        node.key(cnt.getAndIncrement()).key
                                )
                        )
                );

    }

    /**
     * Test last key remove.
     */
    @Test
    public void testRemoveLastKey() {
        final BtreeNode node = createNode();
        node.removeKey(5);
        final AtomicInteger cnt = new AtomicInteger(0);
        Arrays.asList(1, 2, 3, 4, 5)
                .forEach(value ->
                        Assert.assertThat(
                                value,
                                CoreMatchers.is(
                                        node.key(cnt.getAndIncrement()).key
                                )
                        )
                );

    }

    /**
     * Generate node for test.
     *
     * @return Node
     */
    private BtreeNode createNode() {
        final BtreeNode node = new BtreeNode(6, 0);
        IntStream.rangeClosed(1, 6).forEach(value -> node.addKey(value, ""));
        return node;
    }
}
