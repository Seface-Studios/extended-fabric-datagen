package net.sefacestudios.testmod.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import net.sefacestudios.datagen_extras.data_maps.entity_type.EntityTypeDataMap;
import net.sefacestudios.datagen_extras.data_maps.item.ItemDataMap;
import net.sefacestudios.datagen_extras.provider.neoforge.EntityTypeDataMapProvider;
import net.sefacestudios.datagen_extras.provider.neoforge.ItemDataMapProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModItemDataMapProvider extends ItemDataMapProvider {
  public ModItemDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup);
  }

  @Override
  public void generate(HolderLookup.Provider registryLookup, Consumer<ItemDataMap> consumer) {
    this.addCompostableItem(Blocks.ACACIA_LOG.asItem(), 0.5f);
    this.addCompostableItem(Blocks.ACACIA_DOOR.asItem(), 1.0f);
    this.addCompostableItem(Blocks.ACACIA_BUTTON.asItem(), 1.0f, true);

    this.addFuelItem(Blocks.STONE.asItem(), 1000);
  }
}
