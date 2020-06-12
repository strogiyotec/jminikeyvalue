package com.lightbox.jmkv.btree;

public final class Main {
    public static void main(String[] args) throws InterruptedException {
        final BtreeBB<Integer> tree = new BtreeBB<>(1);
        for (int i = 1; i <= 10; i++) {
            tree.add(i);
        }
        tree.remove(2);
        System.out.println(tree);
    }
}
