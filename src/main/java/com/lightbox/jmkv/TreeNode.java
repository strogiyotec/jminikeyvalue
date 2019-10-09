package com.lightbox.jmkv;

/**
 * Node for trees.
 */
public interface TreeNode {

    /**
     * Check that node is root.
     *
     * @return True if node doesn't have root
     */
    boolean isRoot();

    /**
     * Node has left child.
     *
     * @return True if node has left child
     */
    boolean hasLeft();

    /**
     * Node has right child.
     *
     * @return True if node has right child
     */
    boolean hasRight();

    /**
     * Node is left child of root.
     *
     * @return True if this node is left
     */
    boolean isLeft();

    /**
     * Node is right child of root.
     *
     * @return True if this node is right
     */
    boolean isRight();

    /**
     * Has at least one child.
     *
     * @return True if node has one child.
     */
    boolean hasChild();

    /**
     * Has exactly one child.
     *
     * @return True of node has only 1 child
     */
    boolean hasOneChild();
}
