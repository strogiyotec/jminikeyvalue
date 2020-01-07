package com.lightbox.jmkv.btree;

/**
 * Find index of prev key.
 * Key in position should be bigger or
 * equal to given key
 * For example:
 * Keys: 2,4,7,9,11,15,17,21
 * Search key:12
 * Previous position will be 4 - position of 15 minus 1
 */
public final class BtPrevKeySearch extends BtreeSearch {

    /**
     * Ctor.
     *
     * @param node Node
     * @param key  Key to search
     */
    public BtPrevKeySearch(
            final BtreeNode node,
            final Integer key
    ) {
        super(
                node,
                key,
                1,
                node.keys(),
                BtPrevKeySearch::search
        );
    }

    /**
     * Ctor.
     *
     * @param node Node
     * @param key  Key to search
     */
    public BtPrevKeySearch(
            final BtreeNode node,
            final NodeKey key
    ) {
        super(
                node,
                key.key,
                1,
                node.keys(),
                BtPrevKeySearch::search
        );
    }

    /**
     * Find position before given key.
     *
     * @param node      Node
     * @param indexFrom Index from
     * @param indexTo   Index to
     * @param key       Key
     * @return Previous position
     */
    private static BtreeSearch search(
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
                low = mid;
            } else if (midVal >= key) {
                return new BtreeSearch(true, mid - 1);
            }
        }
        return new BtreeSearch(true, node.keys() - 1);
    }
}
