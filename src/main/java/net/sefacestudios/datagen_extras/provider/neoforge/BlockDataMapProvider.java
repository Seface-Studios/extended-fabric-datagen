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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.sefacestudios.datagen_extras.data_maps.block.BlockDataMap;
import net.sefacestudios.datagen_extras.data_maps.block.OxidizablesBlockDataMap;
import net.sefacestudios.datagen_extras.data_maps.block.WaxablesDataMap;
import net.sefacestudios.datagen_extras.data_maps.entity_type.AcceptableVillagerDistancesDataMap;
import net.sefacestudios.datagen_extras.data_maps.entity_type.EntityTypeDataMap;
import net.sefacestudios.datagen_extras.data_maps.entity_type.MonsterRoomMobsDataMap;
import net.sefacestudios.datagen_extras.data_maps.entity_type.ParrotImitationsDataMap;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class BlockDataMapProvider implements DataProvider {
  private final FabricPackOutput output;
  private final CompletableFuture<HolderLookup.Provider> registryLookup;
  private Consumer<BlockDataMap> consumer;

  private PackOutput.PathProvider pathResolver;

  public BlockDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    this.output = output;
    this.registryLookup = registryLookup;
  }

  public abstract void generate(HolderLookup.Provider registryLookup, Consumer<BlockDataMap> consumer);

  @NotNull
  @Override
  public CompletableFuture<?> run(@NotNull CachedOutput writer) {
    this.pathResolver = this.output.createPathProvider(PackOutput.Target.DATA_PACK, "data_maps/block");

    return this.registryLookup.thenCompose(lookup -> {
      final Set<BlockDataMap> dataMaps = Sets.newHashSet();
      this.consumer = dataMaps::add;

      this.generate(lookup, this.consumer);

      JsonObject oxidizablesRoot = new JsonObject();
      final JsonObject oxidizablesValues = new JsonObject();

      JsonObject waxedRoot = new JsonObject();
      final JsonObject waxedValues = new JsonObject();

      for (BlockDataMap dataMap : dataMaps) {
        String entityTypeId = BuiltInRegistries.BLOCK.getKey(dataMap.block()).toString();
        JsonObject entry = dataMap.toJson();

        switch (dataMap) {
          case OxidizablesBlockDataMap _ -> oxidizablesValues.add(entityTypeId, entry);
          case WaxablesDataMap _ -> waxedValues.add(entityTypeId, entry);

          default -> throw new IllegalStateException("Unknown block data map type: " + dataMap);
        }
      }

      oxidizablesRoot.add("values", oxidizablesValues);
      waxedRoot.add("values", waxedValues);

      return CompletableFuture.allOf(
        DataProvider.saveStable(writer, oxidizablesRoot, getOutputPath("oxidizables")),
        DataProvider.saveStable(writer, waxedRoot, getOutputPath("waxables"))
      );
    });
  }

  private Path getOutputPath(String fileName) {
    return pathResolver.json(Identifier.fromNamespaceAndPath("neoforge", fileName));
  }

  public void addOxidizableBlock(Block block, Block nextOxidationStage) {
    this.consumer.accept(new OxidizablesBlockDataMap(block, nextOxidationStage));
  }

  public void addWaxableBlock(Block block, Block waxed) {
    this.consumer.accept(new WaxablesDataMap(block, waxed));
  }

  @NotNull
  @Override
  public String getName() {
    return "(NeoForge) Block Data Maps";
  }
}
