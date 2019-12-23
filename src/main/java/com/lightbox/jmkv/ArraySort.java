package com.lightbox.jmkv;

import java.util.Arrays;

/**
 * Sort array.
 */
public interface ArraySort {

    /**
     * Sort given array.
     *
     * @param array     Array to sort
     * @param indexFrom From index
     * @param indexTo   To index
     */
    void sort(Comparable[] array, int indexFrom, int indexTo);

    /**
     * Default sort impl.
     * Sort using Arrays.sort method
     */
    final class DefaultSort implements ArraySort {

        @Override
        public void sort(
                final Comparable[] array,
                final int indexFrom,
                final int indexTo
        ) {
            Arrays.sort(array, indexFrom, indexTo);
        }
    }
}
