package com.lightbox.jmkv.binary;

import com.lightbox.jmkv.ImmutableEntry;

import java.util.*;
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
        map.forEach(this::put);
    }

    @Override
    public void clear() {
        this.root = null;
    }

    @Override
    public Set<Integer> keySet() {
        final Set<Integer> keySet = new HashSet<>(16);
        BinaryTree.collectKeys(this.root, keySet);
        return keySet;
    }

    @Override
    public Collection<String> values() {
        final List<String> values = new ArrayList<>(16);
        BinaryTree.collectValues(this.root, values);
        return values;
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        final Set<Entry<Integer, String>> entries = new HashSet<>(16);
        BinaryTree.collectEntries(this.root, entries);
        return entries;
    }

    /**
     * Collect entries of nodes starting from current.
     *
     * @param current Current node, root by default
     * @param entries Set where entries are stored
     */
    private static void collectEntries(
            final TreeNode current,
            final Set<Entry<Integer, String>> entries
    ) {
        if (current == null) {
            return;
        }
        entries.add(new ImmutableEntry<>(current.key, current.value));
        if (current.left != null) {
            BinaryTree.collectEntries(current.left, entries);
        }
        if (current.right != null) {
            BinaryTree.collectEntries(current.right, entries);
        }
    }

    /**
     * Collect values from nodes starting from current.
     * Store these values in given array
     *
     * @param current Current node, root be default
     * @param values  List to store values
     */
    private static void collectValues(
            final TreeNode current,
            final List<String> values
    ) {
        if (current == null) {
            return;
        }
        values.add(current.value);
        if (current.left != null) {
            BinaryTree.collectValues(current.left, values);
        }
        if (current.right != null) {
            BinaryTree.collectValues(current.right, values);
        }
    }

    /**
     * Collect keys from nodes starting from current.
     * Store these keys in given set
     *
     * @param current Current node, root by default
     * @param keySet  Set to store keys
     */
    private static void collectKeys(
            final TreeNode current,
            final Set<Integer> keySet
    ) {
        if (current == null) {
            return;
        }
        keySet.add(current.key);
        if (current.left != null) {
            BinaryTree.collectKeys(current.left, keySet);
        }
        if (current.right != null) {
            BinaryTree.collectKeys(current.right, keySet);
        }
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
                    //node has exactly one child
                } else if (current.hasOneChild()) {
                    return BinaryTree.deleteRootFromNode(current);
                } else {
                    //set lowest key to deleted node
                    // and delete original node with this lowest key
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
