package com.lightbox.jmkv.binary;

import com.lightbox.jmkv.ImmutableEntry;
import com.lightbox.jmkv.TreeNode;

import java.util.*;

/**
 * Binary Tree.
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
    private TreeNode root;

    /**
     * Size.
     */
    private int size;

    /**
     * Ctor.
     */
    public BinaryTree() {
        this.size = 0;
    }


    public BinaryTree(final TreeNode root) {
        this.root = root;
        this.size = 1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean containsKey(final Object key) {
        if (this.size == 0) {
            return false;
        }
        return BinaryTree.valueByKey(this.root, (Integer) key) != null;
    }

    @Override
    public boolean containsValue(final Object value) {
        if (this.size == 0) {
            return false;
        }
        return BinaryTree.containsValue(this.root, (String) value);
    }

    @Override
    public String get(final Object key) {
        if (this.size == 0) {
            return null;
        }
        return BinaryTree.valueByKey(this.root, (Integer) key);
    }

    @Override
    public String put(final Integer key, final String value) {
        if (this.size == 0) {
            this.root = new BinaryTreeNode(key, value);
        } else {
            BinaryTree.add(this.root, key, value);
        }
        this.size++;
        return value;
    }

    @Override
    public String remove(final Object key) {
        if (this.size == 0) {
            return null;
        }
        final String removed = this.remove(this.root, (Integer) key);
        if (removed != null) {
            this.size--;
        }
        return removed;
    }

    @Override
    public void putAll(final Map<? extends Integer, ? extends String> map) {
        if (this.size == 0) {
            return;
        }
        map.forEach(this::put);
    }

    @Override
    public void clear() {
        this.size = 0;
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
        if (this.size == 0) {
            return Collections.emptySet();
        }
        final Set<Entry<Integer, String>> entries = new HashSet<>(DEFAULT_TREE_SIZE);
        BinaryTree.collectEntries(this.root, entries);
        return entries;
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
        } else if (current.key().equals(key)) {
            if (current.isRoot()) {
                this.root = null;
            } else {
                //node doesn't have children
                final int children = current.children();
                if (children == 0) {
                    return BinaryTree.deleteNodeWithoutChildren(current);
                    //node has exactly one child
                } else if (children == 1) {
                    return BinaryTree.deleteNodeWithOneChild(current);
                } else {
                    //set lowest key to deleted node
                    // and delete original node with this lowest key
                    current.setKey(current.right().minKey());
                    this.remove(current.right(), current.key());
                    return current.value();
                }
            }
        } else if (current.key() > key) {
            return this.remove(current.left(), key);
        }
        return this.remove(current.right(), key);
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
        entries.add(new ImmutableEntry<>(current.key(), current.value()));
        BinaryTree.collectEntries(current.left(), entries);
        BinaryTree.collectEntries(current.right(), entries);
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
        values.add(current.value());
        BinaryTree.collectValues(current.left(), values);
        BinaryTree.collectValues(current.right(), values);
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
        keySet.add(current.key());
        BinaryTree.collectKeys(current.left(), keySet);
        BinaryTree.collectKeys(current.right(), keySet);
    }


    /**
     * Delete reference to given root from the child.
     *
     * @param node Node to be deleted
     * @return Value of current root
     */
    private static String deleteNodeWithOneChild(final TreeNode node) {
        final String value = node.value();
        if (node.isLeft()) {
            if (node.left() != null) {
                node.left().setRoot(node.right());
                node.root().setLeft(node.left());
            }
            if (node.right() != null) {
                node.right().setRoot(node.root());
                node.root().setLeft(node.right());
            }
        }
        if (node.isRight()) {
            if (node.left() != null) {
                node.left().setRoot(node.root());
                node.root().setRight(node.left());

            }
            if (node.right() != null) {
                node.right().setRoot(node.root());
                node.root().setRight(node.right());
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
            final TreeNode current
    ) {
        final TreeNode root = current.root();
        final String value = current.value();
        if (root.left() == current) {
            root.setLeft(null);
        } else if (root.right() == current) {
            root.setRight(null);
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
        if (root.key() < key) {
            if (root.right() == null) {
                root.setRight(new BinaryTreeNode(root, key, value));
            } else {
                BinaryTree.add(root.right(), key, value);
            }
        } else {
            if (root.left() == null) {
                root.setLeft(new BinaryTreeNode(root, key, value));
            } else {
                BinaryTree.add(root.left(), key, value);
            }
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
            final TreeNode currentNode,
            final Integer key
    ) {
        if (currentNode == null) {
            return null;
        }
        if (currentNode.key().equals(key)) {
            return currentNode.value();
        }
        if (currentNode.key() > key) {
            return BinaryTree.valueByKey(currentNode.left(), key);
        }
        return BinaryTree.valueByKey(currentNode.right(), key);
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
            final TreeNode currentNode,
            final String value
    ) {
        if (currentNode == null) {
            return false;
        }
        if (currentNode.value().equals(value)) {
            return true;
        }
        return BinaryTree.containsValue(currentNode.left(), value)
                || BinaryTree.containsValue(currentNode.right(), value);
    }
}
