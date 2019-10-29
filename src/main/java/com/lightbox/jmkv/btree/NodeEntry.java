package com.lightbox.jmkv.btree;

/**
 * Entry of Btree node.
 */
final class NodeEntry implements Comparable<Integer> {

    /**
     * Key of node.
     */
    final Integer key;

    /**
     * Value of node.
     */
    final String value;

    /**
     * Pointer to next value of node.
     */
    private NodeEntry next;

    /**
     * Ctor.
     *
     * @param key   Key
     * @param value Value
     */
    NodeEntry(final Integer key, final String value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    @Override
    public int compareTo(final Integer other) {
        return this.key.compareTo(other);
    }
}
