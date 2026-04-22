package net.sefacestudios.testmod.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.ai.behavior.GiveGiftToHero;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.sefacestudios.datagen_extras.data_maps.game_event.GameEventDataMap;
import net.sefacestudios.datagen_extras.data_maps.villager_profession.VillagerProfessionDataMap;
import net.sefacestudios.datagen_extras.provider.neoforge.GameEventMapProvider;
import net.sefacestudios.datagen_extras.provider.neoforge.VillagerProfessionDataMapProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModVillagerProfessionDataMapProvider extends VillagerProfessionDataMapProvider {
  public ModVillagerProfessionDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup);
  }

  @Override
  public void generate(HolderLookup.Provider registryLookup, Consumer<VillagerProfessionDataMap> consumer) {
    this.addRaidHeroGifts(VillagerProfession.ARMORER, BuiltInLootTables.ARMORER_GIFT);
  }
}
