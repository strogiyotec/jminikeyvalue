package com.lightbox.jmkv.btree;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

/**
 * Test {@link com.lightbox.jmkv.btree.Btree} class.
 */
@SuppressWarnings("MagicNumber")
public final class BtreeTest {

    /**
     * Test put method of {@link Btree}.
     */
    @Test
    public void testPutAndGet() {
        final Btree btree = new Btree(2);
        for (int i = 1; i <= 17; i++) {
            if (i == 5) {
                btree.put(5, "answer");
            } else {
                btree.put(i, "");
            }
        }
        Assert.assertThat(btree.get(5), CoreMatchers.is("answer"));
        Assert.assertThat(btree.get(13), CoreMatchers.is(""));
        Assert.assertNull(btree.get(18));
        Assert.assertNull(btree.get(0));
    }

    /**
     * Test that node was split after overflow.
     */
    @Test
    public void testSplit() {
        final Btree btree = new Btree(2);
        IntStream.of(3, 1, 5, 4).forEach(value -> btree.put(value, ""));
        Assert.assertThat(btree.root.keys(), CoreMatchers.is(1));
        Assert.assertThat(btree.root.key(0).key, CoreMatchers.is(4));
        Assert.assertThat(btree.root.child(0).key(0).key, CoreMatchers.is(1));
        Assert.assertThat(btree.root.child(0).key(1).key, CoreMatchers.is(3));
        Assert.assertThat(btree.root.child(1).key(0).key, CoreMatchers.is(5));
    }

    /**
     * Test remove key from root.
     */
    @Test
    public void testRemoveFromRoot() {
        final Btree btree = new Btree(2);
        IntStream.of(3, 1, 5).forEach(value -> btree.put(value, ""));
        btree.remove(1);
        Assert.assertThat(btree.root.keys(), CoreMatchers.is(2));
        Assert.assertThat(btree.root.key(0).key, CoreMatchers.is(3));
        Assert.assertThat(btree.root.key(1).key, CoreMatchers.is(5));

    }
}
