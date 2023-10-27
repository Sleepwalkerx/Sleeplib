package com.sleepwalker.sleeplib.gg.essential.universal;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.util.ResourceLocation;
//#if MC>10809
import net.minecraft.util.SoundEvent;
//#endif

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;

class PositionedSoundRecordFactory {
    //#if MC>10809
    public static SimpleSound makeRecord(SoundEvent event, float volume, float pitch) {
        return SimpleSound.master(event, pitch, volume);
    }
    //#else
    //$$ private static final MethodHandle constructor;
    //$$ static {
    //$$     MethodHandle locatedConstructor;
    //$$     try {
    //$$         Constructor<PositionedSoundRecord> ctor = PositionedSoundRecord.class.getDeclaredConstructor(ResourceLocation.class, float.class, float.class, boolean.class, int.class, ISound.AttenuationType.class, float.class, float.class, float.class);
    //$$         ctor.setAccessible(true);
    //$$         locatedConstructor = MethodHandles.lookup().unreflectConstructor(ctor);
    //$$     } catch (Throwable e) {
    //$$         e.printStackTrace();
    //$$         locatedConstructor = null;
    //$$     }
    //$$     constructor = locatedConstructor;
    //$$ }
    //$$ public static PositionedSoundRecord makeRecord(ResourceLocation event, float volume, float pitch) {
    //$$     if (constructor == null) return null;
    //$$     try {
    //$$         return (PositionedSoundRecord) constructor.invokeExact(event, volume, pitch, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
    //$$     } catch (Throwable throwable) {
    //$$         throwable.printStackTrace();
    //$$     }
    //$$     return null;
    //$$ }
    //#endif
}
