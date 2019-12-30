package com.lightbox.jmkv.btree;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link BtreeSearch}.
 */
@SuppressWarnings("MagicNumber")
public final class BtreeSearchTest {

    /**
     * Length of keys.
     */
    private static final int KEYS_LENGTH = 6;

    /**
     * Node for testing.
     */
    private BtreeNode testNode;

    /**
     * Setup node.
     */
    @Before
    public void setup() {
        this.testNode = new BtreeNode(KEYS_LENGTH, 0);
        for (int i = 1; testNode.keys() < KEYS_LENGTH; i = i * 2) {
            this.testNode.addKey(i, "");
        }
    }

    /**
     * Test search with existing key.
     */
    @Test
    public void testSearchExisting() {
        final BtreeSearch search = new BtreeSearch(this.testNode, 4);
        Assert.assertTrue(search.found());
        Assert.assertThat(search.position(), CoreMatchers.is(2));
    }

    /**
     * Test search with non existing key.
     */
    @Test
    public void testSearchNonExisting() {
        final BtreeSearch search = new BtreeSearch(this.testNode, 6);
        Assert.assertFalse(search.found());
        Assert.assertThat(search.position(), CoreMatchers.is(4));
    }
}
