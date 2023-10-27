package com.sleepwalker.sleeplib.test.screen;


import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.elementa.components.UIDrawable;
import com.sleepwalker.sleeplib.gg.essential.elementa.ElementaVersion;
import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent;
import com.sleepwalker.sleeplib.gg.essential.elementa.WindowScreen;
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIBlock;
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIImage;
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIText;
import com.sleepwalker.sleeplib.gg.essential.elementa.components.input.UITextInput;
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.CenterConstraint;
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.PixelConstraint;
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.RelativeConstraint;
import kotlin.Unit;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

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

    UIComponent image = new UIDrawable(SLSprites.DISPOSABLE_BUTTON_ENABLED)
        .setX(new CenterConstraint())
        .setY(new CenterConstraint())
        .setWidth(new PixelConstraint(50))
        .setHeight(new PixelConstraint(50))
        .setChildOf(getWindow());

    UIComponent block = new UIBlock(Color.BLUE)
        .setX(new PixelConstraint(20))
        .setY(new PixelConstraint(20))
        .setWidth(new PixelConstraint(80))
        .setHeight(new PixelConstraint(21))
        .setChildOf(getWindow());

    UIComponent text = new UIText("Тест")
        .setX(new PixelConstraint(50))
        .setY(new PixelConstraint(80))
        .setChildOf(getWindow());

    public TestoScreen() {
        super(ElementaVersion.V3);

        //UITextInput textInput = new UITextInput();

        /*img.onMouseEnterRunnable(() -> {
            AnimatingConstraints anim = img.makeAnimation();
            anim.setWidthAnimation(Animations.IN_OUT_BOUNCE, 5f, new PixelConstraint(60));
            anim.setHeightAnimation(Animations.IN_OUT_BOUNCE, 5f, new PixelConstraint(60));
            img.animateTo(anim);
        });

        ScrollComponent.DefaultScrollBar bar = new ScrollComponent.DefaultScrollBar(false);
        bar.setChildOf(bg);
        scroll.setVerticalScrollBarComponent(bar, false);

        for(int i = 0; i < 10; i++){

            UIComponent cell = new UIBlock(Color.GRAY)
                .setX(new CenterConstraint())
                .setY(new SiblingConstraint(2f))
                .setHeight(new PixelConstraint(10))
                .setWidth(new PixelConstraint(40));

            scroll.addChild(cell);
        }*/

        UITextInput input = (UITextInput) new UITextInput("112323123")
            .setWidth(new RelativeConstraint())
            .setHeight(new RelativeConstraint())
            .setChildOf(block);

        input.onUpdate(s -> {
            getWindow().removeChild(image);
            URL url;
            try {
                url = new URL(s);
            }
            catch (MalformedURLException e) {
                return Unit.INSTANCE;
            }
            image = UIImage.ofURL(url)
                .setX(new CenterConstraint())
                .setY(new CenterConstraint())
                .setWidth(new PixelConstraint(50))
                .setHeight(new PixelConstraint(50))
                .setChildOf(getWindow());
            return Unit.INSTANCE;
        });

        block.onMouseClickConsumer(e -> input.grabWindowFocus());

        //fon.onMouseClickConsumer(e -> input.grabWindowFocus());
        //input.setText("123");
        //input.setColor(new ConstantColorConstraint());
        //input.removeEffect(ScissorEffect.class);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
