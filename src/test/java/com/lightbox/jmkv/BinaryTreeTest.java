package com.lightbox.jmkv;

import com.lightbox.jmkv.binary.BinaryTree;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

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

