package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Node;

public interface PostProcessor {

    /**
     * @param node the node to post-process
     * @return the result of post-processing, may be a modified {@code node} argument
     */
    Node process(Node node);

}
