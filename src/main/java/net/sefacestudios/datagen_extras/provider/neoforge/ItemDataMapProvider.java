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
import net.minecraft.world.item.Item;
import net.sefacestudios.datagen_extras.data_maps.item.CompostableDataMap;
import net.sefacestudios.datagen_extras.data_maps.item.FurnaceFuelsDataMap;
import net.sefacestudios.datagen_extras.data_maps.item.ItemDataMap;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class ItemDataMapProvider implements DataProvider {
  private final FabricPackOutput output;
  private final CompletableFuture<HolderLookup.Provider> registryLookup;
  private Consumer<ItemDataMap> consumer;

  private PackOutput.PathProvider pathResolver;

  public ItemDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    this.output = output;
    this.registryLookup = registryLookup;
  }

  public abstract void generate(HolderLookup.Provider registryLookup, Consumer<ItemDataMap> consumer);

  @NotNull
  @Override
  public CompletableFuture<?> run(@NotNull CachedOutput writer) {
    this.pathResolver = this.output.createPathProvider(PackOutput.Target.DATA_PACK, "data_maps/item");

    return this.registryLookup.thenCompose(lookup -> {
      final Set<ItemDataMap> dataMaps = Sets.newHashSet();
      this.consumer = dataMaps::add;

      this.generate(lookup, this.consumer);

      JsonObject compostablesRoot = new JsonObject();
      final JsonObject compostablesValues = new JsonObject();

      JsonObject fuelsRoot = new JsonObject();
      final JsonObject fuelsValues = new JsonObject();

      for (ItemDataMap dataMap : dataMaps) {
        String itemId = dataMap.item().getRegisteredName();
        JsonObject entry = dataMap.toJson();

        switch (dataMap) {
          case CompostableDataMap _ -> compostablesValues.add(itemId, entry);
          case FurnaceFuelsDataMap _ -> fuelsValues.add(itemId, entry);

          default -> throw new IllegalStateException("Unknown item data map type: " + dataMap);
        }
      }

      compostablesRoot.add("values", compostablesValues);
      fuelsRoot.add("values", fuelsValues);

      return CompletableFuture.allOf(
        DataProvider.saveStable(writer, compostablesRoot, getOutputPath("compostables")),
        DataProvider.saveStable(writer, fuelsRoot, getOutputPath("furnace_fuels"))
      );
    });
  }

  private Path getOutputPath(String fileName) {
    return pathResolver.json(Identifier.fromNamespaceAndPath("neoforge", fileName));
  }

  public void addCompostableItem(Item item, float chance, boolean canVillagerCompost) {
    this.consumer.accept(new CompostableDataMap(item.builtInRegistryHolder(), chance, canVillagerCompost));
  }

  public void addCompostableItem(Item item, float chance) {
    this.addCompostableItem(item, chance, false);
  }

  public void addFuelItem(Item item, int burnTime) {
    this.consumer.accept(new FurnaceFuelsDataMap(item.builtInRegistryHolder(), burnTime));
  }

  @NotNull
  @Override
  public String getName() {
    return "(NeoForge) Item Data Maps";
  }
}
