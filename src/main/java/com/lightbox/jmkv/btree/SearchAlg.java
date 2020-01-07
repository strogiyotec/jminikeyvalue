package com.lightbox.jmkv.btree;

/**
 * Search key in Btree node.
 */
interface SearchAlg {

    /**
     * Search Key in node.
     *
     * @param node      Node with keys
     * @param indexFrom Start position
     * @param indexTo   End position
     * @param key       Key to find
     * @return BtreeSearch with search result
     */
    BtreeSearch searchKey(
            BtreeNode node,
            int indexFrom,
            int indexTo,
            Integer key
    );

    /**
     * Default search using binary search.
     */
    final class DefaultBinarySearch implements SearchAlg {

        @Override
        public BtreeSearch searchKey(
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
}
