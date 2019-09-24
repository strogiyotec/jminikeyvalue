package com.lightbox.jmkv;

import java.util.Map;
import java.util.Objects;

/**
 * Entry which doesn't allow to change the state.
 *
 * @param <K> Key
 * @param <V> Value
 */
public final class ImmutableEntry<K, V> implements Map.Entry<K, V> {

    /**
     * Key.
     */
    private final K key;

    /**
     * Value.
     */
    private final V value;

    /**
     * Ctor.
     *
     * @param key   Key
     * @param value Value
     */
    public ImmutableEntry(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(final V unused) {
        throw new UnsupportedOperationException(
                "State of ImmutableEntry can't be changed"
        );
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ImmutableEntry<?, ?> that = (ImmutableEntry<?, ?>) obj;
        return Objects.equals(key, that.key)
                && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return String.format("key=%s,value=%s", this.key, this.value);
    }
}
