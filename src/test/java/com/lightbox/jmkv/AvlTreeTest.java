package com.lightbox.jmkv;

import com.lightbox.jmkv.avl.AvlTree;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Test {@link com.lightbox.jmkv.avl.AvlTree} class.
 */
@SuppressWarnings("MagicNumber")
public final class AvlTreeTest {

    /**
     * Test put method.
     * Assert that Tree was rebalanced and now has given order
     */
    @Test
    public void testPut() {
        final AvlTree tree = AvlTreeTest.getTree();
        Assert.assertThat(tree.keySet(), Matchers.contains(12, 10, 5, 15));
    }

    /**
     * Test values method of Avl tree.
     * Check that values are in the same position as their balanced keys
     */
    @Test
    public void testValueSet() {
        final AvlTree tree = AvlTreeTest.getTree();
        Assert.assertThat(
                tree.values(),
                Matchers.contains("almat", "abzal", "zhanara", "almas")
        );
    }

    /**
     * Test contains method of Avl tree.
     */
    @Test
    public void testContains() {
        final AvlTree tree = AvlTreeTest.getTree();
        Assert.assertTrue(tree.containsKey(15));
        Assert.assertTrue(tree.containsKey(10));
        Assert.assertTrue(tree.containsKey(12));
        Assert.assertTrue(tree.containsKey(5));
        Assert.assertFalse(tree.containsKey(322));
    }

    /**
     * Test put all method of Avl tree.
     * Check that when put all was called then
     * Avl tree was rebalanced.
     */
    @Test
    public void testPutAll() {
        final AvlTree tree = new AvlTree(10, "test");
        final Map<Integer, String> map = new HashMap<>(2, 1.0F);
        map.put(12, "Hello");
        map.put(15, "Me friend");
        tree.putAll(map);
        Assert.assertThat(tree.keySet(), Matchers.contains(12, 10, 15));
    }

    /**
     * Get avl tree for testing.
     *
     * @return Avl tree instance
     */
    private static AvlTree getTree() {
        final AvlTree tree = new AvlTree(15, "almas");
        tree.put(10, "abzal");
        tree.put(12, "almat");
        tree.put(5, "zhanara");
        return tree;
    }
}
