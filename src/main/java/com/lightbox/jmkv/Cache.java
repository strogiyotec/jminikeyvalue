package com.lightbox.jmkv;

/**
 * Base cache interface.
 */
public interface Cache {

    /**
     * Get value by key.
     *
     * @param key Key
     * @return Value
     */
    byte[] get(byte[] key);

    /**
     * Put key value pair.
     *
     * @param key   Key
     * @param value Value
     * @return Value
     */
    byte[] put(byte[] key, byte[] value);

    /**
     * Delete value by key.
     *
     * @param key Key
     * @return Value
     */
    byte[] delete(byte[] key);

    /**
     * Check that value with given key is present.
     *
     * @param key Key
     * @return True if value is present
     */
    boolean exists(byte[] key);
}
