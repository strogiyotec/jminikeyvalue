package com.lightbox.jmkv.binary;

import com.lightbox.jmkv.*;

/**
 * Binary Tree.
 */
public final class BinaryTree extends PlainTree implements Traversable {

    /**
     * Root element.
     */
    private TreeNode root;

    /**
     * Size.
     */
    private int size;

    /**
     * Traverse to use during delete.
     */
    private final Traversable traversable;

    /**
     * Ctor.
     */
    public BinaryTree() {
        this(
                null,
                0,
                new TraverseNode()
        );
    }

    BinaryTree(final BinaryTreeNode root, Traversable traversable) {
        this(root, 1, traversable);
    }

    private BinaryTree(final BinaryTreeNode root, final int size, final Traversable traversable) {
        this.root = root;
        this.size = size;
        this.traversable = traversable;
    }

    @Override
    protected TreeNode getRoot() {
        return this.root;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public String put(final Integer key, final String value) {
        if (this.size == 0) {
            this.root = new BinaryTreeNode(key, value);
        } else {
            super.put(key, value);
        }
        this.size++;
        return value;
    }

    @Override
    public final String delete(final Integer key) {
        if (this.size == 0) {
            return null;
        }
        final TreeNode toRemove = this.find(key);
        if (toRemove != null) {
            this.remove(toRemove);
            this.size--;
            return toRemove.value();
        } else {
            return null;
        }
    }

    @Override
    public boolean clear() {
        this.size = 0;
        this.root = null;
        return true;
    }

    @Override
    public void traverse(final TreeNode toDelete, final TreeNode replacing) {
        if (toDelete.isRoot()) {
            this.root = replacing;
        } else {
            this.traversable.traverse(toDelete, replacing);
        }
    }

    /**
     * Remove node with given key.
     *
     * @param toDelete Node to delete
     */
    @SuppressWarnings("ReturnCount")
    private void remove(final TreeNode toDelete) {
        if (toDelete.left() == null) {
            this.traverse(toDelete, toDelete.right());

        } else if (toDelete.right() == null) {
            this.traverse(toDelete, toDelete.left());
        } else {
            final TreeNode successor = new Successor(toDelete);
            if (successor.parent() != toDelete) {
                this.traverse(successor, successor.right());
                successor.setRight(toDelete.right());
            }
            this.traverse(toDelete, successor);
            successor.setLeft(toDelete.left());
        }
    }
}
