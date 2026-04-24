package net.sefacestudios.testmod.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.level.biome.Biomes;
import net.sefacestudios.datagen_extras.data_maps.worldgen.WorldgenDataMap;
import net.sefacestudios.datagen_extras.provider.neoforge.WorldgenDataMapProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModWorlgenDataMapProvider extends WorldgenDataMapProvider {
  public ModWorlgenDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup);
  }

  @Override
  public void generate(HolderLookup.Provider registryLookup, Consumer<WorldgenDataMap<?>> consumer) {
    this.addVillagerType(Biomes.JUNGLE, VillagerType.DESERT);
  }
}
