package com.lightbox.jmkv.btree;

import com.lightbox.jmkv.ArraySort;
import com.sun.istack.internal.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Node of Btree.
 * Constraints:
 * t - minDegree
 * 1) Every non parent node must have at least t-1 keys
 * 2) Every node must have at most 2t-1 keys
 * 3) Every internal node(at least one child) must have at least t children
 * 4) External node - node without children
 * And at most 2t children
 */
class BtreeNode {

    /**
     * Comparator to compare Children.
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
    private final NodeKey[] keys;

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
     * @param parent   Ref to parent
     */
    BtreeNode(
            final BtreeNode parent,
            final int keys,
            final int children
    ) {
        this.parent = parent;
        this.keys = new NodeKey[keys];
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
     * Add key and sort array of keys.
     *
     * @param key   Key
     * @param value Value
     */
    public final void addKey(final Integer key, final String value) {
        this.addKey(new NodeKey(key, value));
    }

    /**
     * Add key and sort array of keys.
     * Move all elements of array to one position right
     * Add new key to empty position
     *
     * @param entry Entry to add
     */
    public final void addKey(final NodeKey entry) {
        if (this.keysSize.get() == 0) {
            this.keys[this.keysSize.getAndIncrement()] = entry;
        } else {
            int index;
            //TODO rewrite to binary search
            for (index = 0; index < this.keysSize.get(); index++) {
                if (this.keys[index].compareTo(entry) >= 0) {
                    break;
                }
            }
            if (index == this.keysSize.get()) {
                this.keys[this.keysSize.getAndIncrement()] = entry;
            } else {
                //move all elements to one position right
                System.arraycopy(
                        this.keys,
                        index,
                        this.keys,
                        index + 1,
                        this.keysSize.getAndIncrement() - index
                );
                this.keys[index] = entry;
            }
        }
    }

    /**
     * Add key and sort array of keys with custom sorter.
     *
     * @param sort  Logic to sort array of keys
     * @param entry Entry to add
     */
    public final void addKey(final NodeKey entry, final ArraySort sort) {
        this.keys[this.keysSize.getAndIncrement()] = entry;
        sort.sort(this.keys, 0, this.keysSize.get());
    }

    /**
     * Add key and sort array of keys.
     *
     * @param child Child to add
     */
    public final void addChild(final BtreeNode child) {
        child.parent = this;
        this.children[this.childrenSize.getAndIncrement()] = child;

        Arrays.sort(this.children, 0, this.childrenSize.get(), COMPARATOR);
    }

    /**
     * Amount of children.
     *
     * @return Amount of children
     */
    public final int children() {
        return this.childrenSize.get();
    }

    /**
     * Get child by index.
     *
     * @param index Index of child
     * @return Child by index
     */
    public final BtreeNode child(final int index) {
        return this.children[index];
    }

    /**
     * Get greatest from given node.
     * Search the last right child
     *
     * @return Greatest child or Current node if it doesn't have children
     */
    public final BtreeNode greatestChild() {
        BtreeNode node = this;
        while (node.hasChildren()) {
            node = node.children[node.childrenSize.get() - 1];
        }
        return node;
    }

    /**
     * Get child by index or null if index bigger than amount of children.
     *
     * @param index Index
     * @return Child Or Null
     */
    @Nullable
    public final BtreeNode childOrNull(final int index) {
        if (index >= this.childrenSize.get() || index < 0) {
            return null;
        }
        return this.children[index];
    }

    /**
     * Amount of keys.
     *
     * @return Amount of keys
     */
    public final int keys() {
        return this.keysSize.get();
    }

    /**
     * Get key by index.
     *
     * @param index Index
     * @return Key by index
     */
    public final NodeKey key(final int index) {
        return this.keys[index];
    }

    /**
     * Check that node has parent.
     *
     * @return True of parent is not null
     */
    public final boolean hasParent() {
        return this.parent != null;
    }

    /**
     * Check that node has children.
     *
     * @return True if amount of children bigger than 0
     */
    public final boolean hasChildren() {
        return this.childrenSize.get() > 0;
    }

    /**
     * Get first key from keys array.
     *
     * @return First key
     */
    public final NodeKey firstKey() {
        return this.keys[0];
    }

    /**
     * Get last key from keys array.
     *
     * @return Last key
     */
    public final NodeKey lastKey() {
        return this.keys[this.keysSize.get() - 1];
    }

    /**
     * Fetch current parent.
     *
     * @return Parent of current node
     */
    public final BtreeNode parent() {
        return this.parent;
    }

    /**
     * Remove given node from tree.
     *
     * @param node Node to remove
     * @return True if node was deleted
     */
    public final boolean removeChild(final BtreeNode node) {
        final boolean deleted;
        if (this.childrenSize.get() == 0) {
            deleted = false;
        } else {
            boolean found = false;
            for (int i = 0; i < this.childrenSize.get(); i++) {
                if (this.children[i] == node) {
                    found = true;
                } else if (found) {
                    //move to left by one
                    this.children[i - 1] = this.children[i];
                }
            }
            if (found) {
                this.children[this.childrenSize.decrementAndGet()] = null;
                deleted = true;
            } else {
                deleted = false;
            }
        }
        return deleted;
    }

    /**
     * Remove child in given position.
     *
     * @param position Position of child
     * @return Removed child
     */
    public final BtreeNode removeChild(final int position) {
        final BtreeNode removed = this.children[position];
        if (position == this.childrenSize.get() - 1) {
            this.children[this.childrenSize.decrementAndGet()] = null;
        } else {
            System.arraycopy(
                    this.children,
                    position + 1,
                    this.children,
                    position,
                    this.childrenSize.get() - 1 - position
            );
            this.children[this.childrenSize.decrementAndGet()] = null;
        }
        return removed;
    }

    /**
     * Delete key by position.
     *
     * @param keyPosition Position of key
     * @return Removed key
     */
    public final NodeKey removeKey(final int keyPosition) {
        final NodeKey removed = this.keys[keyPosition];
        if (keyPosition == this.keysSize.get() - 1) {
            this.keys[this.keysSize.decrementAndGet()] = null;
        } else {
            System.arraycopy(
                    this.keys,
                    keyPosition + 1,
                    this.keys,
                    keyPosition,
                    this.keysSize.get() - 1 - keyPosition
            );
            this.keys[this.keysSize.decrementAndGet()] = null;
        }
        return removed;
    }

    /**
     * Remove the last key from node.
     *
     * @return Removed key
     */
    public final NodeKey removeLastKey() {
        final NodeKey removed = this.keys[this.keysSize.get() - 1];
        this.keys[this.keysSize.decrementAndGet()] = null;
        return removed;
    }

    /**
     * Remove the first key from node.
     *
     * @return Removed key
     */
    public final NodeKey removeFirstKey() {
        final NodeKey removed = this.keys[0];
        System.arraycopy(
                this.keys,
                1,
                this.keys,
                0,
                this.keysSize.get() - 1
        );
        this.keys[this.keysSize.decrementAndGet()] = null;
        return removed;
    }

    /**
     * Search node among children.
     *
     * @param node Node to search
     * @return Index of node or -1 if not found
     */
    public final int indexOfNode(final BtreeNode node) {
        for (int i = 0; i < this.childrenSize.get(); i++) {
            if (this.children[i] == node) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Search key amount keys.
     *
     * @param key Key to search
     * @return Index of key or -1 if not found
     */
    public final int indexOfKey(final NodeKey key) {
        final BtreeSearch btreeSearch = new BtreeSearch(this, key);
        if (btreeSearch.found()) {
            return btreeSearch.position();
        } else {
            return -1;
        }
    }

    /**
     * Check that node has keys.
     *
     * @return True of node has keys
     */
    public final boolean hasKeys() {
        return this.keysSize.get() > 0;
    }

    /**
     * Copy all keys.
     *
     * @param other Node from which to copy
     */
    public final void addAllKeys(final BtreeNode other) {
        for (int i = 0; i < other.keysSize.get(); i++) {
            this.addKey(other.keys[i]);
        }
    }

    /**
     * Copy all children.
     *
     * @param other Node from which to copy
     */
    public final void addAllChildren(final BtreeNode other) {
        for (int i = 0; i < other.childrenSize.get(); i++) {
            this.addChild(other.children[i]);
        }
    }

    /**
     * Delete reference to parent.
     */
    public final void removeParent() {
        this.parent = null;
    }

    @Override
    public String toString() {
        return Arrays.stream(this.keys)
                .filter(Objects::nonNull)
                .map(nodeKey -> nodeKey.key.toString())
                .collect(Collectors.joining(","));
    }
}
