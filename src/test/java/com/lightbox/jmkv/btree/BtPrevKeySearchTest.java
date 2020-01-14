package com.lightbox.jmkv.btree;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test {@link BtPrevKeySearch}.
 */
@SuppressWarnings("MagicNumber")
public final class BtPrevKeySearchTest {

    /**
     * Test index of non existing key.
     */
    @Test
    public void testSearchNonExistingKey() {
        final BtreeNode node = new BtreeNode(2, 0);
        node.addKey(3, "");
        node.addKey(6, "");
        final BtPrevKeySearch search = new BtPrevKeySearch(node, 2);
        Assert.assertThat(search.position(), CoreMatchers.is(0));
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
        final BtPrevKeySearch search = new BtPrevKeySearch(node, 3);
        Assert.assertThat(search.position(), CoreMatchers.is(2));
    }
}
