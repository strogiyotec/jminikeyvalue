package com.lightbox.jmkv.btree;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Node of Btree.
 * Constraints:
 * t - minDegree
 * 1) Every non root node must have at least t-1 keys
 * 2) Every node must have at most 2t-1 keys
 * 3) Every internal node(without root) must have at least t children
 * And at most 2t children
 */
final class BtreeNode {

    /**
     * Comparator to compare Nodes.
     */
    private static final Comparator<BtreeNode> COMPARATOR =
            Comparator.comparingInt(node -> node.key(0).key);

    /**
     * Reference to parent.
     */
    private BtreeNode parent;

    /**
     * Array of keys.
     */
    private final NodeEntry[] keys;

    /**
     * Array of children.
     */
    private final BtreeNode[] children;

    /**
     * Size of keys.
     */
    private final AtomicInteger keysSize = new AtomicInteger(0);

    /**
     * Size of children.
     */
    private final AtomicInteger childrenSize = new AtomicInteger(0);

    /**
     * Ctor.
     *
     * @param children Amount of children
     * @param keys     Amount of keys
     * @param parent   Ref to root
     */
    BtreeNode(
            final BtreeNode parent,
            final int keys,
            final int children
    ) {
        this.parent = parent;
        this.keys = new NodeEntry[keys];
        this.children = new BtreeNode[children];
    }

    /**
     * Root ctor.
     *
     * @param children Amount of children
     * @param keys     Amount of keys
     */
    BtreeNode(final int keys, final int children) {
        this.parent = null;
        this.keys = new NodeEntry[keys];
        this.children = new BtreeNode[children];
    }

    /**
     * Add key and sort array of keys.
     *
     * @param key   Key
     * @param value Value
     */
    public void addKey(final Integer key, final String value) {
        this.keys[this.keysSize.getAndIncrement()] = new NodeEntry(key, value);
        Arrays.sort(this.keys, 0, this.keysSize.get());
    }

    /**
     * Add key and sort array of keys.
     *
     * @param entry Entry to add
     */
    public void addKey(final NodeEntry entry) {
        this.keys[this.keysSize.getAndIncrement()] = entry;
        Arrays.sort(this.keys, 0, this.keysSize.get());
    }

    /**
     * Add key and sort array of keys.
     *
     * @param child Child to add
     */
    public void addChild(final BtreeNode child) {
        child.parent = this;
        this.children[this.childrenSize.getAndIncrement()] = child;

        Arrays.sort(this.children, 0, this.childrenSize.get(), COMPARATOR);
    }

    /**
     * Amount of children.
     *
     * @return Amount of children
     */
    public int children() {
        return this.childrenSize.get();
    }

    /**
     * Get child by index.
     *
     * @param index Index of child
     * @return Child by index
     */
    public BtreeNode child(final int index) {
        return this.children[index];
    }

    /**
     * Amount of keys.
     *
     * @return Amount of keys
     */
    public int keys() {
        return this.keysSize.get();
    }

    /**
     * Get key by index.
     *
     * @param index Index
     * @return Key by index
     */
    public NodeEntry key(final int index) {
        return this.keys[index];
    }

    /**
     * Chec that node has parent.
     *
     * @return True of parent is not null
     */
    public boolean hasParent() {
        return this.parent != null;
    }

    /**
     * Update reference to parent.
     *
     * @param node New parent
     */
    public void withRoot(final BtreeNode node) {
        this.parent = node;
    }

    /**
     * Fetch current parent.
     *
     * @return Parent of current node
     */
    public BtreeNode parent() {
        return this.parent;
    }

    /**
     * Remove given node from tree.
     *
     * @param node Node to remove
     * @return True if node was deleted
     */
    public boolean removeChild(final BtreeNode node) {
        if (this.childrenSize.get() == 0) {
            return false;
        }
        int idx = -1;
        for (int i = 0; i < this.childrenSize.get(); i++) {
            if (this.children[i] == node) {
                idx = i;
                break;
            }
        }
        if (idx != -1) {
            System.arraycopy(
                    children,
                    idx,
                    this.children,
                    idx - 1,
                    this.childrenSize.get() - idx
            );
            this.childrenSize.decrementAndGet();
            this.children[this.childrenSize.get()] = null;
            return true;
        }
        return false;
    }
}
