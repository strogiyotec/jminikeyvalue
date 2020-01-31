package com.lightbox.jmkv.btree;

/**
 * Find position where given key could be inserted.
 * Example: keys are 2,4,6,8,10 and key is 7
 * If this key will be inserted then it's position would be 2 (before key 8)
 * If key is bigger than last key than position would be the last key position
 */
public final class TheoreticalKeyPosition extends BtreeSearch {

    /**
     * Ctor.
     *
     * @param node Node
     * @param key  Key to search
     */
    public TheoreticalKeyPosition(
            final BtreeNode node,
            final Integer key
    ) {
        super(
                node,
                key,
                1,
                node.keys(),
                TheoreticalKeyPosition::search
        );
    }

    /**
     * Ctor.
     *
     * @param node Node
     * @param key  Key to search
     */
    public TheoreticalKeyPosition(
            final BtreeNode node,
            final NodeKey key
    ) {
        super(
                node,
                key.key,
                1,
                node.keys(),
                TheoreticalKeyPosition::search
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
                low = mid + 1;
            } else if (midVal >= key) {
                return new BtreeSearch(true, mid - 1);
            }
        }
        return new BtreeSearch(true, node.keys() - 1);
    }
}
