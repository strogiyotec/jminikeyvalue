package com.lightbox.jmkv.btree;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final NodeKey nodeKey = (NodeKey) obj;
        return Objects.equals(this.key, nodeKey.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.key);
    }
}
