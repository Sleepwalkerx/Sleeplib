package com.sleepwalker.sleeplib.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.SleepLib;
import com.sleepwalker.sleeplib.client.utils.SLGuiUtils;
import com.sleepwalker.sleeplib.client.utils.SpriteUtils;
import com.sleepwalker.sleeplib.client.widget.base.sprite.BaseSprite;
import com.sleepwalker.sleeplib.client.widget.base.sprite.ISprite;
import com.sleepwalker.sleeplib.client.widget.base.sprite.SubSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.gui.GuiUtils;

import javax.annotation.Nonnull;

public final class SLSprites {

    @Nonnull
    public static final ResourceLocation WIDGETS = new ResourceLocation(SleepLib.MODID, "textures/gui/widgets.png");

    public static final ISprite EMPTY = new BaseSprite(0, 0) {
        @Override
        public void render(@Nonnull MatrixStack ms, float posX, float posY, float zLevel, int width, int height, int state) { }
    };

    // SWITCHER_BUTTON - 18 x 18
    public static final ISprite RECT_1 = SpriteUtils.scaledStateWidth(WIDGETS, 0, 0, 2);
    public static final ISprite MODE_SWITCHER_CELL = new SubSprite(RECT_1, 18, 18);

    // WRAP_SCROLL_RECT_BACKGROUND
    public static final ISprite SCROLL_RECT_BACKGROUND = SpriteUtils.scaled(WIDGETS,32, 0, 3, 2, 2, 8);

    // WRAP_OBJECT_CELL - 18 x 18
    public static final ISprite RECT_2 = SpriteUtils.scaledStateWidth(WIDGETS,48, 0, 1);
    public static final ISprite WRAP_OBJECT_CELL = new SubSprite(RECT_2, 18, 18);

    // WRAP_BACKGROUND
    public static final ISprite RECT_3 = SpriteUtils.scaled(WIDGETS,80, 0, 2);

    // BUTTON
    public static final ISprite RECT_4 = SpriteUtils.scaledStateWidth(WIDGETS,96, 0, 2);

    // TEXT_FIELD
    public static final ISprite RECT_5 = SpriteUtils.scaled(WIDGETS,128, 0, 1);

    public static final ISprite RECT_6 = SpriteUtils.scaled(WIDGETS,144, 0, 1);

    public static final ISprite DISPOSABLE_BUTTON = SpriteUtils.scaledStateWidth(WIDGETS, 160, 0, 3, 6, 2, 2);

    // 24 x 24
    public static final ISprite RECT_9 = new BaseSprite() {
        @Override
        public void render(@Nonnull MatrixStack ms, float posX, float posY, float zLevel, int width, int height, int state) {
            Minecraft.getInstance().textureManager.bind(WIDGETS);
            //State
            GuiUtils.drawContinuousTexturedBox(
                ms,
                (int) posX, (int) posY,
                0 + ((state >> 2 & 3) * getWidth()), 16,
                width, height,
                getWidth(), getHeight(),
                2, 6, 2, 2,
                0
            );
            if((state & 2) == 2){
                //MouseOver
                SLGuiUtils.drawColorRect(ms.last().pose(), zLevel, (int) posX + 1, (int) posY + 20, 22, 3, 0x5D5D5F);
            }
            if((state & 1) == 1){
                //Selected
                GuiUtils.drawTexturedModalRect(ms, (int) posX + 9, (int) posY + 21, 87, 62, 6, 2, zLevel);
            }
        }
    };
    public static final ISprite OBJECT_CELL = new SubSprite(RECT_9, 24, 24);

    // SWITCHER - 12 x 12
    public static final ISprite RECT_7 = SpriteUtils.scaledStateWidth(WIDGETS,48, 16, 2, 4, 2, 2);
    public static final ISprite SWITCHER_BUTTON = new SubSprite(RECT_7, 12, 12);

    // ICON_CELL - 20 x 20
    public static final ISprite RECT_8 = SpriteUtils.scaledStateWidth(WIDGETS,80, 16, 2);
    public static final ISprite PROPERTY_CELL = new SubSprite(RECT_8, 20, 20);

    public static final ISprite VERTICAL_SLIDER = SpriteUtils.iconStateWidth(WIDGETS,0, 32, 6, 23);

    public static final ISprite SWITCHER_BACKGROUND = SpriteUtils.iconStateHeight(WIDGETS,24, 32, 24, 11);

    public static final ISprite HORIZONTAL_SLIDER = SpriteUtils.iconStateWidth(WIDGETS,48, 32, 32, 21);

    public static final ISprite BOOK_ICON = SpriteUtils.icon(WIDGETS,0, 55, 12, 12);
    public static final ISprite CHEST_ICON = SpriteUtils.icon(WIDGETS,12, 55, 12, 12);
    public static final ISprite PLANET_ICON = SpriteUtils.icon(WIDGETS,24, 55, 11, 11);
    public static final ISprite TURN_LEFT_ICON = SpriteUtils.icon(WIDGETS,35, 55, 13, 7);
    public static final ISprite TURN_RIGHT_ICON = SpriteUtils.icon(WIDGETS,48, 55, 13, 7);
    public static final ISprite LOCK_ICON = SpriteUtils.iconStateWidth(WIDGETS,61, 55, 7, 9);
    public static final ISprite DROP_ICON = SpriteUtils.iconStateHeight(WIDGETS,75, 55, 6, 4);
    public static final ISprite GREY_PLUS_ICON = SpriteUtils.icon(WIDGETS,81, 55, 6, 6);
    public static final ISprite GREY_MINUS_ICON = SpriteUtils.icon(WIDGETS,81, 62, 6, 2);
    public static final ISprite GREEN_PLUS_ICON = SpriteUtils.icon(WIDGETS,87, 55, 6, 6);
    public static final ISprite TRASH_ICON = SpriteUtils.icon(WIDGETS,93, 55, 6, 8);
    public static final ISprite CHECK_MARK_ICON = SpriteUtils.icon(WIDGETS,99, 55, 7, 7);
    public static final ISprite DUPLICATE_ICON = SpriteUtils.icon(WIDGETS,106, 55, 8, 8);
    public static final ISprite SAVE_ICON = SpriteUtils.icon(WIDGETS,114, 55, 6, 6);
    public static final ISprite CROSS_ICON = SpriteUtils.icon(WIDGETS,120, 55, 5, 5);
    public static final ISprite EDIT_ICON = SpriteUtils.icon(WIDGETS,125, 55, 6, 6);
    public static final ISprite OUT_ICON = SpriteUtils.icon(WIDGETS,131, 55, 8, 7);

    public static final ISprite FILE_ICON = SpriteUtils.icon(WIDGETS,0, 67, 16, 20);
    public static final ISprite FOLDER_ICON = SpriteUtils.icon(WIDGETS,16, 67, 22, 18);
}
