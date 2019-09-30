package com.lightbox.jmkv.binary;

/**
 * Binary Tree representation.
 * Hidden from the world because only Binary Tree is supposed to use it
 */
final class TreeNode {

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
    TreeNode root;

    /**
     * Left child of node.
     */
    @SuppressWarnings("check:VisibilityModifier")
    TreeNode left;

    /**
     * Right child of node.
     */
    @SuppressWarnings("check:VisibilityModifier")
    TreeNode right;

    /**
     * Ctor for node with root and without children.
     *
     * @param root  Root
     * @param key   Key
     * @param value Value
     */
    TreeNode(
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
    TreeNode(
            final Integer key,
            final String value,
            final TreeNode root,
            final TreeNode left,
            final TreeNode right
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
    TreeNode(final Integer key, final String value) {
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
     * Check that node has exactly one child.
     *
     * @return True if node has only one child.
     */
    public boolean hasOneChild() {
        return (this.left != null && this.right == null)
                || (this.left == null && this.right != null);
    }

    /**
     * Find lowest key in left node recursively.
     *
     * @param node Current node
     * @return Min value of left node
     */
    static Integer minKey(final TreeNode node) {
        return node.left == null ? node.key : TreeNode.minKey(node.left);
    }
}
