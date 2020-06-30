package com.lightbox.jmkv.queue;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public final class StaticHeap {

    /**
     * TODO: move to test
     */
    public static void main(String[] args) {
        final StaticHeap staticHeap = new StaticHeap(12);
        IntStream.of(
                15, 13, 9, 5, 12, 8, 7, 4, 0, 6, 2, 1
        ).forEach(staticHeap::add);
        while (!staticHeap.empty()) {
            System.out.println(staticHeap.pool());
        }
    }

    private final int[] elements;

    private int size;

    public StaticHeap(final int size) {
        this.elements = new int[size];
        this.size = 0;
    }

    boolean empty() {
        return this.size == 0;
    }

    int add(final int number) {
        this.replace(this.size++, number);
        return number;
    }

    int pool() {
        if (this.size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }
        final int max = this.elements[0];
        this.elements[0] = this.elements[--this.size];
        this.maxHeapify(0);
        return max;
    }

    private void replace(int position, final int value) {
        this.elements[position] = value;
        while (position > 1 && this.elements[this.parent(position)] < this.elements[position]) {
            final int parentPosition = this.parent(position);
            final int temp = this.elements[position];
            this.elements[position] = this.elements[parentPosition];
            this.elements[parentPosition] = temp;
            position = parentPosition;
        }
    }

    private void maxHeapify(final int currentPosition) {
        final int left = this.left(currentPosition);
        final int right = this.right(currentPosition);
        final int largestPosition;
        if (left < this.size && this.elements[left] > this.elements[currentPosition] && this.elements[left] > this.elements[right]) {
            largestPosition = left;
        } else if (right < this.size && this.elements[right] > this.elements[currentPosition] && this.elements[right] > this.elements[left]) {
            largestPosition = right;
        } else {
            largestPosition = currentPosition;
        }
        if (largestPosition != currentPosition) {
            final int temp = this.elements[currentPosition];
            this.elements[currentPosition] = this.elements[largestPosition];
            this.elements[largestPosition] = temp;
            this.maxHeapify(largestPosition);
        }
    }

    private int parent(final int position) {
        return (position - 1) / 2;
    }

    private int left(final int position) {
        return (position * 2) + 1;
    }

    private int right(final int position) {
        return (position * 2) + 2;
    }

}
