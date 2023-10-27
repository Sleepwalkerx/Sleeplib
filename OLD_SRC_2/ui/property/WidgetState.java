package com.sleepwalker.sleeplib.ui.property;

import javax.annotation.Nonnull;

public interface WidgetState {

    @Nonnull String getRegistryName();
    boolean getDefaultActivity();

    @Nonnull
    static WidgetState create(@Nonnull String registryName){
        return new Impl(registryName);
    }

    class Impl implements WidgetState {

        @Nonnull private final String registryName;

        public Impl(@Nonnull String registryName) {
            this.registryName = registryName;
        }

        @Nonnull
        @Override
        public String getRegistryName() {
            return registryName;
        }

        @Override
        public boolean getDefaultActivity() {
            return false;
        }
    }
}
