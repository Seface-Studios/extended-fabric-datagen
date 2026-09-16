package net.sefacestudios.datagen_extras.vanilla.loot;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamily;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Should not be used yet!
 */
@Deprecated
public abstract class ExtendedBlockLootSubProvider extends FabricBlockLootSubProvider {
  private static final Set<BlockFamily> SHOULD_NOT_GENERATE_LOOT_TABLE = new HashSet<>();

  public ExtendedBlockLootSubProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(packOutput, registriesFuture);
  }

  public void ignoreBlockFamilyLootTable(BlockFamily ...blockFamilies) {
    SHOULD_NOT_GENERATE_LOOT_TABLE.addAll(Arrays.asList(blockFamilies));
  }

  public void dropSelfBlockFamilyBlocks(Map<Block, BlockFamily> blockFamilies) {
    for (Map.Entry<Block, BlockFamily> entry : blockFamilies.entrySet()) {
      if (SHOULD_NOT_GENERATE_LOOT_TABLE.contains(entry.getValue())) continue;

      for (Map.Entry<BlockFamily.Variant, Block> variantEntry : entry.getValue().getVariants().entrySet()) {
        if (variantEntry.getKey() == BlockFamily.Variant.SLAB) {
          this.add(variantEntry.getValue(), this.createSlabItemTable(variantEntry.getValue()).setRandomSequence(ModelLocationUtils.getModelLocation(variantEntry.getValue())));
          continue;
        }

        this.dropSelf(variantEntry.getValue());
      }

      this.dropSelf(entry.getKey());
    }
  }

  /**
   * Drops 1x of tall plant.
   * @param tallPlant The tall plant block.
   */
  public LootTable.Builder createShearsOnlyDropForDoublePlant(Block tallPlant) {
    return this.createSinglePropConditionTable(tallPlant, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);
  }

  /**
   *
   * @param block
   * @param itemTag
   * @return
   */
  public LootTable.Builder createItemTagOnlyDrop(Block block, TagKey<@NotNull Item> itemTag) {
    return LootTable.lootTable().withPool(
      LootPool.lootPool()
        .setRolls(ConstantValue.exactly(1.0f))
        .when(MatchTool.toolMatches(net.minecraft.advancements.predicates.ItemPredicate.Builder.item().of(this.registries.lookupOrThrow(Registries.ITEM), itemTag)))
        .add(LootItem.lootTableItem(block))
    );
  }
}
