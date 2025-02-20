package io.github.techtastic.hexmapping.registry;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import io.github.techtastic.hexmapping.api.casting.iota.MapIota;
import io.github.techtastic.hexmapping.api.casting.iota.MarkerIota;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static io.github.techtastic.hexmapping.HexMapping.id;

public class HexMappingIotaTypeRegistry {
    public static Map<ResourceLocation, IotaType<?>> TYPES = new HashMap<>();

    public static void init() {
        register("marker", MarkerIota.Companion.getTYPE());
        register("map", MapIota.Companion.getTYPE());
    }

    public static void registryCallback(BiConsumer<ResourceLocation, IotaType<?>> callback) {
        TYPES.forEach(callback);
    }

    private static <U extends Iota, T extends IotaType<U>> T register(String name, T type) {
        IotaType<?> old = TYPES.put(id(name), type);
        if (old != null) {
            throw new IllegalArgumentException("Typo? Duplicate id " + name);
        }
        return type;
    }
}
