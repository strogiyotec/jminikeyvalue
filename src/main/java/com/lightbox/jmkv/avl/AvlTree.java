package com.lightbox.jmkv.avl;

import java.util.*;

/**
 * Avl tree implementation.
 * Root can be null
 */
public final class AvlTree implements Map<Integer, String> {

    /**
     * Default Tree size for Set and List used in.
     * keySet(),values(),entrySet()
     */
    private static final int DEFAULT_TREE_SIZE = 16;

    /**
     * Reference to root element.
     */
    private TreeNode root;

    /**
     * Ctor.
     */
    @SuppressWarnings("JavadocMethod")
    public AvlTree(final Integer key, final String value) {
        this.root = new TreeNode(key, value, 0);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public boolean containsKey(final Object key) {
        return AvlTree.valueByKey(this.root, (Integer) key) != null;
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
        this.root = AvlTree.put(this.root, key, value);
        return value;
    }

    @Override
    public String remove(final Object key) {
        return null;
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
        final Set<Integer> keySet = new LinkedHashSet<>(DEFAULT_TREE_SIZE);
        AvlTree.collectKeys(this.root, keySet);
        return keySet;
    }

    @Override
    public Collection<String> values() {
        final List<String> values = new ArrayList<>(DEFAULT_TREE_SIZE);
        AvlTree.collectValues(this.root, values);
        return values;
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return null;
    }

    /**
     * Check that node or children of node has given key.
     *
     * @param node Current node
     * @param key  Key
     * @return Value of node with given key
     * Or null if not found
     */
    private static String valueByKey(final TreeNode node, final Integer key) {
        final String value;
        if (node != null) {
            if (node.key.equals(key)) {
                value = node.value;
            } else if (node.key > key) {
                value = AvlTree.valueByKey(node.left, key);
            } else {
                value = AvlTree.valueByKey(node.right, key);
            }
        } else {
            //node is not found
            value = null;
        }
        return value;
    }

    /**
     * Collect all values starting from given node.
     *
     * @param node   Current node, root by default
     * @param values List to store each value
     */
    private static void collectValues(
            final TreeNode node,
            final List<String> values
    ) {
        if (node != null) {
            values.add(node.value);
            if (node.hasLeft()) {
                AvlTree.collectValues(node.left, values);
            }
            if (node.hasRight()) {
                AvlTree.collectValues(node.right, values);
            }
        }
    }

    /**
     * Collect all keys starting from root.
     *
     * @param node   Current node
     * @param keySet Keys storage
     */
    private static void collectKeys(
            final TreeNode node,
            final Set<Integer> keySet
    ) {
        if (node != null) {
            keySet.add(node.key);
            if (node.hasLeft()) {
                AvlTree.collectKeys(node.left, keySet);
            }
            if (node.hasRight()) {
                AvlTree.collectKeys(node.right, keySet);
            }
        }
    }

    /**
     * Put new key value pair as child of given node.
     * Re balance tree of necessary
     * Refresh heights of all nodes
     *
     * @param currentNode Current node
     * @param key         Key
     * @param value       Value
     * @return Current node to update root
     */
    private static TreeNode put(
            final TreeNode currentNode,
            final Integer key,
            final String value
    ) {
        if (currentNode == null) {
            return new TreeNode(key, value, 0);
        }
        if (currentNode.key > key) {
            currentNode.left = AvlTree.put(currentNode.left, key, value);
        } else if (currentNode.key < key) {
            currentNode.right = AvlTree.put(currentNode.right, key, value);
        } else if (currentNode.key.equals(key)) {
            currentNode.value = value;
        }
        currentNode.refreshHeight();
        return AvlTree.balance(currentNode);
    }

    /**
     * Balance given node.
     *
     * @param node Current node
     * @return Current node to update root
     */
    @SuppressWarnings({"FinalParameters", "ParameterAssignment"})
    private static TreeNode balance(TreeNode node) {
        //left rotate
        if (node.balanceFactor() < -1) {
            //right-left rotate
            if (node.right.balanceFactor() > 0) {
                node.right = AvlTree.rotateRight(node.right);
            }
            node = AvlTree.rotateLeft(node);
            //right rotate
        } else if (node.balanceFactor() > 1) {
            if (node.left.balanceFactor() < 0) {
                //left-right rotate
                node.left = AvlTree.rotateLeft(node.left);
            }
            node = AvlTree.rotateRight(node);
        }
        return node;
    }

    /**
     * Does left rotation.
     * Right child of root becomes newRoot
     * Left child of newRoot becomes right child of old root
     * Root of newRoot points to old root's root
     * Root of old root point to new root
     * Left child of newRoot point to oldRoot
     * Refresh heights of new root and old root
     *
     * @param oldRoot Current root
     * @return New root
     */
    private static TreeNode rotateLeft(final TreeNode oldRoot) {
        final TreeNode newRoot = oldRoot.right;
        oldRoot.right = newRoot.left;
        if (newRoot.hasLeft()) {
            newRoot.left.root = oldRoot;
        }
        newRoot.root = oldRoot.root;
        if (oldRoot.isLeft()) {
            oldRoot.root.left = newRoot;
        } else if (oldRoot.isRight()) {
            oldRoot.root.right = newRoot;
        }
        oldRoot.root = newRoot;
        newRoot.left = oldRoot;
        oldRoot.refreshHeight();
        newRoot.refreshHeight();
        return newRoot;
    }

    /**
     * Does right rotation.
     * 1) Left child of old root becomes new root
     * 2) Right child of newRoot becomes left child of old root
     * 3) Root of newRoot points to root of oldRoot
     * 4) Root of oldRoot points to newRoot
     * 5) Right child of newRoot points to old root
     * 6) Refresh heights of old root and new root
     *
     * @param oldRoot Current root
     * @return New Root
     */
    private static TreeNode rotateRight(final TreeNode oldRoot) {
        final TreeNode newRoot = oldRoot.left;
        oldRoot.left = newRoot.right;
        if (newRoot.hasRight()) {
            newRoot.right.root = oldRoot;
        }
        newRoot.root = oldRoot.root;
        if (oldRoot.isRight()) {
            oldRoot.root.right = newRoot;
        } else if (oldRoot.isLeft()) {
            oldRoot.right.left = newRoot;
        }
        newRoot.right = oldRoot;
        oldRoot.root = newRoot;
        oldRoot.refreshHeight();
        newRoot.refreshHeight();
        return newRoot;
    }
}
