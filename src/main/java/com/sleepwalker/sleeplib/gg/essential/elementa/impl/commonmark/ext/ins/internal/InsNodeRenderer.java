package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.ins.internal;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Node;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.NodeRenderer;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.ins.Ins;

import java.util.Collections;
import java.util.Set;

abstract class InsNodeRenderer implements NodeRenderer {

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return Collections.<Class<? extends Node>>singleton(Ins.class);
    }
}
