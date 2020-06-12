package com.lightbox.jmkv;

public abstract class TreeNodeEnvelope implements TreeNode {

    private final TreeNode origin;

    public TreeNodeEnvelope(final TreeNode origin) {
        this.origin = origin;
    }

    @Override
    public final boolean isRoot() {
        return this.origin.isRoot();
    }

    @Override
    public final boolean isLeft() {
        return this.origin.isLeft();
    }

    @Override
    public final boolean isRight() {
        return this.origin.isRight();
    }

    @Override
    public final Integer key() {
        return this.origin.key();
    }

    @Override
    public final Integer minKey() {
        return this.origin.minKey();
    }

    @Override
    public final String value() {
        return this.origin.value();
    }

    @Override
    public final void setKey(final Integer key) {
        this.origin.setKey(key);
    }

    @Override
    public final void setValue(final String value) {
        this.origin.setValue(value);
    }

    @Override
    public final void setLeft(final Integer key, final String value) {
        this.origin.setLeft(key, value);
    }

    @Override
    public final void setRight(final Integer key, final String value) {
        this.origin.setRight(key, value);
    }

    @Override
    public final void setLeft(final TreeNode left) {
        this.origin.setLeft(left);
    }

    @Override
    public final void setRight(final TreeNode right) {
        this.origin.setRight(right);
    }

    @Override
    public final void setParent(final TreeNode parent) {
        this.origin.setParent(parent);
    }

    @Override
    public final TreeNode left() {
        return this.origin.left();
    }

    @Override
    public final TreeNode right() {
        return this.origin.right();
    }

    @Override
    public final TreeNode parent() {
        return this.origin.parent();
    }
}
