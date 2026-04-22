package net.sefacestudios.testmod.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Blocks;
import net.sefacestudios.datagen_extras.data_maps.block.BlockDataMap;
import net.sefacestudios.datagen_extras.provider.neoforge.BlockDataMapProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModBlockDataMapProvider extends BlockDataMapProvider {
  public ModBlockDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup);
  }

  @Override
  public void generate(HolderLookup.Provider registryLookup, Consumer<BlockDataMap> consumer) {
    this.addOxidizableBlock(Blocks.COPPER_BLOCK, Blocks.WEATHERED_COPPER);
    this.addOxidizableBlock(Blocks.WEATHERED_COPPER, Blocks.EXPOSED_COPPER);

    this.addWaxableBlock(Blocks.EXPOSED_COPPER, Blocks.WEATHERED_COPPER);
    this.addWaxableBlock(Blocks.WEATHERED_COPPER, Blocks.COPPER_BLOCK);
  }
}
