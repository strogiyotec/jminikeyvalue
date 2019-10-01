package com.lightbox.jmkv.avl;

/**
 * Tree node fo AVL Tree.
 */
final class TreeNode {

    /**
     * Height of newly created node.
     */
    static final int EMPTY_HEIGHT = 0;

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
        this.root = null;
    }

    /**
     * Ctor for node with root and without children.
     */
    @SuppressWarnings("JavadocMethod")
    TreeNode(
            final Integer key,
            final String value,
            final int height,
            final TreeNode root
    ) {
        this.key = key;
        this.value = value;
        this.height = height;
        this.root = root;
        this.left = null;
        this.right = null;
    }

    /**
     * Refresh height value of current node.
     */
    void refreshHeight() {
        this.height = 1 + Math.max(
                TreeNode.height(this.left),
                TreeNode.height(this.right)
        );
    }

    /**
     * Calculate balance factor.
     * Difference between left height and right
     *
     * @return Balance factor
     */
    int balanceFactor() {
        return TreeNode.height(this.left) - TreeNode.height(this.right);
    }

    /**
     * Node has left child.
     *
     * @return True if node has left child
     */
    boolean hasLeft() {
        return this.left != null;
    }

    /**
     * Node has right child.
     *
     * @return True if node has right child
     */
    boolean hasRight() {
        return this.right != null;
    }

    /**
     * Node is left child of root.
     *
     * @return True if this node is left
     */
    boolean isLeft() {
        return this.root != null && this.root.key > this.key;
    }

    /**
     * Node is right child of root.
     *
     * @return True if this node is right
     */
    boolean isRight() {
        return this.root != null && this.root.key < this.key;
    }

    /**
     * Calc height of this node.
     *
     * @param node Node
     * @return Height of given node
     * -1 of node is null
     */
    private static int height(final TreeNode node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }
}
