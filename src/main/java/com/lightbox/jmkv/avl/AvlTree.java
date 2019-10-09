package com.lightbox.jmkv.avl;

import com.lightbox.jmkv.ImmutableEntry;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
    private AvlNode root;

    /**
     * Ctor.
     */
    @SuppressWarnings("JavadocMethod")
    public AvlTree(final Integer key, final String value) {
        this.root = new AvlNode(key, value, 0);
    }

    @Override
    public int size() {
        return AvlTree.size(this.root, new AtomicInteger(0));
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
        return AvlTree.hasValue(this.root, (String) value);
    }

    @Override
    public String get(final Object key) {
        return AvlTree.valueByKey(this.root, (Integer) key);
    }

    @Override
    public String put(final Integer key, final String value) {
        if (value == null) {
            throw new IllegalArgumentException("Null values are not allowed");
        }
        this.root = AvlTree.put(this.root, key, value);
        return value;
    }

    @Override
    public String remove(final Object key) {
        return this.removeNode(this.root, (Integer) key);
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
    @SuppressWarnings("LineLength")
    public Set<Entry<Integer, String>> entrySet() {
        final Set<Entry<Integer, String>> entrySet = new LinkedHashSet<>(DEFAULT_TREE_SIZE);
        AvlTree.collectEntries(this.root, entrySet);
        return entrySet;
    }

    /**
     * Remove node with given key from given node.
     *
     * @param node Current node
     * @param key  Key
     * @return Value of deleted node
     * or null if no node with given key
     */
    @SuppressWarnings("ReturnCount")
    private String removeNode(final AvlNode node, final Integer key) {
        if (node == null) {
            return null;
        }
        if (node.key.equals(key)) {
            final String value = node.value;
            if (node.isRoot()) {
                this.root = null;
                throw new IllegalStateException(
                        "Root was deleted. Tree is not valid anymore"
                );
            }
            if (!node.hasChild()) {
                AvlTree.deleteNodeWithoutChildren(node);
                node.root.refreshHeight();
                this.root = AvlTree.balanceToRoot(node.root);
            } else if (node.hasOneChild()) {
                AvlTree.deleteNodeWithOneChild(node);
                node.root.refreshHeight();
                this.root = AvlTree.balanceToRoot(node.root);
            } else {
                node.key = AvlNode.minKey(node.right);
                this.removeNode(node.right, node.key);
            }
            return value;
        }
        if (node.key > key) {
            return this.removeNode(node.left, key);
        }
        return this.removeNode(node.right, key);
    }

    /**
     * Balance each node starting from given till the last root.
     *
     * @param node Current node
     * @return Balanced node
     */
    @SuppressWarnings({"FinalParameters", "ParameterAssignment"})
    private static AvlNode balanceToRoot(AvlNode node) {
        AvlNode temp = node;
        while (temp != null) {
            node = AvlTree.balance(temp);
            temp = temp.root;
        }
        return node;
    }

    /**
     * Check that node or one of child has given value.
     *
     * @param node  Current node
     * @param value Value
     * @return True if value is present in given node
     */
    private static boolean hasValue(final AvlNode node, final String value) {
        final boolean contains;
        if (node == null) {
            contains = false;
        } else if (node.value.equals(value)) {
            contains = true;
        } else {
            contains = AvlTree.hasValue(node.left, value)
                    || AvlTree.hasValue(node.right, value);
        }
        return contains;
    }

    /**
     * Calculate size of node.
     *
     * @param node Current node
     * @param size Atomic integer to store size
     * @return Size of node
     */
    private static int size(final AvlNode node, final AtomicInteger size) {
        if (node != null) {
            size.incrementAndGet();
            if (node.hasLeft()) {
                AvlTree.size(node.left, size);
            }
            if (node.hasRight()) {
                AvlTree.size(node.right, size);
            }
        }
        return size.get();
    }

    /**
     * Collect entries of node and it's children.
     *
     * @param node     Current node
     * @param entrySet Set to store entries
     */
    private static void collectEntries(
            final AvlNode node,
            final Set<Entry<Integer, String>> entrySet
    ) {
        if (node != null) {
            entrySet.add(new ImmutableEntry<>(node.key, node.value));
            if (node.hasLeft()) {
                AvlTree.collectEntries(node.left, entrySet);
            }
            if (node.hasRight()) {
                AvlTree.collectEntries(node.right, entrySet);
            }
        }
    }

    /**
     * Check that node or children of node has given key.
     *
     * @param node Current node
     * @param key  Key
     * @return Value of node with given key
     * Or null if not found
     */
    private static String valueByKey(final AvlNode node, final Integer key) {
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
            final AvlNode node,
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
            final AvlNode node,
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
    private static AvlNode put(
            final AvlNode currentNode,
            final Integer key,
            final String value
    ) {
        if (currentNode == null) {
            return new AvlNode(key, value, 0);
        }
        if (currentNode.key > key) {
            currentNode.left = AvlTree.put(currentNode.left, key, value);
            currentNode.left.root = currentNode;
        } else if (currentNode.key < key) {
            currentNode.right = AvlTree.put(currentNode.right, key, value);
            currentNode.right.root = currentNode;
        } else if (currentNode.key.equals(key)) {
            currentNode.value = value;
        }
        currentNode.refreshHeight();
        return AvlTree.balance(currentNode);
    }

    /**
     * Delete given node.
     * This node has to have at least one child
     * If this node is left node of it's root
     * then assign either left or right child of given node
     * as the left child of node's root
     * If this node is right node of it's root
     * then  assign either left or right child of given node
     * as the right child of node's root
     *
     * @param node Node to delete
     */
    private static void deleteNodeWithOneChild(final AvlNode node) {
        if (node.isLeft()) {
            if (node.hasLeft()) {
                node.root.left = node.left;
                node.left.root = node.root;
            }
            if (node.hasRight()) {
                node.root.left = node.right;
                node.right.root = node.root;
            }
        }
        if (node.isRight()) {
            if (node.hasLeft()) {
                node.root.right = node.left;
                node.left.right = node.root;
            }
            if (node.hasRight()) {
                node.root.right = node.right;
                node.right.root = node.root;
            }
        }

    }

    /**
     * Delete reference to given node from it's root.
     *
     * @param node Node to delete
     */
    private static void deleteNodeWithoutChildren(final AvlNode node) {
        final AvlNode root = node.root;
        if (root.left == node) {
            root.left = null;
        } else if (root.right == node) {
            root.right = null;
        }
    }

    /**
     * Balance given node.
     *
     * @param node Current node
     * @return Current node to update root
     */
    @SuppressWarnings({"FinalParameters", "ParameterAssignment"})
    private static AvlNode balance(AvlNode node) {
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
    private static AvlNode rotateLeft(final AvlNode oldRoot) {
        final AvlNode newRoot = oldRoot.right;
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
    private static AvlNode rotateRight(final AvlNode oldRoot) {
        final AvlNode newRoot = oldRoot.left;
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
