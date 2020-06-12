package com.lightbox.jmkv;

/**
 * Base cache interface.
 */
public interface Cache {

    /**
     * Clear cache.
     *
     * @return True if successfully
     */
    boolean clear();

    /**
     * Get value by key.
     *
     * @param key Key
     * @return Value
     */
    String get(Integer key);

    /**
     * Put key value pair.
     *
     * @param key   Key
     * @param value Value
     * @return Value
     */
    String put(Integer key, String value);

    /**
     * Delete value by key.
     *
     * @param key Key
     * @return Value
     */
    String delete(Integer key);

    /**
     * Check that value with given key is present.
     *
     * @param key Key
     * @return True if value is present
     */
    boolean exists(Integer key);

    /**
     * Cache size.
     *
     * @return Size
     */
    int size();
}
