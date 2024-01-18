package com.sleepwalker.sleeplib.util.serialization;

import net.minecraft.nbt.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.function.Supplier;

public final class NBTUtil {

    public static void putEnum(@Nonnull CompoundNBT compoundNBT, @Nonnull String name, @Nonnull Enum<?> value){
        compoundNBT.putString(name, value.name());
    }

    public static <T extends Enum<T>> T getEnum(
        @Nonnull CompoundNBT compoundNBT,
        @Nonnull String name,
        T defaultValue
    ){
        if(compoundNBT.contains(name, Constants.NBT.TAG_STRING)){
            try {
                return Enum.valueOf(defaultValue.getDeclaringClass(), defaultValue.name());
            }
            catch (IllegalArgumentException ignore){
            }
        }
        return defaultValue;
    }

    public static <T extends IForgeRegistryEntry<T>> void putRegistryEntry(
        @Nonnull CompoundNBT compoundNBT,
        @Nonnull String name,
        @Nonnull T value
    ){
        compoundNBT.putString(name, Objects.requireNonNull(value.getRegistryName()).toString());
    }

    public static <T extends IForgeRegistryEntry<T>> T getRegistryEntry(
        @Nonnull CompoundNBT compoundNBT,
        @Nonnull String name,
        @Nonnull Supplier<IForgeRegistry<T>> registry
    ){
        return getRegistryEntry(compoundNBT, name, registry.get());
    }

    public static <T extends IForgeRegistryEntry<T>> T getRegistryEntry(
        @Nonnull CompoundNBT compoundNBT,
        @Nonnull String name,
        @Nonnull IForgeRegistry<T> registry
    ){
        return getRegistryEntry(compoundNBT, name, registry, registry.getDefaultKey());
    }

    public static <T extends IForgeRegistryEntry<T>> T getRegistryEntry(
        @Nonnull CompoundNBT compoundNBT,
        @Nonnull String name,
        @Nonnull IForgeRegistry<T> registry,
        @Nullable ResourceLocation defKey
    ){
        if(compoundNBT.contains(name, Constants.NBT.TAG_STRING)){
            ResourceLocation loc = ResourceLocation.tryCreate(compoundNBT.getString(name));

            if(loc != null){
                T type = registry.getValue(loc);
                if(type != null){
                    return type;
                }
            }
        }

        return defKey != null ? registry.getValue(defKey) : null;
    }

    public static void putBigInteger(@Nonnull CompoundNBT compoundNBT, @Nonnull String name, @Nonnull BigInteger value){
        compoundNBT.putString(name, value.toString());
    }

    public static BigInteger getBigInteger(@Nonnull CompoundNBT compoundNBT, @Nonnull String name, BigInteger def){
        if(compoundNBT.contains(name)){
            INBT nbt = compoundNBT.get(name);
            if(nbt instanceof NumberNBT){
                return BigInteger.valueOf(((NumberNBT) nbt).getLong());
            }
            else if(nbt instanceof StringNBT) {
                return new BigInteger(nbt.getString());
            }
        }
        return def;
    }

    public static BigInteger getBigInteger(@Nonnull CompoundNBT compoundNBT, @Nonnull String name){
        return getBigInteger(compoundNBT, name, null);
    }

    public static void putBigDecimal(@Nonnull CompoundNBT compoundNBT, @Nonnull String name, @Nonnull BigDecimal value){
        compoundNBT.putString(name, value.toString());
    }

    public static BigDecimal getBigDecimal(@Nonnull CompoundNBT compoundNBT, @Nonnull String name, BigDecimal def){
        if(compoundNBT.contains(name)){
            INBT nbt = compoundNBT.get(name);
            if(nbt instanceof NumberNBT){
                return BigDecimal.valueOf(((NumberNBT) nbt).getDouble());
            }
            else if(nbt instanceof StringNBT) {
                return new BigDecimal(nbt.getString());
            }
        }
        return def;
    }

    public static BigDecimal getBigDecimal(@Nonnull CompoundNBT compoundNBT, @Nonnull String name){
        return getBigDecimal(compoundNBT, name, null);
    }

    public static void putColor(@Nonnull CompoundNBT compoundNBT, @Nonnull String name, @Nonnull Color value){
        compoundNBT.putString(name, "#" + Long.toHexString(value.getAlpha() == 255 ? value.getRGB() & 0xFFFFFF : value.getRGB()));
    }

    public static Color getColor(@Nonnull CompoundNBT compoundNBT, @Nonnull String name, Color def){
        if(compoundNBT.contains(name)){
            INBT nbt = compoundNBT.get(name);
            if(nbt instanceof StringNBT){
                String val = nbt.getString();
                if(val.startsWith("#")){
                    val = val.substring(1);
                    try {
                        long i = Long.parseLong(val, 16);
                        return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF, (i >> 24) & 0xFF);
                    }
                    catch (NumberFormatException ignore){}
                }
                return Color.decode(val);
            }
            else if(nbt instanceof NumberNBT){
                try {
                    long i = ((NumberNBT) nbt).getLong();
                    return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF, (i >> 24) & 0xFF);
                }
                catch (NumberFormatException ignore){}
            }
        }

        return def;
    }

    public static Color getColor(@Nonnull CompoundNBT compoundNBT, @Nonnull String name){
        return getColor(compoundNBT, name, null);
    }

    public static void putBlockPos(@Nonnull CompoundNBT compoundNBT, @Nonnull String name, @Nonnull BlockPos value){
        compoundNBT.putIntArray(name, new int[]{value.getX(), value.getY(), value.getZ()});
    }

    public static BlockPos getBlockPos(@Nonnull CompoundNBT compoundNBT, @Nonnull String name, BlockPos blockPos){
        if(compoundNBT.contains(name)){
            INBT list = compoundNBT.get(name);
            if(list instanceof ListNBT){
                ListNBT listNBT = (ListNBT) list;
                if(listNBT.size() == 3){
                    return new BlockPos(listNBT.getInt(0), listNBT.getInt(1), listNBT.getInt(2));
                }
            }
            else if(list instanceof IntArrayNBT){
                int[] array = compoundNBT.getIntArray(name);
                if(array.length == 3){
                    return new BlockPos(array[0], array[1], array[2]);
                }
            }
            else if(list instanceof LongArrayNBT){
                long[] array = compoundNBT.getLongArray(name);
                if(array.length == 3){
                    return new BlockPos(array[0], array[1], array[2]);
                }
            }
        }
        return blockPos;
    }

    public static BlockPos getBlockPos(@Nonnull CompoundNBT compoundNBT, @Nonnull String name){
        return getBlockPos(compoundNBT, name, null);
    }
}
