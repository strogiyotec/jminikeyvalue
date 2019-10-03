package com.lightbox.jmkv;

import com.lightbox.jmkv.binary.BinaryTree;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Test {@link BinaryTree} class.
 */
@SuppressWarnings("MagicNumber")
public final class BinaryTreeTest {

    /**
     * Test size of Binary tree.
     */
    @Test
    public void testSize() {
        final BinaryTree tree = BinaryTreeTest.getTree();
        Assert.assertThat(tree.size(), CoreMatchers.is(5));
    }

    /**
     * Test contains method of Binary Tree.
     */
    @Test
    public void testContains() {
        final BinaryTree tree = BinaryTreeTest.getTree();
        Assert.assertTrue(tree.containsKey(6));
        Assert.assertTrue(tree.containsKey(5));
        Assert.assertTrue(tree.containsKey(4));
        Assert.assertTrue(tree.containsKey(2));
        Assert.assertTrue(tree.containsKey(1));
        Assert.assertFalse(tree.containsKey(32));
    }

    /**
     * Tests contains value method of Binary Tree.
     */
    @Test
    public void testContainsValue() {
        final BinaryTree tree = BinaryTreeTest.getTree();
        Assert.assertTrue(tree.containsValue("almas"));
        Assert.assertTrue(tree.containsValue("hello"));
        Assert.assertTrue(tree.containsValue("wewe"));
        Assert.assertTrue(tree.containsValue("dsd"));
        Assert.assertTrue(tree.containsValue("alloha"));
        Assert.assertFalse(tree.containsValue("hello my friend"));

    }

    /**
     * Test remove of left node of Binary Tree.
     */
    @Test
    public void testRemoveKeyFromLeftTree() {
        final BinaryTree tree = BinaryTreeTest.getTree();
        tree.put(10, "ten");
        tree.put(15, "fifteen");
        tree.put(11, "eleven");
        tree.put(14, "fourteen");
        tree.put(9, "nine");
        Assert.assertThat(tree.size(), CoreMatchers.is(10));
        Assert.assertThat(tree.remove(2), CoreMatchers.is("dsd"));
        Assert.assertThat(tree.remove(10), CoreMatchers.is("ten"));
        Assert.assertThat(tree.size(), CoreMatchers.is(8));
    }

    /**
     * Test remove of right node of Binary Tree.
     */
    @Test
    public void testRemoveFromRightTree() {
        final BinaryTree binaryTree = new BinaryTree(10, "");
        binaryTree.put(15, "To delete");
        binaryTree.put(20, "");
        Assert.assertThat(binaryTree.remove(15), CoreMatchers.is("To delete"));
        Assert.assertThat(binaryTree.size(), CoreMatchers.is(2));
    }

    /**
     * Test fail during root deletion.
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveRoot() {
        final BinaryTree tree = BinaryTreeTest.getTree();
        tree.remove(5);
    }

    /**
     * Test get method of binary tree.
     */
    @Test
    public void testGet() {
        final BinaryTree tree = BinaryTreeTest.getTree();
        Assert.assertThat(tree.get(5), CoreMatchers.is("almas"));
        Assert.assertThat(tree.get(6), CoreMatchers.is("hello"));
        Assert.assertThat(tree.get(4), CoreMatchers.is("wewe"));
        Assert.assertThat(tree.get(2), CoreMatchers.is("dsd"));
        Assert.assertThat(tree.get(1), CoreMatchers.is("alloha"));
        Assert.assertNull(tree.get(322));
    }

    /**
     * Test put method of binary tree.
     */
    @Test
    public void testPut() {
        final BinaryTree binaryTree = new BinaryTree(5, "almas");
        binaryTree.put(6, "almat");
        binaryTree.put(7, "abzal");
        binaryTree.put(4, "zhanara");
        binaryTree.put(3, "erik");
        Assert.assertThat(binaryTree.size(), CoreMatchers.is(5));
        Assert.assertThat(binaryTree.get(4), CoreMatchers.is("zhanara"));
    }

    /**
     * Test pul all method of binary tree.
     */
    @Test
    public void testPutAll() {
        final BinaryTree tree = new BinaryTree(22, "almas");
        final Map<Integer, String> map = new HashMap<>(2, 1.0F);
        map.put(100, "almat");
        map.put(200, "abzal");
        tree.putAll(map);
        Assert.assertThat(tree.size(), CoreMatchers.is(3));
        Assert.assertThat(tree.get(22), CoreMatchers.is("almas"));
        Assert.assertThat(tree.get(100), CoreMatchers.is("almat"));
        Assert.assertThat(tree.get(200), CoreMatchers.is("abzal"));
    }

    /**
     * Test key set method of binary tree.
     */
    @Test
    public void testCollectKeys() {
        final BinaryTree tree = BinaryTreeTest.getTree();
        final Set<Integer> keySet = tree.keySet();
        final Set<Integer> preCalculatedKeys = new HashSet<>(5);
        preCalculatedKeys.add(5);
        preCalculatedKeys.add(6);
        preCalculatedKeys.add(4);
        preCalculatedKeys.add(2);
        preCalculatedKeys.add(1);
        Assert.assertEquals(keySet, preCalculatedKeys);
    }

    /**
     * Test values methof of binary tree.
     */
    @Test
    public void testCollectValues() {
        final BinaryTree tree = BinaryTreeTest.getTree();
        Assert.assertThat(
                tree.values(),
                Matchers.contains("almas", "wewe", "dsd", "alloha", "hello")
        );
    }

    /**
     * Test entry set method of binary tree.
     */
    @Test
    public void testEntries() {
        final BinaryTree tree = BinaryTreeTest.getTree();
        final Set<Map.Entry<Integer, String>> entries = tree.entrySet();
        Assert.assertTrue(entries.contains(new ImmutableEntry<>(5, "almas")));
        Assert.assertTrue(entries.contains(new ImmutableEntry<>(6, "hello")));
        Assert.assertTrue(entries.contains(new ImmutableEntry<>(4, "wewe")));
        Assert.assertTrue(entries.contains(new ImmutableEntry<>(2, "dsd")));
        Assert.assertTrue(entries.contains(new ImmutableEntry<>(1, "alloha")));
        Assert.assertFalse(
                entries.contains(
                        new ImmutableEntry<>(10, "I'M NOT PRESENT")
                )
        );
    }

    /**
     * Create binary tree for test purposes.
     *
     * @return Binary tree
     */
    private static BinaryTree getTree() {
        final BinaryTree binaryTree = new BinaryTree(5, "almas");
        binaryTree.put(6, "hello");
        binaryTree.put(4, "wewe");
        binaryTree.put(2, "dsd");
        binaryTree.put(1, "alloha");
        return binaryTree;
    }
}

