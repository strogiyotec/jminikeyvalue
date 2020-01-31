package com.lightbox.jmkv.btree;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test {@link TheoreticalKeyPosition}.
 */
@SuppressWarnings("MagicNumber")
public final class TheoreticalKeyPositionTest {

    /**
     * Test key which is lower than all node keys.
     */
    @Test
    public void testLowKey() {
        final BtreeNode node = new BtreeNode(2, 0);
        node.addKey(3, "");
        node.addKey(6, "");
        final TheoreticalKeyPosition search =
                new TheoreticalKeyPosition(node, 2);
        Assert.assertThat(
                search.position(),
                CoreMatchers.is(0)
        );
    }

    /**
     * Test key which is bigger than all node keys.
     */
    @Test
    public void testHighKey() {
        final BtreeNode node = new BtreeNode(2, 0);
        node.addKey(6, "");
        node.addKey(7, "");
        final TheoreticalKeyPosition search =
                new TheoreticalKeyPosition(node, 9);
        Assert.assertThat(
                search.position(),
                CoreMatchers.is(1)
        );
    }

    /**
     * Test key which bigger than first key and lower than last.
     */
    @Test
    public void testMiddleKey() {
        final BtreeNode node = new BtreeNode(5, 0);
        node.addKey(2, "");
        node.addKey(4, "");
        node.addKey(6, "");
        node.addKey(8, "");
        node.addKey(10, "");
        final TheoreticalKeyPosition search =
                new TheoreticalKeyPosition(node, 7);
        Assert.assertThat(
                search.position(),
                CoreMatchers.is(2)
        );
    }

    /**
     * Test index of existing key.
     * This index should be lesser than actual index
     * on 1
     */
    @Test
    public void testSearchExistingKey() {
        final BtreeNode node = new BtreeNode(5, 0);
        for (int i = 0; i < 5; i++) {
            node.addKey(i, "");
        }
        final TheoreticalKeyPosition search =
                new TheoreticalKeyPosition(node, 3);
        Assert.assertThat(
                search.position(),
                CoreMatchers.is(2)
        );
    }
}
