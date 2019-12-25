package com.lightbox.jmkv.btree;

/**
 * Btree Node with one key.
 * Btree uses this class because it needs
 * bigger value for maxKeys and maxChildren
 * If maxKeys is 4 then length if keys will be 5
 * It requires because Btree firstly adds key
 * and then checks that amount of keys is bigger
 * then given maxKeys value
 */
class BtnWithKey extends BtreeNode {

    /**
     * Ctor.
     *
     * @param maxKeys     Max keys
     * @param maxChildren Max children
     * @param key         First key
     */
    BtnWithKey(final int maxKeys, final int maxChildren, final NodeKey key) {
        super(
                maxKeys + 1,
                maxChildren + 1
        );
        this.addKey(key);
    }

    /**
     * Ctor.
     *
     * @param maxKeys     Max keys
     * @param maxChildren Max children
     * @param key         Key for first key
     * @param value       Value for first key
     */
    BtnWithKey(final int maxKeys, final int maxChildren, final Integer key, final String value) {
        this(
                maxKeys,
                maxChildren,
                new NodeKey(key, value)
        );
    }
}
