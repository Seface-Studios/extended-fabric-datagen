package net.sefacestudios.datagen_extras.provider.neoforge;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import net.sefacestudios.datagen_extras.data_maps.block.BlockDataMap;
import net.sefacestudios.datagen_extras.data_maps.block.OxidizablesBlockDataMap;
import net.sefacestudios.datagen_extras.data_maps.block.StrippablesDataMap;
import net.sefacestudios.datagen_extras.data_maps.block.WaxablesDataMap;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public abstract class BlockDataMapProvider extends AbstractDataMapProvider<BlockDataMap> {
  public BlockDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup, "block");
  }

  public void addOxidizableBlock(Block block, Block nextOxidationStage) {
    this.consumer.accept(new OxidizablesBlockDataMap(block, nextOxidationStage));
  }

  public void addWaxableBlock(Block block, Block waxed) {
    this.consumer.accept(new WaxablesDataMap(block, waxed));
  }

  public void addStrippableBlock(Block block, Block stripped) {
    this.consumer.accept(new StrippablesDataMap(block, stripped));
  }

  @NotNull
  @Override
  public String getName() {
    return "(NeoForge) Block data maps";
  }
}
