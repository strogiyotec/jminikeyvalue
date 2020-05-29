package com.lightbox.jmkv.binary;

import com.lightbox.jmkv.TreeNode;

/**
 * Binary Tree representation.
 * Hidden from the world because only Binary Tree is supposed to use it
 */
final class BinaryTreeNode implements TreeNode {

    /**
     * Key of node.
     */
    private Integer key;

    /**
     * Value of node.
     */
    private String value;

    /**
     * Root of node.
     */
    private TreeNode root;

    /**
     * Left child of node.
     */
    private TreeNode left;

    /**
     * Right child of node.
     */
    private TreeNode right;

    /**
     * Ctor for node with root and without children.
     *
     * @param root  Root
     * @param key   Key
     * @param value Value
     */
    BinaryTreeNode(
            final TreeNode root,
            final Integer key,
            final String value
    ) {
        this.root = root;
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    /**
     * Ctor.
     *
     * @param key   Key
     * @param value Value
     * @param root  Root
     * @param left  Left
     * @param right Right
     */
    BinaryTreeNode(
            final Integer key,
            final String value,
            final BinaryTreeNode root,
            final BinaryTreeNode left,
            final BinaryTreeNode right
    ) {
        this.key = key;
        this.value = value;
        this.root = root;
        this.left = left;
        this.right = right;
    }

    /**
     * Ctor for Root without reference to root.
     *
     * @param key   Key
     * @param value Value
     */
    BinaryTreeNode(final Integer key, final String value) {
        this.key = key;
        this.value = value;
        this.root = null;
        this.left = null;
        this.right = null;
    }

    @Override
    public boolean isRoot() {
        return this.root == null;
    }


    @Override
    public Integer key() {
        return this.key;
    }

    @Override
    public boolean isLeft() {
        return this.root != null && this.root.left() == this;
    }

    @Override
    public boolean isRight() {
        return this.root != null && this.root.right() == this;
    }

    @Override
    public int children() {
        if (this.left == null && this.right == null) {
            return 0;
        } else if (this.left != null && this.right != null) {
            return 2;
        }
        return 1;
    }

    @Override
    public TreeNode left() {
        return this.left;
    }

    @Override
    public TreeNode right() {
        return this.right;
    }

    @Override
    public TreeNode root() {
        return this.root;
    }

    @Override
    public Integer minKey() {
        return this.left == null ?
                this.key :
                this.left.minKey();
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public void setKey(final Integer key) {
        this.key = key;
    }

    @Override
    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public void setLeft(final TreeNode node) {
        this.left = node;
    }

    @Override
    public void setRight(final TreeNode node) {
        this.right = node;
    }

    @Override
    public void setRoot(final TreeNode node) {
        this.root = node;
    }

}
