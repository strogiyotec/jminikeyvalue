package com.lightbox.jmkv.btree;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * B-tree implementation.
 */
public final class Btree implements Map<Integer, String> {

    /**
     * Number to determine amount of max keys and max children.
     * 1) Number of children [B,2B-1) (false for root node)
     * 2) Number of keys [B-1,2B)
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
        return this.size.get();
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public boolean containsKey(final Object key) {
        return this.get(key) != null;
    }

    @Override
    public boolean containsValue(final Object value) {
        return false;
    }

    @Override
    public String get(final Object kKey) {
        final Integer key = (Integer) kKey;
        BtreeNode node = this.root;
        while (node != null) {
            //if in left child
            if (key < node.firstKey().key) {
                node = node.childOrNull(0);
                continue;
            }
            //if in right child
            if (key > node.lastKey().key) {
                node = node.childOrNull(node.children() - 1);
                continue;
            }
            //if among keys of current node
            final BtreeSearch search = new BtreeSearch(node, key);
            if (search.found()) {
                return node.key(search.position()).value;
            } else {
                if (this.searchInNextChild(node, search.position())) {
                    node = node.child(search.position());
                    continue;
                }
                return null;
            }
        }
        return null;
    }

    @Override
    public String put(final Integer key, final String value) {
        if (this.root == null) {
            this.root = new BtnWithKey(
                    this.maxKeys(),
                    this.maxChildren(),
                    key,
                    value
            );
            return value;
        } else {
            this.put(new NodeKey(key, value));
        }
        this.size.incrementAndGet();
        return value;
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
     * Insert new key to Btree.
     *
     * @param key Key to insert
     */
    private void put(final NodeKey key) {
        BtreeNode node = this.root;
        while (node != null) {
            if (!node.hasChildren()) {
                node.addKey(key);
                if (node.keys() > this.maxKeys()) {
                    this.split(node);
                    break;
                }
            }
            //go to first child
            if (key.key <= node.firstKey().key) {
                //go to left child
                node = node.childOrNull(0);
                continue;
            }
            //go to last child
            if (key.key > node.lastKey().key) {
                node = node.childOrNull(node.keys());
                continue;
            }
            //find child between first and last
            for (int i = 1; i < node.keys(); i++) {
                final NodeKey prev = node.key(i - 1);
                final NodeKey current = node.key(i);
                if (Btree.keyBetweenEntries(key.key, prev, current)) {
                    node = node.childOrNull(i);
                    break;
                }
            }
        }
    }

    /**
     * Min amount of keys.
     *
     * @return Min amount of keys
     */
    private int minKeys() {
        return this.branchingNumber;
    }

    /**
     * Max amount of keys.
     *
     * @return Max amount of keys
     */
    private int maxKeys() {
        return this.branchingNumber * 2;
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
        return this.maxKeys() + 1;
    }

    /**
     * Split tree recursively.
     * Create two new nodes : left and right
     * Add first half of keys from original node to left
     * Add first half of children from original node to left
     * Add second half of keys from original node to right
     * Add second half of children from original node to right
     * Create new root if current one is root without parent
     *
     * @param node Node to split
     */
    private void split(final BtreeNode node) {
        final int keys = node.keys();
        final int middle = keys / 2;
        final BtreeNode left = BtreeNode.subNode(node, 0, middle);
        final BtreeNode right = BtreeNode.subNode(node, middle + 1, keys);
        final NodeKey middleKey = node.key(middle);
        if (!node.hasParent()) {
            this.splitRoot(left, right, middleKey);
        } else {
            final BtreeNode parent = node.parent();
            parent.addKey(middleKey);
            parent.removeChild(node);
            parent.addChild(left);
            parent.addChild(right);
            if (parent.keys() > this.maxKeys()) {
                this.split(parent);
            }
        }
    }

    /**
     * Check if key in the next child.
     *
     * @param node           Node with keys
     * @param searchPosition Search result
     * @return True if need to search key in next child
     */
    private boolean searchInNextChild(
            final BtreeNode node,
            final int searchPosition
    ) {
        final int last = node.keys() - 1;
        return searchPosition <= last && searchPosition < node.children();
    }

    /**
     * Create new root.
     * This method is called only if current node is root
     *
     * @param left      Left node
     * @param right     Right node
     * @param middleKey Middle key entry
     */
    private void splitRoot(
            final BtreeNode left,
            final BtreeNode right,
            final NodeKey middleKey
    ) {
        this.root = new BtnWithKey(
                this.maxKeys(),
                this.maxChildren(),
                middleKey
        );
        this.root.addChild(left);
        this.root.addChild(right);
    }

    /**
     * Check if key between prev and next.
     *
     * @param key     Current key
     * @param prev    Previous node
     * @param current Current node
     * @return True of key is bigger that prev and less or eq to current
     */
    private static boolean keyBetweenEntries(
            final Integer key,
            final NodeKey prev,
            final NodeKey current
    ) {
        return key.compareTo(prev.key) > 0
                && key.compareTo(current.key) <= 0;
    }
}
