package com.lightbox.jmkv;

import com.lightbox.jmkv.binary.BinaryTree;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Test binary tree.
 */
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
     * Test remove method of Binary Tree.
     */
    @Test
    public void testRemoveKey() {
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

