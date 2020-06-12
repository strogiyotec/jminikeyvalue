package com.lightbox.jmkv;

import com.lightbox.jmkv.binary.BinaryTreeNode;

public final class TreeRotation implements Rotatable {

    @Override
    public void toLeft(final BinaryTreeNode node) {
        final BinaryTreeNode right = node.right();
        this.updateParentReference(node, right);
        if (right.left() != null) {
            final BinaryTreeNode left = right.left();
            node.setRight(left);
        }
        right.setLeft(node);

    }

    @Override
    public void toRight(final BinaryTreeNode node) {
        final BinaryTreeNode left = node.left();
        this.updateParentReference(node, left);
        if (left.right() != null) {
            final BinaryTreeNode right = left.right();
            node.setLeft(right);
        }
        left.setRight(left);
    }

    private void updateParentReference(final BinaryTreeNode node, final BinaryTreeNode child) {
        if (!node.isRoot()) {
            final BinaryTreeNode parent = node.parent();
            if (node.isRight()) {
                parent.setRight(child);
            } else {
                parent.setLeft(child);
            }
        }
    }

}
