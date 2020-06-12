package com.lightbox.jmkv;

import com.lightbox.jmkv.binary.BinaryTreeNode;

/**
 * Perform rotations on nodes.
 */
public interface Rotatable {
    /**
     * Rotate to left.
     *
     * @param node Node to rotate
     */
    void toLeft(BinaryTreeNode node);

    /**
     * Rotate to right.
     *
     * @param node Node to rotate
     */
    void toRight(BinaryTreeNode node);
}
