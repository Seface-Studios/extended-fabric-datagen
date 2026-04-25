package net.sefacestudios.datagen_extras.provider.neoforge;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.level.biome.Biome;
import net.sefacestudios.datagen_extras.data_maps.worldgen.VillagerTypeDataMap;
import net.sefacestudios.datagen_extras.data_maps.worldgen.WorldgenDataMap;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public abstract class WorldgenDataMapProvider extends AbstractDataMapProvider<WorldgenDataMap<?>> {
  public WorldgenDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup, "worldgen");
  }

  public void addVillagerType(ResourceKey<@NotNull Biome> biome, ResourceKey<@NotNull VillagerType> villagerType) {
    this.consumer.accept(new VillagerTypeDataMap(biome, villagerType));
  }

  @NotNull
  @Override
  public String getName() {
    return "(NeoForge) Worldgen data maps";
  }
}
