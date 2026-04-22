package net.sefacestudios.datagen_extras.provider.neoforge;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.storage.loot.LootTable;
import net.sefacestudios.datagen_extras.data_maps.villager_profession.RaidHeroGiftsDataMap;
import net.sefacestudios.datagen_extras.data_maps.villager_profession.VillagerProfessionDataMap;
import net.sefacestudios.datagen_extras.data_maps.worldgen.VillagerTypeDataMap;
import net.sefacestudios.datagen_extras.data_maps.worldgen.WorldgenDataMap;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class WorldgenDataMapProvider implements DataProvider {
  private final FabricPackOutput output;
  private final CompletableFuture<HolderLookup.Provider> registryLookup;
  private Consumer<WorldgenDataMap<?>> consumer;

  private PackOutput.PathProvider pathResolver;

  public WorldgenDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    this.output = output;
    this.registryLookup = registryLookup;
  }

  public abstract void generate(HolderLookup.Provider registryLookup, Consumer<WorldgenDataMap<?>> consumer);

  @NotNull
  @Override
  public CompletableFuture<?> run(@NotNull CachedOutput writer) {
    this.pathResolver = this.output.createPathProvider(PackOutput.Target.DATA_PACK, "data_maps/worldgen");

    return this.registryLookup.thenCompose(lookup -> {
      final Set<WorldgenDataMap<?>> dataMaps = Sets.newHashSet();
      this.consumer = dataMaps::add;

      this.generate(lookup, this.consumer);

      JsonObject giftsRoot = new JsonObject();
      final JsonObject giftsValues = new JsonObject();

      for (WorldgenDataMap<?> dataMap : dataMaps) {
        JsonObject entry = dataMap.toJson();

        switch (dataMap) {
          case VillagerTypeDataMap _ -> {
            Identifier key = (Identifier) dataMap.key();
            giftsValues.add(key.toString(), entry);
          }

          default -> throw new IllegalStateException("Unknown villager profession data map type: " + dataMap);
        }
      }

      giftsRoot.add("values", giftsValues);

      return CompletableFuture.allOf(
        DataProvider.saveStable(writer, giftsRoot, getOutputPath("biome/villager_types"))
      );
    });
  }

  private Path getOutputPath(String fileName) {
    return pathResolver.json(Identifier.fromNamespaceAndPath("neoforge", fileName));
  }

  public void addVillagerType(ResourceKey<@NotNull Biome> biome, ResourceKey<@NotNull VillagerType> villagerType) {
    this.consumer.accept(new VillagerTypeDataMap(biome.identifier(), villagerType.identifier()));
  }

  @NotNull
  @Override
  public String getName() {
    return "(NeoForge) Worldgen Data Maps";
  }
}
