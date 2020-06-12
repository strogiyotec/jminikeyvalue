package com.lightbox.jmkv;

public final class TraverseNode implements Traversable {
    @Override
    public void traverse(final TreeNode toDelete, final TreeNode replacing) {
        if (toDelete.isLeft()) {
            toDelete.parent().setLeft(replacing);
        } else {
            toDelete.parent().setRight(replacing);
        }
    }
}
