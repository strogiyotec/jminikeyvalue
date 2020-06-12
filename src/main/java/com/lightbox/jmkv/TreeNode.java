package com.lightbox.jmkv;

/**
 * Node for trees.
 */
public interface TreeNode {

    /**
     * Check that node is parent.
     *
     * @return True if node doesn't have parent
     */
    boolean isRoot();

    /**
     * Node is left child of parent.
     *
     * @return True if this node is left
     */
    boolean isLeft();

    /**
     * Node is right child of parent.
     *
     * @return True if this node is right
     */
    boolean isRight();

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

    void setLeft(Integer key, String value);

    void setRight(Integer key, String value);

    void setLeft(TreeNode left);

    void setRight(TreeNode right);

    void setParent(TreeNode parent);

    TreeNode left();

    TreeNode right();

    TreeNode parent();
}