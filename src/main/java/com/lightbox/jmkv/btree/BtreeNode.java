package com.lightbox.jmkv.btree;

import com.lightbox.jmkv.ArraySort;
import com.sun.istack.internal.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Node of Btree.
 * Constraints:
 * t - minDegree
 * 1) Every non root node must have at least t-1 keys
 * 2) Every node must have at most 2t-1 keys
 * 3) Every internal node(at least one child) must have at least t children
 * 4) External node - node without children
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
        this(
                null,
                keys,
                children
        );
    }

    /**
     * Create new BtreeNode.
     * Move all keys from fromIndex to toIndex to new node
     * Move all children from fromIndex to toIndex inclusive to new node
     * Can't use ctor. because {@link BtreeNode}
     * already has ctor with given params
     *
     * @param parent    Reference to parent
     * @param fromIndex Index from
     * @param toIndex   Index to
     * @return New node
     */
    static BtreeNode subNode(
            final BtreeNode parent,
            final int fromIndex,
            final int toIndex
    ) {
        final BtreeNode node =
                new BtreeNode(
                        parent.keysSize.get(),
                        parent.childrenSize.get()
                );
        for (int i = fromIndex; i < toIndex; i++) {
            node.addKey(parent.key(i));
        }
        if (parent.hasChildren()) {
            for (int i = fromIndex; i <= toIndex; i++) {
                node.addChild(parent.child(i));
            }
        }
        return node;
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
     * Move all elements of array to one position right
     * Add new key to empty position
     *
     * @param entry Entry to add
     */
    public void addKey(final NodeEntry entry) {
        if (this.keysSize.get() == 0) {
            this.keys[this.keysSize.getAndIncrement()] = entry;
        } else {
            int index;
            for (index = 0; index < this.keysSize.get(); index++) {
                if (this.keys[index].compareTo(entry) >= 0) {
                    break;
                }
            }
            System.arraycopy(
                    this.keys,
                    index,
                    this.keys,
                    index + 1,
                    this.keysSize.get() + 1 - index
            );
            this.keys[index] = entry;
            this.keysSize.getAndIncrement();
        }
    }

    /**
     * Add key and sort array of keys with custom sorter.
     *
     * @param sort  Logic to sort array of keys
     * @param entry Entry to add
     */
    public void addKey(final NodeEntry entry, final ArraySort sort) {
        this.keys[this.keysSize.getAndIncrement()] = entry;
        sort.sort(this.keys, 0, this.keysSize.get());
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
     * Get child by index or null if index bigger than amount of children.
     *
     * @param index Index
     * @return Child Or Null
     */
    @Nullable
    public BtreeNode childOrNull(final int index) {
        //need it in order to exit while loop in
        // {@link com.lightbox.jmkv.btree.Btree#put(Integer, String)} method
        if (index >= this.childrenSize.get()) {
            return null;
        }
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
     * Check that node has parent.
     *
     * @return True of parent is not null
     */
    public boolean hasParent() {
        return this.parent != null;
    }

    /**
     * Check that node has children.
     *
     * @return True if amount of children bigger than 0
     */
    public boolean hasChildren() {
        return this.childrenSize.get() > 0;
    }

    /**
     * Get first key from keys array.
     *
     * @return First key
     */
    public NodeEntry firstEntry() {
        return this.keys[0];
    }

    /**
     * Get last key from keys array.
     *
     * @return Last key
     */
    public NodeEntry lastEntry() {
        return this.keys[this.keysSize.get() - 1];
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
        final boolean deleted;
        if (this.childrenSize.get() == 0) {
            deleted = false;
        } else {
            if (this.childrenSize.get() == 1 && this.children[0] == node) {
                this.children[0] = null;
                this.childrenSize.decrementAndGet();
                deleted = true;
            } else {
                boolean found = false;
                for (int i = 0; i < this.childrenSize.get(); i++) {
                    if (this.children[i] == node) {
                        found = true;
                    }
                    if (found && i + 1 < this.childrenSize.get()) {
                        this.children[i] = this.children[i + 1];
                    }
                }
                if (found) {
                    this.children[this.childrenSize.get()] = null;
                    this.childrenSize.decrementAndGet();
                    deleted = true;
                } else {
                    deleted = false;
                }
            }
        }
        return deleted;
    }

    /**
     * Get first children from given node.
     *
     * @return First child
     * @throws ArrayIndexOutOfBoundsException of node doesn't have children
     */
    public BtreeNode firstChild() {
        return this.children[0];
    }

    /**
     * Get last children from given node.
     *
     * @return Last child
     * @throws ArrayIndexOutOfBoundsException of node doesn't have children
     */
    public BtreeNode lastChild() {
        return this.children[this.childrenSize.get() - 1];
    }
}
