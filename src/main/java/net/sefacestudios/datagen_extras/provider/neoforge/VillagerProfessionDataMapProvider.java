package net.sefacestudios.datagen_extras.provider.neoforge;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootTable;
import net.sefacestudios.datagen_extras.data_maps.villager_profession.RaidHeroGiftsDataMap;
import net.sefacestudios.datagen_extras.data_maps.villager_profession.VillagerProfessionDataMap;
import net.sefacestudios.datagen_extras.data_maps.worldgen.VillagerTypeDataMap;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public abstract class VillagerProfessionDataMapProvider extends AbstractDataMapProvider<VillagerProfessionDataMap> {
  public VillagerProfessionDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup, "villager_profession");
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
    return "(NeoForge) Villager Profession data maps";
  }
}
