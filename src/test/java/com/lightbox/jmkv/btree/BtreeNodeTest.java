package com.lightbox.jmkv.btree;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

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
}
