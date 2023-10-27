package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.gfm.strikethrough.internal;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.gfm.strikethrough.Strikethrough;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Node;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.NodeRenderer;

import java.util.Collections;
import java.util.Set;

abstract class StrikethroughNodeRenderer implements NodeRenderer {

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return Collections.<Class<? extends Node>>singleton(Strikethrough.class);
    }
}
