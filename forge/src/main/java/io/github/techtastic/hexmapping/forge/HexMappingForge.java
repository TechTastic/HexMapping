package io.github.techtastic.hexmapping.forge;

import at.petrak.hexcasting.common.lib.HexRegistries;
import dev.architectury.platform.forge.EventBuses;
import io.github.techtastic.hexmapping.HexMapping;
import io.github.techtastic.hexmapping.registry.HexMappingIotaTypeRegistry;
import io.github.techtastic.hexmapping.registry.HexMappingPatternRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import static io.github.techtastic.hexmapping.HexMapping.id;

/**
 * This is your loading entrypoint on forge, in case you need to initialize
 * something platform-specific.
 */
@Mod(HexMapping.MOD_ID)
public class HexMappingForge {
    public HexMappingForge() {
        // Submit our event bus to let architectury register our content on the right time
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(HexMapping.MOD_ID, bus);

        bus.addListener(HexMappingClientForge::init);
        bus.addListener(this::registerPatterns);

        HexMapping.init();
    }

    private void registerPatterns(RegisterEvent event) {
        HexMappingPatternRegistry.registryCallback((key, entry) -> event.register(HexRegistries.ACTION, id(key), () -> entry));
        HexMappingIotaTypeRegistry.registryCallback((key, entry) -> event.register(HexRegistries.IOTA_TYPE, key, () -> entry));
    }
}
