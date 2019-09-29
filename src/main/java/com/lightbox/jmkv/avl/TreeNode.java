package com.lightbox.jmkv.avl;

/**
 * Tree node fo AVL Tree.
 */
final class TreeNode {

    /**
     * Reference to root.
     */
    @SuppressWarnings("check:VisibilityModifier")
    TreeNode root;

    /**
     * Reference to left.
     */
    @SuppressWarnings("check:VisibilityModifier")
    TreeNode left;

    /**
     * Reference to right.
     */
    @SuppressWarnings("check:VisibilityModifier")
    TreeNode right;

    /**
     * Key.
     */
    @SuppressWarnings("check:VisibilityModifier")
    Integer key;

    /**
     * Value.
     */
    @SuppressWarnings("check:VisibilityModifier")
    String value;

    /**
     * Height.
     */
    @SuppressWarnings("check:VisibilityModifier")
    int height;

    /**
     * Ctor.
     */
    @SuppressWarnings({"JavadocMethod", "ParameterNumber"})
    TreeNode(
            final TreeNode root,
            final TreeNode left,
            final TreeNode right,
            final Integer key,
            final String value,
            final int height
    ) {
        this.root = root;
        this.left = left;
        this.right = right;
        this.key = key;
        this.value = value;
        this.height = height;
    }

    /**
     * Root ctor.
     */
    @SuppressWarnings("JavadocMethod")
    TreeNode(
            final Integer key,
            final String value,
            final int height
    ) {
        this.key = key;
        this.value = value;
        this.height = height;
        this.left = null;
        this.right = null;
    }
}
