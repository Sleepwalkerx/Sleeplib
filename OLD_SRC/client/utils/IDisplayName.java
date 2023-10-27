package com.sleepwalker.sleeplib.client.utils;

import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IDisplayName {
    @OnlyIn(Dist.CLIENT)
    ITextComponent getDisplayName();
}
