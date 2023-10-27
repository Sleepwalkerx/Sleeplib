package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.ins;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.CustomNode;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Delimited;

/**
 * An ins node containing text and other inline nodes as children.
 */
public class Ins extends CustomNode implements Delimited {

    private static final String DELIMITER = "++";

    @Override
    public String getOpeningDelimiter() {
        return DELIMITER;
    }

    @Override
    public String getClosingDelimiter() {
        return DELIMITER;
    }
}
