package com.lightbox.jmkv;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Test {@link ImmutableEntry} class.
 */
@SuppressWarnings("MagicNumber")
public final class ImmutableEntryTest {

    /**
     * Test get key and value methods.
     */
    @Test
    @SuppressWarnings("LineLength")
    public void testGetKeyAndValue() {
        final Map.Entry<Integer, String> entry = new ImmutableEntry<>(22, "Almas");
        Assert.assertThat(entry.getKey(), CoreMatchers.is(22));
        Assert.assertThat(entry.getValue(), CoreMatchers.is("Almas"));
    }

    /**
     * Test set value method.
     */
    @Test(expected = UnsupportedOperationException.class)
    @SuppressWarnings("LineLength")
    public void testInsert() {
        final Map.Entry<Integer, String> entry = new ImmutableEntry<>(22, "Almas");
        entry.setValue("BAD");
    }

    /**
     * Test equals and toString methods.
     */
    @Test
    @SuppressWarnings("LineLength")
    public void testEqualsToString() {
        final Map.Entry<Integer, String> entry = new ImmutableEntry<>(22, "Almas");
        final Map.Entry<Integer, String> secondEntry = new ImmutableEntry<>(22, "Almas");
        final Map.Entry<Integer, String> thirdEntry = new ImmutableEntry<>(22, "BAD");
        Assert.assertEquals(entry, secondEntry);
        Assert.assertNotEquals(entry, thirdEntry);
        Assert.assertThat(
                entry.toString(),
                CoreMatchers.is(
                        String.format(
                                "key=%s,value=%s",
                                entry.getKey(),
                                entry.getValue()
                        )
                )
        );
    }
}
