package net.sefacestudios.testmod.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.sefacestudios.datagen_extras.data_maps.CompostableDataMap;
import net.sefacestudios.datagen_extras.provider.neoforge.CompostablesDataMapProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModCompostablesDataMapProvider extends CompostablesDataMapProvider {
  public ModCompostablesDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup);
  }

  @Override
  public void generate(HolderLookup.Provider registryLookup, Consumer<CompostableDataMap> consumer) {
    this.addCompostable(Blocks.ACACIA_LOG.asItem(), 0.5F);
    this.addCompostable(Blocks.ACACIA_BUTTON.asItem(), 1.0F);
    this.addCompostable(Blocks.ACACIA_DOOR.asItem(), 0.25F, true);
  }
}
