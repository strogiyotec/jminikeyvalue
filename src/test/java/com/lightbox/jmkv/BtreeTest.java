package com.lightbox.jmkv;

import com.lightbox.jmkv.btree.Btree;
import org.junit.Test;

/**
 * Test {@link com.lightbox.jmkv.btree.Btree} class.
 */
public final class BtreeTest {

    @Test
    @SuppressWarnings("MagicNumber")
    public void testPut() {
        final Btree btree = new Btree(2);
        btree.put(1, "");
        btree.put(2, "");
        btree.put(3, "");
        btree.put(4, "");
        btree.put(5, "");
        btree.put(6, "");
    }
}
