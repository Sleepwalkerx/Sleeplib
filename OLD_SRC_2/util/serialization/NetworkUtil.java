/*
 * Copyright (c)
 *
 * All rights reserved
 * Any copying and reproduction of the text, including partial and in any form,
 * without the written permission of the copyright holder is prohibited.
 * Any use of photos, audio, video and graphic materials, including partial,
 * without the written permission of the copyright holder is prohibited.
 *
 * © Салтыков Д.А., 2022
 */

package com.sleepwalker.sleeplib.util.serialization;

import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class NetworkUtil {

    public static void writeDecimal(@Nonnull PacketBuffer pf, @Nonnull BigDecimal bigDecimal){
        pf.writeByteArray(bigDecimal.unscaledValue().toByteArray());
        pf.writeVarInt(bigDecimal.scale());
    }

    @Nonnull
    public static BigDecimal readDecimal(@Nonnull PacketBuffer pf, @Nonnull MathContext context){
        BigInteger value = new BigInteger(pf.readByteArray());
        return new BigDecimal(value, pf.readVarInt(), context);
    }

    public static <K, V> void writeMap(
        @Nonnull PacketBuffer buffer, @Nonnull Map<K, V> map,
        @Nonnull BiConsumer<PacketBuffer, K> keyConsumer, @Nonnull BiConsumer<PacketBuffer, V> valueConsumer
    ) {
        buffer.writeVarInt(map.size());
        map.forEach((k, v) -> {
            keyConsumer.accept(buffer, k);
            valueConsumer.accept(buffer, v);
        });
    }

    @Nonnull
    public static <K, V> Map<K, V> readMap(
        @Nonnull PacketBuffer buffer,
        @Nonnull Function<PacketBuffer, K> keySupplier,
        @Nonnull Function<PacketBuffer, V> valueSupplier
    ) {

        int size = buffer.readVarInt();

        Map<K, V> map = new HashMap<>(size);

        for(int i = 0; i < size; i++){
            map.put(keySupplier.apply(buffer), valueSupplier.apply(buffer));
        }

        return map;
    }

    @Nonnull
    public static <K, V, M extends Map<K, V>> M readMap(
        @Nonnull PacketBuffer buffer,
        @Nonnull M map,
        @Nonnull Function<PacketBuffer, K> keySupplier,
        @Nonnull Function<PacketBuffer, V> valueSupplier
    ) {

        int size = buffer.readVarInt();

        for(int i = 0; i < size; i++){
            map.put(keySupplier.apply(buffer), valueSupplier.apply(buffer));
        }

        return map;
    }

    public static <V> void writeCollection(
        @Nonnull PacketBuffer buffer, @Nonnull Collection<V> collection,
        @Nonnull BiConsumer<PacketBuffer, V> valueConsumer
    ) {
        buffer.writeVarInt(collection.size());
        collection.forEach(v -> valueConsumer.accept(buffer, v));
    }

    @Nonnull
    public static <V, C extends Collection<V>> C readCollection(
        @Nonnull PacketBuffer buffer,
        @Nonnull C collection,
        @Nonnull Function<PacketBuffer, V> valueSupplier
    ) {
        int size = buffer.readVarInt();
        for(int i = 0; i < size; i++){
            collection.add(valueSupplier.apply(buffer));
        }

        return collection;
    }

    @Nonnull
    public static <V> List<V> readArrayList(@Nonnull PacketBuffer buffer, @Nonnull Function<PacketBuffer, V> valueSupplier) {
        return readCollection(buffer, new ArrayList<>(), valueSupplier);
    }

    @Nonnull
    public static <V> Set<V> readHashSet(@Nonnull PacketBuffer buffer, @Nonnull Function<PacketBuffer, V> valueSupplier) {
        return readCollection(buffer, new HashSet<>(), valueSupplier);
    }

    public static void writeStrArray(@Nonnull PacketBuffer pf, @Nonnull String[] array){
        pf.writeVarInt(array.length);
        for(String str : array){
            pf.writeUtf(str);
        }
    }

    @Nonnull
    public static String[] readStrArray(@Nonnull PacketBuffer pf){
        int len = pf.readVarInt();
        String[] array = new String[len];
        for(int i = 0; i < len; i++){
            array[i] = pf.readUtf();
        }
        return array;
    }
}
