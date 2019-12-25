package com.lightbox.jmkv.btree;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test {@link com.lightbox.jmkv.btree.Btree} class.
 */
public final class BtreeTest {

    /**
     * Test put method of {@link Btree}.
     */
    @Test
    @SuppressWarnings("MagicNumber")
    public void testPut() {
        final Btree btree = new Btree(2);
        for (int i = 1; i <= 17; i++) {
            if (i == 5) {
                btree.put(5, "answer");
            } else {
                btree.put(i, "");
            }
        }
        Assert.assertThat(btree.get(5), CoreMatchers.is("answer"));
    }


}
