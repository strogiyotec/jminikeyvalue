package com.lightbox.jmkv.btree;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * B-tree implementation.
 * Notes
 * 1) Number of children [B,2B) (false for root node)
 * 2) Number of keys [B-1,2B-1)
 */
public final class Btree implements Map<Integer, String> {

    /**
     * Number to determine amount of max keys and max children.
     */
    private final int branchingNumber;

    /**
     * Current size of tree.
     */
    private final AtomicInteger size = new AtomicInteger(0);

    /**
     * Reference to root.
     */
    private BtreeNode root;

    /**
     * Ctor.
     *
     * @param branchingFactor Order
     */
    public Btree(final int branchingFactor) {
        this.branchingNumber = branchingFactor;
    }

    @Override
    public int size() {
        return 0;
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

    @SuppressWarnings("ExecutableStatementCount")
    @Override
    public String put(final Integer key, final String value) {
        if (this.root == null) {
            this.root = new BtreeNode(this.maxKeys(), this.maxChildren());
            this.root.addKey(key, value);
            return value;
        } else {
            BtreeNode node = this.root;
            while (node != null) {
                if (node.children() == 0) {
                    node.addKey(key, value);
                    if (node.keys() <= this.maxKeys()) {
                        //OK
                        break;
                    } else {
                        this.split(node);
                        break;
                    }
                }
                final NodeEntry less = node.key(0);
                if (key.compareTo(less.key) <= 0) {
                    node = node.child(0);
                    continue;
                }
                final NodeEntry great = node.key(node.keys() - 1);
                if (key.compareTo(great.key) > 0) {
                    node = node.child(node.children());
                    continue;
                }
                for (int i = 1; i < node.keys(); i++) {
                    final NodeEntry prev = node.key(i - 1);
                    final NodeEntry next = node.key(i);
                    if (key.compareTo(prev.key) > 0
                            && key.compareTo(next.key) <= 0) {
                        node = node.child(i);
                        break;
                    }
                }
            }
        }
        this.size.incrementAndGet();
        return value;
    }

    /**
     * Split tree recursively.
     * // TODO: refactor to remove warning
     *
     * @param toSplit Node to split
     */
    @SuppressWarnings("ExecutableStatementCount")
    private void split(final BtreeNode toSplit) {
        BtreeNode node = toSplit;
        final int keys = node.keys();
        final int middle = keys / 2;
        final NodeEntry key = node.key(middle);
        final BtreeNode left =
                new BtreeNode(this.maxKeys(), this.maxChildren());
        for (int i = 0; i < middle; i++) {
            left.addKey(node.key(i));
        }
        if (node.children() > 0) {
            for (int i = 0; i <= middle; i++) {
                left.addChild(node.child(i));
            }
        }
        final BtreeNode right =
                new BtreeNode(this.maxKeys(), this.maxChildren());
        for (int i = middle + 1; i < keys; i++) {
            right.addChild(node.child(i));
        }
        if (!node.hasParent()) {
            final BtreeNode newRoot =
                    new BtreeNode(this.maxKeys(), this.maxChildren());
            newRoot.addKey(key);
            node.withRoot(newRoot);
            this.root = newRoot;
            node = this.root;
            node.addChild(left);
            node.addChild(right);
        } else {
            final BtreeNode parent = node.parent();
            parent.addKey(key);
            parent.removeChild(node);
            parent.addChild(left);
            parent.addChild(right);
            if (parent.keys() > this.maxKeys()) {
                this.split(parent);
            }
        }
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
     * Min amount of keys.
     *
     * @return Min amount of keys
     */
    private int minKeys() {
        return this.branchingNumber - 1;
    }

    /**
     * Max amount of keys.
     *
     * @return Max amount of keys
     */
    private int maxKeys() {
        return this.branchingNumber * 2 - 2;
    }

    /**
     * Min amount of children.
     *
     * @return Min amount of children
     */
    private int minChildren() {
        return this.branchingNumber;
    }

    /**
     * Max amount of children.
     *
     * @return Max amount of children
     */
    private int maxChildren() {
        return this.branchingNumber * 2 - 1;
    }


}
