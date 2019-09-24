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
    private TreeNode root;

    /**
     * Ctor.
     *
     * @param key   Key
     * @param value Value
     */
    public BinaryTree(final Integer key, final String value) {
        this.root = new TreeNode(key, value);
    }

    @Override
    public int size() {
        return BinaryTree.calculateSize(this.root, new AtomicInteger(0));
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public boolean containsKey(final Object key) {
        return BinaryTree.getValueByKey(this.root, (Integer) key) != null;
    }

    @Override
    public boolean containsValue(final Object value) {
        return BinaryTree.containsValue(this.root, (String) value);
    }

    @Override
    public String get(final Object key) {
        return BinaryTree.getValueByKey(this.root, (Integer) key);
    }

    @Override
    public String put(final Integer key, final String value) {
        BinaryTree.add(this.root, key, value);
        return value;
    }

    @Override
    public String remove(final Object key) {
        return this.remove(this.root, (Integer) key);
    }

    @Override
    public void putAll(final Map<? extends Integer, ? extends String> map) {

    }

    @Override
    public void clear() {
        this.root = null;
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
     * Remove node with given key.
     *
     * @param current Current node, root by default
     * @param key     Key
     * @return Value of deleted node or null if node
     * with given key is not present
     * @throws IllegalStateException if root was deleted
     */
    @SuppressWarnings("ReturnCount")
    private String remove(final TreeNode current, final Integer key) {
        if (current == null) {
            return null;
        } else if (current.key.equals(key)) {
            if (current.isRoot()) {
                this.root = null;
                throw new IllegalStateException(
                        "Root of Tree was deleted. Map is not valid anymore"
                );
            } else {
                //node doesn't have children
                if (!current.hasChild()) {
                    return BinaryTree.deleteNodeFromRoot(current);
                    //node has both children
                } else if (current.hasOneChild()) {
                    return BinaryTree.deleteRootFromNode(current);
                } else {
                    current.key = TreeNode.minKey(current.right);
                    this.remove(current.right, current.key);
                    return current.value;
                }
            }
        } else if (current.left != null && current.key > key) {
            return this.remove(current.left, key);
        } else if (current.right != null && current.key < key) {
            return this.remove(current.right, key);
        }
        return null;
    }

    /**
     * Delete reference to given root from the child.
     *
     * @param currentRoot Current root node
     * @return Value of current root
     */
    private static String deleteRootFromNode(final TreeNode currentRoot) {
        final String value = currentRoot.value;
        if (currentRoot.left != null) {
            currentRoot.left.root = currentRoot.root;
            currentRoot.root.left = currentRoot.left;
        }
        if (currentRoot.right != null) {
            currentRoot.right.root = currentRoot.root;
            currentRoot.root.left = currentRoot.right;
        }
        return value;
    }

    /**
     * Delete reference to given node from it's root.
     *
     * @param current Current node
     * @return Value of deleted node
     */
    private static String deleteNodeFromRoot(final TreeNode current) {
        final TreeNode currentRoot = current.root;
        final String value = current.value;
        if (currentRoot.left == current) {
            currentRoot.left = null;
        } else if (currentRoot.right == current) {
            currentRoot.right = null;
        }
        return value;
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
     *
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

    /**
     * Return value of node with given key.
     *
     * @param currentNode Current node, root by default
     * @param key         Key
     * @return Value by key or null if no node with given key
     */
    @SuppressWarnings("ReturnCount")
    private static String getValueByKey(
            final TreeNode currentNode,
            final Integer key
    ) {
        if (currentNode == null) {
            return null;
        }
        if (currentNode.key.equals(key)) {
            return currentNode.value;
        }
        if (currentNode.key > key) {
            return BinaryTree.getValueByKey(currentNode.left, key);
        }
        if (currentNode.key <= key) {
            return BinaryTree.getValueByKey(currentNode.right, key);
        }
        return null;
    }

    /**
     * Check that Tree getValueByKey given key.
     *
     * @param currentNode Current node, root by default
     * @param value       Value
     * @return True if key is present in tree
     */
    @SuppressWarnings("ReturnCount")
    private static boolean containsValue(
            final TreeNode currentNode,
            final String value
    ) {
        if (currentNode == null) {
            return false;
        }
        if (currentNode.value.equals(value)) {
            return true;
        }
        return BinaryTree.containsValue(currentNode.left, value)
                || BinaryTree.containsValue(currentNode.right, value);
    }
}
