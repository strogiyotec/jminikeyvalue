package com.lightbox.jmkv;

public final class Successor extends TreeNodeEnvelope {
    public Successor(final TreeNode origin) {
        super(
                successor(
                        origin.right()
                )
        );
    }

    private static TreeNode successor(TreeNode successor) {
        while (successor != null && successor.left() != null) {
            successor = successor.left();
        }
        return successor;
    }
}
