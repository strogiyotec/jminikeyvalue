package com.lightbox.jmkv.binary;

import com.lightbox.jmkv.ImmutableEntry;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Binary Tree.
 * Root have to be present
 * If clean method was called then this tree is not valid anymore
 */
public final class BinaryTree implements Map<Integer, String> {

    /**
     * Default Tree size for Set and List used in.
     * keySet(),values(),entrySet()
     */
    private static final int DEFAULT_TREE_SIZE = 16;

    /**
     * Root element.
     */
    private BinaryTreeNode root;

    /**
     * Ctor.
     *
     * @param key   Key
     * @param value Value
     */
    public BinaryTree(final Integer key, final String value) {
        this.root = new BinaryTreeNode(key, value);
    }

    @Override
    public int size() {
        //atomic integer in order to store size
        return BinaryTree.calculateSize(this.root, new AtomicInteger(0));
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public boolean containsKey(final Object key) {
        return BinaryTree.valueByKey(this.root, (Integer) key) != null;
    }

    @Override
    public boolean containsValue(final Object value) {
        return BinaryTree.containsValue(this.root, (String) value);
    }

    @Override
    public String get(final Object key) {
        return BinaryTree.valueByKey(this.root, (Integer) key);
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
        final Set<Integer> keySet = new HashSet<>(DEFAULT_TREE_SIZE);
        BinaryTree.collectKeys(this.root, keySet);
        return keySet;
    }

    @Override
    public Collection<String> values() {
        final List<String> values = new ArrayList<>(DEFAULT_TREE_SIZE);
        BinaryTree.collectValues(this.root, values);
        return values;
    }

    @Override
    @SuppressWarnings("LineLength")
    public Set<Entry<Integer, String>> entrySet() {
        final Set<Entry<Integer, String>> entries = new HashSet<>(DEFAULT_TREE_SIZE);
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
            final BinaryTreeNode current,
            final Set<Entry<Integer, String>> entries
    ) {
        if (current == null) {
            return;
        }
        entries.add(new ImmutableEntry<>(current.key, current.value));
        if (current.hasLeft()) {
            BinaryTree.collectEntries(current.left, entries);
        }
        if (current.hasRight()) {
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
            final BinaryTreeNode current,
            final List<String> values
    ) {
        if (current == null) {
            return;
        }
        values.add(current.value);
        if (current.hasLeft()) {
            BinaryTree.collectValues(current.left, values);
        }
        if (current.hasRight()) {
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
            final BinaryTreeNode current,
            final Set<Integer> keySet
    ) {
        if (current == null) {
            return;
        }
        keySet.add(current.key);
        if (current.hasLeft()) {
            BinaryTree.collectKeys(current.left, keySet);
        }
        if (current.hasRight()) {
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
    private String remove(final BinaryTreeNode current, final Integer key) {
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
                    return BinaryTree.deleteNodeWithoutChildren(current);
                    //node has exactly one child
                } else if (current.hasOneChild()) {
                    return BinaryTree.deleteNodeWithOneChild(current);
                } else {
                    //set lowest key to deleted node
                    // and delete original node with this lowest key
                    current.key = BinaryTreeNode.minKey(current.right);
                    this.remove(current.right, current.key);
                    return current.value;
                }
            }
        } else if (current.hasLeft() && current.key > key) {
            return this.remove(current.left, key);
        } else if (current.hasRight() && current.key < key) {
            return this.remove(current.right, key);
        }
        return null;
    }

    /**
     * Delete reference to given root from the child.
     *
     * @param node Node to be deleted
     * @return Value of current root
     */
    private static String deleteNodeWithOneChild(final BinaryTreeNode node) {
        final String value = node.value;
        if (node.isLeft()) {
            if (node.hasLeft()) {
                node.left.root = node.root;
                node.root.left = node.left;
            }
            if (node.hasRight()) {
                node.right.root = node.root;
                node.root.left = node.right;
            }
        }
        if (node.isRight()) {
            if (node.hasLeft()) {
                node.left.root = node.root;
                node.root.right = node.left;
            }
            if (node.hasRight()) {
                node.right.root = node.root;
                node.root.right = node.right;
            }
        }
        return value;
    }

    /**
     * Delete reference to given node from it's root.
     *
     * @param current Node to be deleted
     * @return Value of deleted node
     */
    private static String deleteNodeWithoutChildren(
            final BinaryTreeNode current
    ) {
        final BinaryTreeNode parent = current.root;
        final String value = current.value;
        if (parent.left == current) {
            parent.left = null;
        } else if (parent.right == current) {
            parent.right = null;
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
            final BinaryTreeNode root,
            final Integer key,
            final String value
    ) {
        if (root.key < key) {
            if (!root.hasRight()) {
                root.right = new BinaryTreeNode(root, key, value);
            } else {
                BinaryTree.add(root.right, key, value);
            }
        } else {
            if (!root.hasLeft()) {
                root.left = new BinaryTreeNode(root, key, value);
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
            final BinaryTreeNode current,
            final AtomicInteger size
    ) {
        if (current == null) {
            return size.get();
        } else {
            if (current.isRoot()) {
                size.incrementAndGet();
            }
            if (!current.hasChild()) {
                return 1;
            }
            if (current.hasLeft()) {
                size.incrementAndGet();
            }
            if (current.hasRight()) {
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
    private static String valueByKey(
            final BinaryTreeNode currentNode,
            final Integer key
    ) {
        if (currentNode == null) {
            return null;
        }
        if (currentNode.key.equals(key)) {
            return currentNode.value;
        }
        if (currentNode.key > key) {
            return BinaryTree.valueByKey(currentNode.left, key);
        }
        return BinaryTree.valueByKey(currentNode.right, key);
    }

    /**
     * Check that Tree valueByKey given key.
     *
     * @param currentNode Current node, root by default
     * @param value       Value
     * @return True if key is present in tree
     */
    @SuppressWarnings("ReturnCount")
    private static boolean containsValue(
            final BinaryTreeNode currentNode,
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
