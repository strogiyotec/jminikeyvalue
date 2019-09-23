package com.lightbox.jmkv.binary;

/**
 * Binary Tree representation.
 */
final class TreeNode {

    /**
     * Key of node.
     */
    @SuppressWarnings("check:VisibilityModifier")
    final Integer key;

    /**
     * Value of node.
     */
    @SuppressWarnings("check:VisibilityModifier")
    final String value;

    /**
     * Root of node.
     */
    @SuppressWarnings("check:VisibilityModifier")
    final TreeNode root;

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
    }

    /**
     * Check that node is root.
     *
     * @return True of reference to root is not present
     */
    boolean isRoot() {
        return root == null;
    }
}
