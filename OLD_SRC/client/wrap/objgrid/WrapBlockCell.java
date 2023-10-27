package com.sleepwalker.sleeplib.client.wrap.objgrid;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.opengl.GL11;
import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.client.utils.SLGuiUtils;
import com.sleepwalker.sleeplib.client.widget.core.ITooltipRenderable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WrapBlockCell extends BaseWrapObjectCell implements ITooltipRenderable {

    @Nonnull
    protected final Block block;
    @Nonnull
    protected final ItemStack renderCache;
    protected final int id;

    public WrapBlockCell(@Nonnull Block block, int id) {
        super(
            block.getRegistryName().toString(),
            block.getName().getString().toLowerCase(Locale.ROOT),
            block.getTags().stream().map(ResourceLocation::toString).collect(Collectors.toSet())
        );

        this.block = block;
        this.id = id;

        renderCache = new ItemStack(block);
        selected = false;

        setSize(SLSprites.WRAP_OBJECT_CELL.getWidth(), SLSprites.WRAP_OBJECT_CELL.getHeight());
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int x, int y, float pt) {

        RenderSystem.disableDepthTest();
        SLSprites.WRAP_OBJECT_CELL.render(ms, posX, posY, isMouseFocused() ? 1 : 0);

        Minecraft.getInstance().getItemRenderer().renderGuiItem(renderCache, posX + 1, posY + 1);

        if(selected){
            SLSprites.CHECK_MARK_ICON.render(ms,
                posEndX - SLSprites.CHECK_MARK_ICON.getWidth() - 2,
                posEndY - SLSprites.CHECK_MARK_ICON.getHeight() - 2
            );
        }
    }

    @Override
    public void renderTooltips(@Nonnull MatrixStack ms, int mouseX, int mouseY, float pt) {

        boolean state = GL11.glIsEnabled(GL11.GL_SCISSOR_TEST);

        if(state){
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }

        SLGuiUtils.renderItemTooltip(ms, renderCache, mouseX, mouseY);

        if(state){
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
        }
    }

    @Nonnull
    public Block getBlock() {
        return block;
    }

    @Nonnull
    public static <T extends WrapBlockCell> Function<WrapObjectGrid<T>, Collection<T>> makeProviderListener(BiFunction<Block, Integer, T> constructor){

        return (wrapObjectGrid -> {

            Collection<T> collection = new ArrayList<>();

            switch (wrapObjectGrid.contentProvider()){

                case FORGE_REGISTRY:

                    AtomicInteger index = new AtomicInteger();
                    ForgeRegistries.BLOCKS.forEach(block1 -> {
                        if(block1.asItem() != Items.AIR){
                            collection.add(constructor.apply(block1, index.getAndIncrement()));
                        }
                    });

                    break;

                case INVENTORY:

                    if(Minecraft.getInstance().player != null) {

                        NonNullList<ItemStack> items = Minecraft.getInstance().player.inventory.items;

                        for(int i = 0; i < items.size(); i++){
                            if(items.get(i).getItem() instanceof BlockItem && !items.get(i).isEmpty()){
                                collection.add(constructor.apply(((BlockItem) items.get(i).getItem()).getBlock(), i));
                            }
                        }
                    }

                    break;
            }

            return collection;
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrapBlockCell that = (WrapBlockCell) o;
        return block == that.block;
    }

    @Override
    public int hashCode() {
        return Objects.hash(block);
    }

    @Override
    public int getSortedID() {
        return id;
    }
}
