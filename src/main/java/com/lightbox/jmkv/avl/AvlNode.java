package com.lightbox.jmkv.avl;

import com.lightbox.jmkv.PlainTreeNode;
import com.lightbox.jmkv.TreeNode;
import com.lightbox.jmkv.binary.BinaryTreeNode;

/**
 * Tree node fo AVL Tree.
 */
final class AvlNode extends PlainTreeNode {

    /**
     * Height of newly created node.
     */
    private static final int EMPTY_HEIGHT = -1;

    /**
     * Height.
     */
    private int height;

    /**
     * Root of node.
     */
    private AvlNode root;

    /**
     * Left child of node.
     */
    private AvlNode left;

    /**
     * Right child of node.
     */
    private AvlNode right;

    /**
     * Ctor.
     */
    @SuppressWarnings({"JavadocMethod", "ParameterNumber"})
    AvlNode(
            final AvlNode root,
            final AvlNode left,
            final AvlNode right,
            final Integer key,
            final String value,
            final int height
    ) {
        super(key, value);
        this.height = height;
        this.root = root;
        this.left = left;
        this.right = right;
    }

    /**
     * Root ctor.
     */
    @SuppressWarnings("JavadocMethod")
    AvlNode(
            final Integer key,
            final String value,
            final int height
    ) {
        super(key, value);
        this.height = height;
    }

    /**
     * Ctor for node with parent and without children.
     */
    @SuppressWarnings("JavadocMethod")
    AvlNode(
            final Integer key,
            final String value,
            final int height,
            final AvlNode root
    ) {
        super(key, value);
        this.root = root;
        this.height = height;
    }

    /**
     * Refresh height value of current node.
     * For avl tree only
     */
    void refreshHeight() {
        this.height = 1 + Math.max(
                AvlNode.height(this.left()),
                AvlNode.height(this.right)
        );
    }

    /**
     * Calculate balance factor.
     * Difference between left height and right
     * For avl tree only
     *
     * @return Balance factor
     */
    int balanceFactor() {
        return AvlNode.height(this.left) - AvlNode.height(this.right);
    }

    /**
     * Find min left key starting from given node.
     *
     * @param node Current node
     * @return Min key of the last left key of given node
     */
    public static Integer minKey(final AvlNode node) {
        if (node.left == null) {
            return node.key();
        } else {
            return AvlNode.minKey(node.left);
        }
    }

    /**
     * Calc height of this node.
     *
     * @param node Node
     * @return Height of given node
     * -1 of node is null
     */
    private static int height(final AvlNode node) {
        if (node == null) {
            return AvlNode.EMPTY_HEIGHT;
        }
        return node.height();
    }

    public int height() {
        return 0;
    }

    @Override
    public void setLeft(final Integer key, final String value) {

    }

    @Override
    public void setLeft(final TreeNode treeNode) {

    }

    @Override
    public void setRight(final TreeNode right) {

    }

    @Override
    public void setParent(final TreeNode parent) {

    }

    @Override
    public void setRight(final Integer key, final String value) {

    }

    @Override
    public AvlNode left() {
        return this.left;
    }

    @Override
    public AvlNode right() {
        return this.right;
    }

    @Override
    public AvlNode parent() {
        return this.root;
    }

    public void setLeft(final AvlNode node) {
        this.left = node;
    }

    public void setRight(final AvlNode node) {

    }

    public void setRoot(final AvlNode node) {

    }
}
