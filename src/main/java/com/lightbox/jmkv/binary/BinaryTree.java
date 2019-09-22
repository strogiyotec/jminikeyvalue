package com.lightbox.jmkv.binary;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Binary Tree.
 */
public final class BinaryTree implements Map<Integer, String> {

    /**
     * Root element.
     */
    private final TreeNode root;

    /**
     * Ctor.
     *
     * @param key   Key
     * @param value Value
     */
    public BinaryTree(final Integer key, final String value) {
        this.root = new TreeNode(key, value, null, null, null);
    }

    @Override
    public int size() {
        return BinaryTree.calculateSize(this.root, new AtomicInteger(0));
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(final Object key) {
        return false;
    }

    @Override
    public boolean containsValue(final Object value) {
        return false;
    }

    @Override
    public String get(final Object key) {
        return null;
    }

    @Override
    public String put(final Integer key, final String value) {
        BinaryTree.add(this.root, key, value);
        return value;
    }

    @Override
    public String remove(final Object key) {
        return null;
    }

    @Override
    public void putAll(final Map<? extends Integer, ? extends String> map) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<Integer> keySet() {
        return null;
    }

    @Override
    public Collection<String> values() {
        return null;
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return null;
    }

    /**
     * Add new node.
     *
     * @param root  Current node. Root by default
     * @param key   Key
     * @param value Value
     */
    private static void add(
            final TreeNode root,
            final Integer key,
            final String value
    ) {
        if (root.key < key) {
            if (root.right == null) {
                root.right = new TreeNode(key, value, root, null, null);
            } else {
                BinaryTree.add(root.right, key, value);
            }
        } else if (root.key >= key) {
            if (root.left == null) {
                root.left = new TreeNode(key, value, root, null, null);
            } else {
                BinaryTree.add(root.left, key, value);
            }
        }
    }

    /**
     * Calculate total size of binary tree.
     * @param current Current node
     * @param size    Current size , 0 by default
     * @return Total size of Binary Tree
     */
    private static int calculateSize(
            final TreeNode current,
            final AtomicInteger size
    ) {
        if (current == null) {
            return size.get();
        } else {
            if (current.isRoot()) {
                size.incrementAndGet();
            }
            if (current.left == null && current.right == null) {
                return 1;
            }
            if (current.left != null) {
                size.incrementAndGet();
            }
            if (current.right != null) {
                size.incrementAndGet();
            }
            BinaryTree.calculateSize(current.left, size);
            BinaryTree.calculateSize(current.right, size);

            return size.get();
        }
    }
}
