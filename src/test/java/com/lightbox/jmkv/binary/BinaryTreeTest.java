package com.lightbox.jmkv.binary;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertThat(tree.size(), CoreMatchers.is(8));
    }

    /**
     * Test contains method of Binary Tree.
     */
    @Test
    public void testContains() {
        final BinaryTree tree = BinaryTreeTest.getTree();
        Assert.assertTrue(tree.exists(5));
        //In the left
        Assert.assertTrue(tree.exists(4));
        Assert.assertTrue(tree.exists(2));
        Assert.assertTrue(tree.exists(1));
        Assert.assertTrue(tree.exists(3));
        //In the right
        Assert.assertTrue(tree.exists(10));
        Assert.assertTrue(tree.exists(7));
        Assert.assertTrue(tree.exists(11));
    }

    /**
     * Test delete of left node of Binary Tree.
     */
    @Test
    public void testRemoveKeyFromLeftTree() {
        final BinaryTree tree = BinaryTreeTest.getTree();
        tree.put(10, "ten");
        tree.put(15, "fifteen");
        tree.put(11, "eleven");
        tree.put(14, "fourteen");
        tree.put(9, "nine");
        Assert.assertThat(tree.delete(2), CoreMatchers.is("dsd"));
        Assert.assertThat(tree.delete(10), CoreMatchers.is("ten"));
        Assert.assertThat(tree.size(), CoreMatchers.is(8));
    }

    /**
     * Test delete of right node of Binary Tree.
     */
    @Test
    public void testRemoveFromRightTree() {
        final BinaryTree binaryTree = new BinaryTree();
        binaryTree.put(15, "To delete");
        binaryTree.put(20, "");
        Assert.assertThat(binaryTree.delete(15), CoreMatchers.is("To delete"));
        Assert.assertThat(binaryTree.size(), CoreMatchers.is(2));
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
        final BinaryTree binaryTree = new BinaryTree();
        binaryTree.put(6, "almat");
        binaryTree.put(7, "abzal");
        binaryTree.put(4, "zhanara");
        binaryTree.put(3, "erik");
        Assert.assertThat(binaryTree.size(), CoreMatchers.is(5));
        Assert.assertThat(binaryTree.get(4), CoreMatchers.is("zhanara"));
    }

    /**
     * Create binary tree for test purposes.
     *
     * @return Binary tree
     */
    private static BinaryTree getTree() {
        final BinaryTree binaryTree = new BinaryTree();
        binaryTree.put(5, "root");
        binaryTree.put(4, "4");
        binaryTree.put(2, "2");
        binaryTree.put(3, "3");
        binaryTree.put(1, "1");
        binaryTree.put(10, "10");
        binaryTree.put(7, "7");
        return binaryTree;
    }
}

