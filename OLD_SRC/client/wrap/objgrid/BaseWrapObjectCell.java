package com.sleepwalker.sleeplib.client.wrap.objgrid;

import com.sleepwalker.sleeplib.client.widget.base.button.SelectableButton;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.Set;

public abstract class BaseWrapObjectCell extends SelectableButton implements IWrapObjectCell {

    @Nonnull
    protected final String langNameCache;
    @Nonnull
    protected final String registryCache;
    @Nonnull
    protected final Set<String> tagCache;

    protected BaseWrapObjectCell(@Nonnull String registryCache, @Nonnull String langNameCache, @Nonnull Set<String> tagCache) {
        this.registryCache = registryCache;
        this.tagCache = tagCache;
        this.langNameCache = langNameCache;
    }

    @Override
    public boolean textMatches(@Nonnull String name) {
        return registryCache.contains(name) || langNameCache.contains(name.toLowerCase(Locale.ROOT));
    }

    @Override
    public boolean tagMatches(@Nonnull String name) {
        return tagCache.stream().anyMatch(str -> str.contains(name));
    }
}
