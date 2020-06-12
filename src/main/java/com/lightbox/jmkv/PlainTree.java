package com.lightbox.jmkv;

import java.util.Optional;

public abstract class PlainTree implements Cache {
    protected abstract TreeNode getRoot();

    @Override
    public final String get(final Integer key) {
        return Optional.ofNullable(this.find(key)).map(TreeNode::value).orElse(null);
    }

    protected final TreeNode find(final Integer key) {
        TreeNode node = this.getRoot();
        while (node != null) {
            if (node.key().equals(key)) {
                return node;
            }
            if (node.key() < key) {
                node.right();
            } else {
                node = node.left();
            }
        }
        return null;
    }

    @Override
    public final boolean exists(final Integer key) {
        return this.get(key) != null;
    }

    @Override
    public String put(final Integer key, final String value) {
        TreeNode node = this.getRoot();
        while (true) {
            //Add to left
            if (node.key() > key) {
                if (node.left() == null) {
                    node.setLeft(key, value);
                    return value;
                } else {
                    node = node.left();
                }
            } else {
                //Add to right
                if (node.right() == null) {
                    node.setRight(key, value);
                    return value;
                } else {
                    node = node.right();
                }
            }
        }
    }
}
