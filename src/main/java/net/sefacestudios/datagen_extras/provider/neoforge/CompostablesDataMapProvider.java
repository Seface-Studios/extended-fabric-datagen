package net.sefacestudios.datagen_extras.provider.neoforge;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.sefacestudios.datagen_extras.data_maps.CompostableDataMap;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class CompostablesDataMapProvider implements DataProvider {
  private final FabricPackOutput output;
  private final CompletableFuture<HolderLookup.Provider> registryLookup;
  private Consumer<CompostableDataMap> consumer;

  private PackOutput.PathProvider pathResolver;

  public CompostablesDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    this.output = output;
    this.registryLookup = registryLookup;
  }

  public abstract void generate(HolderLookup.Provider registryLookup, Consumer<CompostableDataMap> consumer);

  @NotNull
  @Override
  public CompletableFuture<?> run(@NotNull CachedOutput writer) {
    this.pathResolver = this.output.createPathProvider(PackOutput.Target.DATA_PACK, "data_maps/item");

    return this.registryLookup.thenCompose(lookup -> {
      final Set<CompostableDataMap> compostables = Sets.newHashSet();

      this.consumer = compostables::add;

      this.generate(lookup, this.consumer);

      final JsonObject root = new JsonObject();
      final JsonObject values = new JsonObject();

      for (CompostableDataMap modifier : compostables) {
        Holder<Item> item = modifier.item();
        float chance = modifier.chance();
        boolean canVillagerCompost = modifier.canVillagerCompost();

        JsonObject entry = new JsonObject();
        entry.addProperty("chance", chance);

        if (canVillagerCompost) {
          entry.addProperty("can_villager_compost", canVillagerCompost);
        }

        values.add(item.getRegisteredName(), entry);
      }

      root.add("values", values);

      return DataProvider.saveStable(writer, root, getOutputPath());
    });
  }

  private Path getOutputPath() {
    return pathResolver.json(Identifier.fromNamespaceAndPath("neoforge", "compostables"));
  }

  /**
   * Registers an item as compostable item.
   * @param item The item to be compostable
   * @param chance The chance between 0 and 1 that this item has to success compostable.
   */
  public void addCompostable(Item item, float chance) {
    this.addCompostable(item, chance, false);
  }

  /**
   * Registers an item as compostable item.
   * @param item The item to be compostable
   * @param chance The chance between 0 and 1 that this item has to success compostable.
   * @param canVillagerCompost Whether farmer villagers can compost this item.
   */
  public void addCompostable(Item item, float chance, boolean canVillagerCompost) {
    this.consumer.accept(new CompostableDataMap(item.builtInRegistryHolder(), chance, canVillagerCompost));
  }

  @NotNull
  @Override
  public String getName() {
    return "(NeoForge) Data Maps/Compostables";
  }
}
