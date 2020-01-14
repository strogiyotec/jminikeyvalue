package com.lightbox.jmkv.btree;

import com.lightbox.jmkv.ImmutableEntry;

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
     * Protected for testing
     */
    protected BtreeNode root;

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
    public String get(final Object key) {
        @SuppressWarnings("LineLength") final ImmutableEntry<BtreeNode, Integer> entry = this.search((Integer) key);
        if (entry != null) {
            return entry.getKey().key(entry.getValue()).value;
        } else {
            return null;
        }
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
        } else {
            this.put(new NodeKey(key, value));
        }
        this.size.incrementAndGet();
        return value;
    }

    @Override
    public String remove(final Object key) {
        @SuppressWarnings("LineLength") final ImmutableEntry<BtreeNode, Integer> entry = this.search((Integer) key);
        if (entry != null) {
            return this.remove(entry.getKey(), entry.getValue());
        } else {
            return null;
        }
    }

    private String remove(final BtreeNode node, final Integer keyPosition) {
        final NodeKey removed = node.removeKey(keyPosition);
        if (!node.hasChildren()) {
            if (node.hasParent() && node.keys() < this.minKeys()) {
                this.combined(node);
            } else if (!node.hasParent() && !node.hasKeys()) {
                this.root = null;
            }
        } else {
            final BtreeNode greatest = node.child(keyPosition).greatestChild();
            final NodeKey replaced = greatest.removeKey(greatest.keys() - 1);
            node.addKey(replaced);
            if (greatest.hasParent() && greatest.keys() < this.minKeys()) {
                this.combined(greatest);
            }
            if (greatest.children() > maxChildren()) {
                this.split(greatest);
            }
        }
        this.size.decrementAndGet();
        return removed.value;
    }

    private void combined(final BtreeNode node) {
        final BtreeNode parent = node.parent();
        final int index = parent.indexOfNode(node);
        final int left = index - 1;
        final int right = index + 1;
        BtreeNode rightNeighbour = null;
        if (right < parent.children()) {
            rightNeighbour = parent.child(right);
        }
        if (rightNeighbour != null && rightNeighbour.keys() > this.minKeys()) {
            final NodeKey firstKey = rightNeighbour.firstKey();
            final BtPrevKeySearch search = new BtPrevKeySearch(parent, firstKey);
            final NodeKey parentKey = parent.removeKey(search.position());
            final NodeKey neighborKey = rightNeighbour.removeKey(0);
            node.addKey(parentKey);
            parent.addKey(neighborKey);
            if (rightNeighbour.hasChildren()) {
                node.addChild(rightNeighbour.removeChild(0));
            }
        } else {
            BtreeNode leftNeighbour = null;
            if (left >= 0) {
                leftNeighbour = parent.child(left);
            }
            if (leftNeighbour != null && leftNeighbour.keys() >= this.minKeys()) {
                final NodeKey lastKey = leftNeighbour.lastKey();
                final BtNextKeySearch search = new BtNextKeySearch(parent, lastKey);
                final NodeKey parentKey = parent.removeKey(search.position());
                final NodeKey neighbourKey = leftNeighbour.removeKey(leftNeighbour.keys() - 1);
                node.addKey(parentKey);
                parent.addKey(neighbourKey);
            } else if (rightNeighbour != null && parent.hasKeys()) {
                final NodeKey firstKey = rightNeighbour.firstKey();
                final BtPrevKeySearch search = new BtPrevKeySearch(parent, firstKey);
                final NodeKey parentKey = parent.removeKey(search.position());
                parent.removeChild(rightNeighbour);
                node.addKey(parentKey);
                node.addAllKeys(rightNeighbour);
                node.addAllChildren(rightNeighbour);
                if (!parent.hasParent() && parent.keys() < this.minKeys()) {
                    this.combined(parent);
                } else if (!parent.hasKeys()) {
                    node.removeParent();
                    this.root = node;
                }
            } else if (leftNeighbour != null && parent.hasKeys()) {
                final NodeKey lastKey = leftNeighbour.lastKey();
                final BtNextKeySearch search = new BtNextKeySearch(parent, lastKey);
                final NodeKey parentKey = parent.removeKey(search.position());
                parent.removeChild(leftNeighbour);
                node.addKey(parentKey);
                node.addAllKeys(leftNeighbour);
                node.addAllChildren(leftNeighbour);
                if (parent.hasParent() && parent.keys() < this.minKeys()) {
                    this.combined(parent);
                } else {
                    node.removeParent();
                    this.root = node;
                }
            }
        }
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
     * Search given key in the Btree.
     *
     * @param key Key to search
     * @return Entry where Key is node with given key
     * and Value is position of given key
     * Null if key doesn't exist
     */
    private ImmutableEntry<BtreeNode, Integer> search(final Integer key) {
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
                return new ImmutableEntry<>(node, search.position());
            } else {
                //if key between first and last child
                if (this.betweenFirstAndLast(node, search.position())) {
                    node = node.child(search.position());
                    continue;
                }
                return null;
            }
        }
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
        return this.branchingNumber - 1;
    }

    /**
     * Max amount of keys.
     *
     * @return Max amount of keys
     */
    private int maxKeys() {
        return (this.branchingNumber * 2) - 1;
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
        final BtreeNode left = new SplitBtn(node, 0, middle);
        final BtreeNode right = new SplitBtn(node, middle + 1, keys);
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
     * Check if key is between the first and the lst child.
     *
     * @param node           Node with keys
     * @param searchPosition Search result
     * @return True if need to search key in next child
     */
    private boolean betweenFirstAndLast(
            final BtreeNode node,
            final int searchPosition
    ) {
        final int last = node.keys() - 1;
        //search is equal to last if key was bigger
        // than middle element if node
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
