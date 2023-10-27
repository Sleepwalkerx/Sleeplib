package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Node;

public interface Renderer {

    /**
     * Render the tree of nodes to output.
     *
     * @param node the root node
     * @param output output for rendering
     */
    void render(Node node, Appendable output);

    /**
     * Render the tree of nodes to string.
     *
     * @param node the root node
     * @return the rendered string
     */
    String render(Node node);
}
