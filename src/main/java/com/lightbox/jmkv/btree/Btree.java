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
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public boolean containsKey(final Object key) {
        return false;
    }

    @Override
    public boolean containsValue(final Object value) {
        return false;
    }

    //TODO rewrite for checkstyle
    @Override
    @SuppressWarnings({"CyclomaticComplexity", "ExecutableStatementCount"})
    public String get(final Object key) {
        final Integer intKey = (Integer) key;
        BtreeNode node = this.root;
        while (node != null) {
            //if in left child
            final NodeEntry least = node.firstEntry();
            if (intKey < least.key) {
                if (node.hasChildren()) {
                    node = node.child(0);
                } else {
                    node = null;
                }
                continue;
            }
            //if in right child
            final NodeEntry lastEntry = node.lastEntry();
            if (intKey > lastEntry.key) {
                if (node.children() > node.keys()) {
                    node = node.child(node.keys());
                } else {
                    node = null;
                }
                continue;
            }
            //if among keys of current node
            final int last = node.keys() - 1;
            for (int i = 0; i < node.keys(); i++) {
                final NodeEntry currentKey = node.key(i);
                if (currentKey.key.equals(intKey)) {
                    return currentKey.value;
                }
                final int next = i + 1;
                if (next <= last) {
                    final NodeEntry nextKey = node.key(next);
                    if (currentKey.key < intKey && nextKey.key > intKey) {
                        if (next < node.children()) {
                            node = node.child(next);
                            break;
                        }
                        return null;
                    }
                }
            }

        }
        return null;
    }

    @Override
    public String put(final Integer key, final String value) {
        if (this.root == null) {
            return this.initRoot(key, value);
        } else {
            BtreeNode node = this.root;
            while (node != null) {
                if (!node.hasChildren()) {
                    node.addKey(key, value);
                    if (node.keys() > this.maxKeys()) {
                        this.split(node);
                        break;
                    }
                }
                if (key.compareTo(node.firstEntry().key) <= 0) {
                    node = node.childOrNull(0);
                    continue;
                }
                if (key.compareTo(node.lastEntry().key) > 0) {
                    node = node.childOrNull(node.keys());
                    continue;
                }
                //If btree has root with (3,6) first children is (1,2)
                // second children (4,5) third children is 7,8
                // if you want to add '4.5' then second node
                // should be chosen and {@link #keyBetweenEntries}
                // will return true for
                // 4.5 because it's bigger than
                // 3(first key of root) and smaller than 6
                // Otherwise Node will be null and while
                // loop will be interrupted
                for (int i = 1; i < node.keys(); i++) {
                    final NodeEntry prev = node.key(i - 1);
                    final NodeEntry current = node.key(i);
                    if (Btree.keyBetweenEntries(key, prev, current)) {
                        node = node.childOrNull(i);
                        break;
                    }
                }
            }
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
        return this.branchingNumber + 1;
    }

    /**
     * Max amount of children.
     *
     * @return Max amount of children
     */
    private int maxChildren() {
        return 2 * this.branchingNumber - 1;
    }

    /**
     * Init root with first entry value.
     * Length of keys and children bigger than maxKeys and maxChildren on 1
     * It was done in this way because in {@link #put(Integer, String)}
     * Btree firstly adds new key and then check if it exceeds maxKeys value
     *
     * @param key   Key
     * @param value Value
     * @return Value
     */
    private String initRoot(final Integer key, final String value) {
        this.root = new BtreeNode(this.maxKeys() + 1, this.maxChildren() + 1);
        this.root.addKey(key, value);
        return value;
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
        final NodeEntry middleKey = node.key(middle);
        if (!node.hasParent()) {
            this.refreshRoot(left, right, middleKey);
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
     * Create new root.
     * This method is called only if current node is root
     *
     * @param left      Left node
     * @param right     Right node
     * @param middleKey Middle key entry
     */
    private void refreshRoot(
            final BtreeNode left,
            final BtreeNode right,
            final NodeEntry middleKey
    ) {
        final BtreeNode newRoot =
                new BtreeNode(
                        this.maxKeys(),
                        this.maxChildren()
                );
        newRoot.addKey(middleKey);
        this.root = newRoot;
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
            final NodeEntry prev,
            final NodeEntry current
    ) {
        return key.compareTo(prev.key) > 0
                && key.compareTo(current.key) <= 0;
    }
}
