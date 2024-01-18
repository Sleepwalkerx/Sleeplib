package com.sleepwalker.sleeplib.test.screen;


import com.sleepwalker.sleeplib.gg.essential.elementa.ElementaVersion;
import com.sleepwalker.sleeplib.gg.essential.elementa.WindowScreen;
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIBlock;
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.CenterConstraint;
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.PixelConstraint;
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.RelativeConstraint;
import com.sleepwalker.sleeplib.gg.essential.elementa.markdown.MarkdownComponent;

import java.awt.*;

public class TestoScreen extends WindowScreen {

    /*UIComponent img = UIImage.ofMCResource(new ResourceLocation(SleepLib.MODID, "textures/gui/widgets.png"))
        .withDrawer(new NineSliceDrawer(0, 0, 16, 16, 2, 2, 2, 2))
        .setX(new PixelConstraint(10))
        .setY(new PixelConstraint(10))
        .setWidth(new PixelConstraint(32))
        .setHeight(new PixelConstraint(32))
        .setChildOf(getWindow());

    UIComponent bg = new UIBlock(Color.WHITE)
        .setX(new CenterConstraint())
        .setY(new CenterConstraint())
        .setWidth(new PixelConstraint(50))
        .setHeight(new PixelConstraint(60))
        .setChildOf(getWindow());

    ScrollComponent scroll = (ScrollComponent) new ScrollComponent()
        .setWidth(new RelativeConstraint(1f))
        .setHeight(new RelativeConstraint(1f))
        .setChildOf(bg);*/

    public TestoScreen() {
        super(ElementaVersion.V3);

        String text = "# SleepWarp\n" +
            "Новый самописный мод на варпы является ПОЛНОЙ заменой плагина - MyWarp.\n" +
            "\n" +
            "> Что необходимо протестировать:\n" +
            "- всю работоспособность мода, что всё корректно работает\n" +
            "- наличие нужного функционала, которым всем пользовались в MyWarp. То есть, чтобы мод был ничем не хуже плагина, что как и вплагине есть всё что нужно игрокам. \n" +
            "Мог что-то забыть добавить/реализовать, по этому нужно помочь понять чего не хватает в моде.\n" +
            "\n" +
            "> Все команды мода, которые необходимо протестировать:\n" +
            "- /warptop - показывает топ 10 варпов по посещениям\n" +
            "- /warps - выводит список публичных варпов\n" +
            "- /warps accesses - показывает все доступные приватные варпы\n" +
            "- /warps [page] - выводит список публичных варпов нужной страницы\n" +
            "- /warp";

        new UIBlock(new Color(0x94FFFFFF, true))
            .setX(new CenterConstraint())
            .setY(new CenterConstraint())
            .setWidth(new PixelConstraint(300))
            .setHeight(new PixelConstraint(210))
            .setChildOf(getWindow())
            .addChild(new MarkdownComponent(text)
                .setWidth(new RelativeConstraint())
                .setHeight(new RelativeConstraint())
            );
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
