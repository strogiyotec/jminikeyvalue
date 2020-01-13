package com.lightbox.jmkv.btree;

/**
 * Create new btree node from given parent.
 * Move keys and children from parent to new node
 * using range
 */
final class SplitBtn extends BtreeNode {

    /**
     * Create new BtreeNode.
     * Move all keys from fromIndex to toIndex to new node
     * Move all children from fromIndex to toIndex inclusive to new node
     *
     * @param parent    Reference to parent
     * @param fromIndex Index from
     * @param toIndex   Index to
     */
    SplitBtn(
            final BtreeNode parent,
            final int fromIndex,
            final int toIndex
    ) {
        super(parent.keys(), parent.children());
        for (int i = fromIndex; i < toIndex; i++) {
            this.addKey(parent.key(i));
        }
        if (parent.hasChildren()) {
            for (int i = fromIndex; i <= toIndex; i++) {
                this.addChild(parent.child(i));
            }
        }
    }

}
