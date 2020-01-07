package com.lightbox.jmkv.btree;

/**
 * Search key in BtreeNode.
 */
public final class BtreeSearch {

    /**
     * True if key was found.
     */
    private final boolean found;

    /**
     * Shows position of found key.
     * If key is not present then shows
     * the point at which the key would be inserted
     * into array
     */
    private final int position;

    /**
     * Ctor.
     *
     * @param node Node with keys
     * @param key  Key to search
     */
    public BtreeSearch(final BtreeNode node, final Integer key) {
        final BtreeSearch btreeSearch = searchKey(node, 0, node.keys(), key);
        this.found = btreeSearch.found;
        this.position = btreeSearch.position;
    }

    /**
     * Ctor.
     *
     * @param node Node with keys
     * @param key  Key to search
     */
    public BtreeSearch(final BtreeNode node, final NodeKey key) {
        this(
                node,
                key.key
        );
    }

    /**
     * Ctor.
     *
     * @param found    Found
     * @param position Position
     */
    private BtreeSearch(final boolean found, final int position) {
        this.found = found;
        this.position = position;
    }

    /**
     * Shows if key was found.
     *
     * @return True if found
     */
    public boolean found() {
        return this.found;
    }

    /**
     * Position of found key.
     * Or position of array where
     * key will be inserted if
     * not found
     *
     * @return Position
     */
    public int position() {
        return this.position;
    }

    /**
     * Search Key in array using binary search.
     *
     * @param node      Node with keys
     * @param indexFrom Start position
     * @param indexTo   End position
     * @param key       Key to find
     * @return BtreeSearch with search result
     */
    private static BtreeSearch searchKey(
            final BtreeNode node,
            final int indexFrom,
            final int indexTo,
            final Integer key
    ) {
        int low = indexFrom;
        int high = indexTo - 1;
        while (low <= high) {
            final int mid = (low + high) >>> 1;
            final Integer midVal = node.key(mid).key;
            if (midVal < key) {
                low = mid + 1;
            } else if (midVal > key) {
                high = mid - 1;
            } else {
                return new BtreeSearch(true, mid);
            }
        }
        return new BtreeSearch(false, low);
    }
}
