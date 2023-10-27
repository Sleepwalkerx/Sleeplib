package com.sleepwalker.sleeplib.registries;

import com.sleepwalker.sleeplib.ui.property.Properties;
import com.sleepwalker.sleeplib.ui.property.Property;
import com.sleepwalker.sleeplib.ui.xml.*;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public final class SLRegistries {

    @Nonnull public static final Supplier<IForgeRegistry<LayoutSerializer>> LAYOUT_SERIALIZERS = LayoutSerializers.REGISTER.makeRegistry("layout_serializers", RegistryBuilder::new);
    @Nonnull public static final Supplier<IForgeRegistry<WidgetSerializer<?>>> WIDGET_SERIALIZERS = WidgetSerializers.REGISTER.makeRegistry("widget_serializers", RegistryBuilder::new);
    @Nonnull public static final Supplier<IForgeRegistry<DrawableSerializer>> DRAWABLE_SERIALIZERS = DrawableSerializers.REGISTER.makeRegistry("drawable_serializers", RegistryBuilder::new);
    @Nonnull public static final Supplier<IForgeRegistry<ShapeSerializer>> SHAPE_SERIALIZERS = ShapeSerializers.REGISTER.makeRegistry("shape_serializers", RegistryBuilder::new);

    @Nonnull public static final Supplier<IForgeRegistry<Property<?, ?>>> PROPERTIES = Properties.REGISTER.makeRegistry("properties", RegistryBuilder::new);

    /*private static void onValidateProperty(@Nonnull IForgeRegistryInternal<Property<?, ?>> owner, RegistryManager stage, int id, @Nonnull ResourceLocation key, Property<?, ?> obj){
        String path = key.getPath();
        for(int i = 0; i < path.length(); i++){
            char c = path.charAt(i);
            if(!(c == '_' || c == '-' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9')){
                throw new IllegalStateException("Invalid registration path name. Allowed: [a-z0-9_-]");
            }
        }
    }*/
}
