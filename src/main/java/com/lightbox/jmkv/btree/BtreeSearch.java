package com.lightbox.jmkv.btree;

/**
 * Search key in BtreeNode.
 */
public class BtreeSearch {

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
        this(
                node,
                key,
                0,
                node.keys(),
                new SearchAlg.DefaultBinarySearch()
        );
    }

    /**
     * Ctor.
     *
     * @param node         Node with keys
     * @param key          Key to search
     * @param positionFrom Starting position for search
     * @param positionTo   Ending position for search
     */
    public BtreeSearch(
            final BtreeNode node,
            final Integer key,
            final int positionFrom,
            final int positionTo,
            final SearchAlg searchAlg
    ) {
        final BtreeSearch btreeSearch = searchAlg.searchKey(node, positionFrom, positionTo, key);
        this.found = btreeSearch.found;
        this.position = btreeSearch.position;
    }

    /**
     * Ctor.
     *
     * @param node Node with keys
     * @param key  Key to search
     */
    private BtreeSearch(final BtreeNode node, final NodeKey key) {
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
    public BtreeSearch(final boolean found, final int position) {
        this.found = found;
        this.position = position;
    }

    /**
     * Shows if key was found.
     *
     * @return True if found
     */
    public final boolean found() {
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
    public final int position() {
        return this.position;
    }

}
