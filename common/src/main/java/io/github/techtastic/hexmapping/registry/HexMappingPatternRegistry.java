package io.github.techtastic.hexmapping.registry;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.HexRegistries;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.techtastic.hexmapping.casting.patterns.spells.OpCongrats;
import io.github.techtastic.hexmapping.casting.patterns.math.OpSignum;

import static io.github.techtastic.hexmapping.HexMapping.MOD_ID;

public class HexMappingPatternRegistry {
    private static final DeferredRegister<ActionRegistryEntry> ACTIONS = DeferredRegister.create(MOD_ID, HexRegistries.ACTION);

    public static RegistrySupplier<ActionRegistryEntry> CONGRATS =
            register(HexPattern.fromAngles("eed", HexDir.WEST), "congrats", new OpCongrats());
    public static RegistrySupplier<ActionRegistryEntry> SIGNUM =
            register(HexPattern.fromAngles("edd", HexDir.NORTH_WEST), "signum", new OpSignum());

    public static void init() {
        ACTIONS.register();
    }

    private static RegistrySupplier<ActionRegistryEntry> register(HexPattern pattern, String name, Action action) {
        return ACTIONS.register(name, () -> new ActionRegistryEntry(pattern, action));
    }
}
