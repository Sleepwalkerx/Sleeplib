package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.gfm.strikethrough;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.CustomNode;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Delimited;

/**
 * A strikethrough node containing text and other inline nodes nodes as children.
 */
public class Strikethrough extends CustomNode implements Delimited {

    private static final String DELIMITER = "~~";

    @Override
    public String getOpeningDelimiter() {
        return DELIMITER;
    }

    @Override
    public String getClosingDelimiter() {
        return DELIMITER;
    }
}
