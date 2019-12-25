package com.lightbox.jmkv.btree;

/**
 * Entry of Btree node.
 */
final class NodeKey implements Comparable<NodeKey> {

    /**
     * Key of node.
     */
    final Integer key;

    /**
     * Value of node.
     */
    final String value;

    /**
     * Ctor.
     *
     * @param key   Key
     * @param value Value
     */
    NodeKey(final Integer key, final String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(final NodeKey other) {
        return this.key.compareTo(other.key);
    }
}
