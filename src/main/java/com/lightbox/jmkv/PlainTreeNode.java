package com.lightbox.jmkv;

public abstract class PlainTreeNode implements TreeNode {

    private Integer key;
    private String value;

    public PlainTreeNode(final Integer key, final String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public final boolean isRoot() {
        return this.parent() == null;
    }

    @Override
    public final boolean isLeft() {
        return this.parent() != null && this.parent().left() == this;
    }

    @Override
    public final boolean isRight() {
        return this.parent() != null && this.parent().right() == this;
    }

    @Override
    public final Integer key() {
        return this.key;
    }

    @Override
    public final Integer minKey() {
        return this.left() == null ?
                this.key :
                this.left().minKey();
    }

    @Override
    public final String value() {
        return this.value;
    }

    @Override
    public final void setKey(final Integer key) {
        this.key = key;
    }

    @Override
    public final void setValue(final String value) {
        this.value = value;
    }

}

