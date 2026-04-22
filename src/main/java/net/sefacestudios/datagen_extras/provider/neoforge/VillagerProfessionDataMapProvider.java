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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.sefacestudios.datagen_extras.data_maps.game_event.GameEventDataMap;
import net.sefacestudios.datagen_extras.data_maps.game_event.VibrationFrequenciesDataMap;
import net.sefacestudios.datagen_extras.data_maps.villager_profession.RaidHeroGiftsDataMap;
import net.sefacestudios.datagen_extras.data_maps.villager_profession.VillagerProfessionDataMap;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class VillagerProfessionDataMapProvider implements DataProvider {
  private final FabricPackOutput output;
  private final CompletableFuture<HolderLookup.Provider> registryLookup;
  private Consumer<VillagerProfessionDataMap> consumer;

  private PackOutput.PathProvider pathResolver;

  public VillagerProfessionDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    this.output = output;
    this.registryLookup = registryLookup;
  }

  public abstract void generate(HolderLookup.Provider registryLookup, Consumer<VillagerProfessionDataMap> consumer);

  @NotNull
  @Override
  public CompletableFuture<?> run(@NotNull CachedOutput writer) {
    this.pathResolver = this.output.createPathProvider(PackOutput.Target.DATA_PACK, "data_maps/villager_profession");

    return this.registryLookup.thenCompose(lookup -> {
      final Set<VillagerProfessionDataMap> dataMaps = Sets.newHashSet();
      this.consumer = dataMaps::add;

      this.generate(lookup, this.consumer);

      JsonObject giftsRoot = new JsonObject();
      final JsonObject giftsValues = new JsonObject();

      for (VillagerProfessionDataMap dataMap : dataMaps) {
        String professionId = BuiltInRegistries.VILLAGER_PROFESSION.getKey(dataMap.profession()).toString();
        JsonObject entry = dataMap.toJson();

        switch (dataMap) {
          case RaidHeroGiftsDataMap _ -> giftsValues.add(professionId, entry);

          default -> throw new IllegalStateException("Unknown villager profession data map type: " + dataMap);
        }
      }

      giftsRoot.add("values", giftsValues);

      return CompletableFuture.allOf(
        DataProvider.saveStable(writer, giftsRoot, getOutputPath("raid_hero_gifts"))
      );
    });
  }

  private Path getOutputPath(String fileName) {
    return pathResolver.json(Identifier.fromNamespaceAndPath("neoforge", fileName));
  }

  public void addRaidHeroGifts(ResourceKey<@NotNull VillagerProfession> profession, ResourceKey<@NotNull LootTable> lootTable) {
    this.consumer.accept(new RaidHeroGiftsDataMap(
      BuiltInRegistries.VILLAGER_PROFESSION.getValue(profession),
      lootTable.identifier()
    ));
  }

  @NotNull
  @Override
  public String getName() {
    return "(NeoForge) Villager Profession Data Maps";
  }
}
