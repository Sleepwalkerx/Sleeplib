package com.sleepwalker.sleeplib.client.wrap.objgrid;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.client.utils.SLGuiUtils;
import com.sleepwalker.sleeplib.client.widget.core.ITooltipRenderable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WrapItemCell extends BaseWrapObjectCell implements ITooltipRenderable {

    @Nonnull
    protected final ItemStack itemStack;
    protected final int id;

    public WrapItemCell(@Nonnull ItemStack itemStack, int id) {
        super(
            itemStack.getItem().getRegistryName().toString(),
            itemStack.getDisplayName().getString().toLowerCase(Locale.ROOT),
            itemStack.getItem().getTags().stream().map(ResourceLocation::toString).collect(Collectors.toSet())
        );

        this.itemStack = itemStack;
        this.id = id;

        selected = false;

        setSize(SLSprites.WRAP_OBJECT_CELL.getWidth(), SLSprites.WRAP_OBJECT_CELL.getHeight());
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int x, int y, float pt) {

        RenderSystem.disableDepthTest();
        SLSprites.WRAP_OBJECT_CELL.render(ms, posX, posY, isMouseFocused() ? 1 : 0);

        Minecraft.getInstance().getItemRenderer().renderGuiItem(itemStack, posX + 1, posY + 1);

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

        SLGuiUtils.renderItemTooltip(ms, itemStack, mouseX, mouseY);

        if(state){
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
        }
    }

    @Nonnull
    public static <T extends WrapItemCell> Function<WrapObjectGrid<T>, Collection<T>> makeProviderListener(BiFunction<ItemStack, Integer, T> constructor){

        return (wrapObjectGrid -> {

            Collection<T> collection = new ArrayList<>();

            switch (wrapObjectGrid.contentProvider()){

                case FORGE_REGISTRY:

                    for(ItemGroup itemGroup : ItemGroup.TABS){

                        if(itemGroup == ItemGroup.TAB_SEARCH || itemGroup == ItemGroup.TAB_HOTBAR){
                            continue;
                        }

                        try {

                            NonNullList<ItemStack> itemStacks = NonNullList.create();
                            itemGroup.fillItemList(itemStacks);

                            for(ItemStack stack : itemStacks){

                                if(stack.getItem() != Items.AIR){
                                    collection.add(constructor.apply(stack, Item.getId(stack.getItem())));
                                }
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    break;

                case INVENTORY:

                    if(Minecraft.getInstance().player != null) {

                        NonNullList<ItemStack> items = Minecraft.getInstance().player.inventory.items;

                        for(int i = 0; i < items.size(); i++){
                            if(!items.get(i).isEmpty()){
                                collection.add(constructor.apply(items.get(i), i));
                            }
                        }
                    }

                    break;
            }

            return collection;
        });
    }

    @Nonnull
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrapItemCell that = (WrapItemCell) o;
        return Objects.equals(itemStack, that.itemStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemStack);
    }

    @Override
    public int getSortedID() {
        return id;
    }
}
