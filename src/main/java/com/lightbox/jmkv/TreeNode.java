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

    int children();

    /**
     * Left child.
     *
     * @return Left child
     */
    TreeNode left();

    /**
     * Right child
     *
     * @return Right child
     */
    TreeNode right();

    /**
     * Root.
     *
     * @return Root
     */
    TreeNode root();

    /**
     * Node key.
     *
     * @return Key
     */
    Integer key();

    /**
     * Min key.
     *
     * @return Min key
     */
    Integer minKey();

    /**
     * Node value.
     *
     * @return Value
     */
    String value();

    /**
     * Set key.
     *
     * @param key New key
     */
    void setKey(Integer key);

    /**
     * Set value.
     *
     * @param value New value
     */
    void setValue(String value);

    /**
     * Set left child.
     *
     * @param node New left
     */
    void setLeft(TreeNode node);

    /**
     * Set right child.
     *
     * @param node New right
     */
    void setRight(TreeNode node);

    /**
     * Set new root.
     *
     * @param node New root
     */
    void setRoot(TreeNode node);
}