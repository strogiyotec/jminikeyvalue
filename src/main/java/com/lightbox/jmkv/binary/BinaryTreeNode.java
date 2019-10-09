package com.lightbox.jmkv.binary;

/**
 * Binary Tree representation.
 * Hidden from the world because only Binary Tree is supposed to use it
 */
final class BinaryTreeNode {

    /**
     * Key of node.
     */
    @SuppressWarnings("check:VisibilityModifier")
    Integer key;

    /**
     * Value of node.
     */
    @SuppressWarnings("check:VisibilityModifier")
    String value;

    /**
     * Root of node.
     */
    @SuppressWarnings("check:VisibilityModifier")
    BinaryTreeNode root;

    /**
     * Left child of node.
     */
    @SuppressWarnings("check:VisibilityModifier")
    BinaryTreeNode left;

    /**
     * Right child of node.
     */
    @SuppressWarnings("check:VisibilityModifier")
    BinaryTreeNode right;

    /**
     * Ctor for node with root and without children.
     *
     * @param root  Root
     * @param key   Key
     * @param value Value
     */
    BinaryTreeNode(
            final BinaryTreeNode root,
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

    /**
     * Check that node is root.
     *
     * @return True of reference to root is not present
     */
    boolean isRoot() {
        return root == null;
    }

    /**
     * Check that node has at least one child.
     *
     * @return True if node has child
     */
    boolean hasChild() {
        return this.left != null || this.right != null;
    }

    /**
     * Check that node has left child.
     *
     * @return True if left is not null
     */
    boolean hasLeft() {
        return this.left != null;
    }

    /**
     * Check that node has right child.
     *
     * @return True if right is not null
     */
    boolean hasRight() {
        return this.right != null;
    }

    /**
     * Check that node has exactly one child.
     *
     * @return True if node has only one child.
     */
    boolean hasOneChild() {
        return (this.left != null && this.right == null)
                || (this.left == null && this.right != null);
    }

    /**
     * Node is left child of it's root.
     *
     * @return True if this is the left child
     */
    boolean isLeft() {
        final boolean isLeft;
        if (this.root != null) {
            isLeft = this.root.left == this;
        } else {
            isLeft = false;
        }
        return isLeft;
    }

    /**
     * Node is right child of it's root.
     *
     * @return True if this is the right child
     */
    boolean isRight() {
        final boolean isRight;
        if (this.root != null) {
            isRight = this.root.right == this;
        } else {
            isRight = false;
        }
        return isRight;
    }

    /**
     * Find lowest key in left node recursively.
     *
     * @param node Current node
     * @return Min value of left node
     */
    static Integer minKey(final BinaryTreeNode node) {
        return node.left == null ? node.key : BinaryTreeNode.minKey(node.left);
    }
}
