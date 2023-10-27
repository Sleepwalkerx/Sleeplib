package com.sleepwalker.sleeplib.client.wrap.objgrid;

import com.sleepwalker.sleeplib.client.widget.core.ISelectable;

import javax.annotation.Nonnull;

public interface IWrapObjectCell extends ISelectable {

    int getSortedID();

    boolean textMatches(@Nonnull String name);

    boolean tagMatches(@Nonnull String name);
}
