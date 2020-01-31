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
    public void testSortedKeys() {
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
        final BtreeNode node = this.nodeWithKeys(1, 6);
        Assert.assertThat(node.keys(), CoreMatchers.is(6));
    }

    /**
     * Test middle key remove.
     */
    @Test
    public void testRemoveMiddleKey() {
        final BtreeNode node = this.nodeWithKeys(1, 6);
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
        final BtreeNode node = nodeWithKeys(1, 6);
        node.removeFirstKey();
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
        final BtreeNode node = nodeWithKeys(1, 6);
        node.removeLastKey();
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
     * Test add all keys from one node to another.
     */
    @Test
    public void testAddAllKeys() {
        final BtreeNode fromNode = nodeWithKeys(0, 5);
        final BtreeNode toNode = new BtreeNode(6, 0);
        toNode.addAllKeys(fromNode);
        for (int i = 0; i < 6; i++) {
            Assert.assertThat(toNode.key(i).key, CoreMatchers.is(i));
        }
    }

    /**
     * Test add all children from one node to another.
     */
    @Test
    public void testAddAllChildren() {
        final int amountOfChildren = 2;
        final int amountOfKeys = 2;
        final BtreeNode nodeFrom =
                this.nodeWithChildren(
                        amountOfChildren,
                        amountOfKeys
                );
        final BtreeNode nodeTo = new BtreeNode(0, amountOfChildren);
        nodeTo.addAllChildren(nodeFrom);
        for (int i = 0; i < amountOfChildren; i++) {
            final BtreeNode child = nodeFrom.child(i);
            for (int j = 0; j < amountOfKeys; j++) {
                Assert.assertThat(
                        child.key(j),
                        CoreMatchers.is(
                                new NodeKey(j, "")
                        )
                );
            }
        }
    }

    /**
     * Test index of child.
     * Add search child in position 2 and then search itgits
     */
    @Test
    public void testIndexOfChildren() {
        final BtreeNode main = new BtreeNode(2, 4);
        final BtreeNode searchNode = new BtreeNode(2, 4);
        searchNode.addKey(new NodeKey(2, ""));
        for (int i = 0; i < 4; i++) {
            if (i == 2) {
                main.addChild(searchNode);
            } else {
                final BtreeNode node = new BtreeNode(2, 4);
                node.addKey(new NodeKey(i, ""));
                main.addChild(node);
            }
        }
        Assert.assertThat(main.indexOfNode(searchNode), CoreMatchers.is(2));
    }

    /**
     * Test remove parent method.
     */
    @Test
    public void testRemoveParent() {
        final BtreeNode node =
                new BtreeNode(
                        new BtreeNode(5, 5),
                        5,
                        0
                );
        Assert.assertTrue(node.hasParent());
        node.removeParent();
        Assert.assertFalse(node.hasParent());
    }

    /**
     * Generate node for test with keys.
     *
     * @param firstValue Value of the first key
     * @param lastValue  Value of the last key
     * @return Node
     */
    private BtreeNode nodeWithKeys(final int firstValue, final int lastValue) {
        final BtreeNode node = new BtreeNode(6, 0);
        IntStream
                .rangeClosed(firstValue, lastValue)
                .forEach(value -> node.addKey(value, ""));
        return node;
    }

    /**
     * Generate node for test with children.
     * Generated node doesn't have keys
     * Generated children have keys but don't have children
     *
     * @param children     Amount of children in generated node
     * @param keysPreChild Amount of keys in each child
     * @return Node
     */
    private BtreeNode nodeWithChildren(
            final int children,
            final int keysPreChild
    ) {
        final BtreeNode node = new BtreeNode(0, children);
        for (int i = 0; i < children; i++) {
            final BtreeNode child = new BtreeNode(keysPreChild, 0);
            for (int j = 0; j < keysPreChild; j++) {
                child.addKey(j, "");
            }
            node.addChild(child);
        }
        return node;
    }
}
