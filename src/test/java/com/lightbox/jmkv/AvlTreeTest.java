package com.lightbox.jmkv;

import com.lightbox.jmkv.avl.AvlTree;
import org.hamcrest.CoreMatchers;
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
     * Test remove node without children.
     */
    @Test
    public void testRemoveWithRightRotation() {
        final AvlTree tree = new AvlTree(10, "");
        tree.put(9, "");
        tree.put(15, "To remove");
        tree.put(8, "");
        Assert.assertThat(tree.remove(15), CoreMatchers.is("To remove"));
        Assert.assertThat(tree.size(), CoreMatchers.is(3));
        //right balance after removing 15
        Assert.assertThat(tree.keySet(), Matchers.contains(9, 8, 10));
    }

    /**
     * Test remove node without children.
     * Last example from https://www.javatpoint.com/deletion-in-avl-tree
     */
    @Test
    public void testRemoveWithRightRotationDeep() {
        final AvlTree tree = new AvlTree(50, "");
        tree.put(40, "");
        tree.put(60, "");
        tree.put(30, "");
        tree.put(45, "");
        tree.put(55, "To remove");
        tree.put(10, "");
        Assert.assertThat(tree.remove(55), CoreMatchers.is("To remove"));
        Assert.assertThat(tree.size(), CoreMatchers.is(6));
        Assert.assertThat(
                tree.keySet(),
                Matchers.contains(40, 30, 10, 50, 45, 60)
        );
    }

    /**
     * Test remove node without children.
     */
    @Test
    public void testRemoveWithLeftRightRotation() {
        final AvlTree tree = new AvlTree(50, "");
        tree.put(40, "");
        tree.put(60, "To remove");
        tree.put(45, "");
        Assert.assertThat(tree.remove(60), CoreMatchers.is("To remove"));
        Assert.assertThat(tree.size(), CoreMatchers.is(3));
        Assert.assertThat(tree.keySet(), Matchers.contains(45, 40, 50));

    }

    /**
     * Test remove node without children.
     * http://www.cs.toronto.edu/~krueger/cscB63h/lectures/tut03.txt
     */
    @Test
    public void testRemoveWithOneChildAndRightLeftRotation() {
        final AvlTree tree = new AvlTree(9, "");
        tree.put(6, "");
        tree.put(15, "");
        tree.put(13, "");
        tree.put(20, "");
        tree.put(2, "To remove");
        tree.put(7, "");
        tree.put(1, "");
        tree.put(12, "");
        tree.put(14, "");
        tree.put(18, "");
        tree.put(30, "");
        tree.put(11, "");
        Assert.assertThat(tree.remove(2), CoreMatchers.is("To remove"));
        Assert.assertThat(tree.size(), CoreMatchers.is(12));
        Assert.assertThat(
                tree.keySet(),
                Matchers.contains(13, 9, 6, 1, 7, 12, 11, 15, 14, 20, 18, 30)
        );
    }

    /**
     * Test contains value method of avl tree.
     */
    @Test
    public void testContainsValue() {
        final AvlTree tree = AvlTreeTest.getTree();
        Assert.assertTrue(tree.containsValue("almas"));
        Assert.assertTrue(tree.containsValue("abzal"));
        Assert.assertTrue(tree.containsValue("almat"));
        Assert.assertTrue(tree.containsValue("zhanara"));
        Assert.assertFalse(tree.containsValue("No value"));
    }

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
     * Test size method of Avl tree.
     */
    @Test
    public void testSize() {
        final AvlTree tree = AvlTreeTest.getTree();
        Assert.assertThat(tree.size(), CoreMatchers.is(4));
        tree.put(17, "");
        Assert.assertThat(tree.size(), CoreMatchers.is(5));
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
     * Test entry set method of Avl tree.
     * Check that entry set has balanced order
     */
    @Test
    public void testEntrySet() {
        final AvlTree tree = AvlTreeTest.getTree();
        Assert.assertThat(
                tree.entrySet(),
                Matchers.contains(
                        new ImmutableEntry<>(12, "almat"),
                        new ImmutableEntry<>(10, "abzal"),
                        new ImmutableEntry<>(5, "zhanara"),
                        new ImmutableEntry<>(15, "almas")
                )
        );
    }

    /**
     * Test get method of Avl tree.
     */
    @Test
    public void testGet() {
        final AvlTree tree = AvlTreeTest.getTree();
        Assert.assertThat(tree.get(15), CoreMatchers.is("almas"));
        Assert.assertThat(tree.get(12), CoreMatchers.is("almat"));
        Assert.assertThat(tree.get(10), CoreMatchers.is("abzal"));
        Assert.assertThat(tree.get(5), CoreMatchers.is("zhanara"));
        Assert.assertNull(tree.get(322));
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
