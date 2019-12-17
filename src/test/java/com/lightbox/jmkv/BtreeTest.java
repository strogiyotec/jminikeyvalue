package com.lightbox.jmkv;

import com.lightbox.jmkv.btree.Btree;
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
        btree.put(1, "");
        btree.put(2, "");
        btree.put(3, "");
        btree.put(4, "");
        btree.put(5, "answer");
        btree.put(6, "");
        btree.put(7, "");
        btree.put(8, "");
        btree.put(9, "");
        Assert.assertThat(btree.get(5), CoreMatchers.is("answer"));
    }
}
