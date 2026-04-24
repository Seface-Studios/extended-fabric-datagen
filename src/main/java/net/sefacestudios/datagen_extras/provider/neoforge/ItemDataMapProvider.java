package net.sefacestudios.datagen_extras.provider.neoforge;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.sefacestudios.datagen_extras.data_maps.item.CompostableDataMap;
import net.sefacestudios.datagen_extras.data_maps.item.FurnaceFuelsDataMap;
import net.sefacestudios.datagen_extras.data_maps.item.ItemDataMap;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public abstract class ItemDataMapProvider extends AbstractDataMapProvider<ItemDataMap> {
  public ItemDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup, "item");
  }

  public void addCompostableItem(Item item, float chance, boolean canVillagerCompost) {
    this.consumer.accept(new CompostableDataMap(item, chance, canVillagerCompost));
  }

  public void addCompostableItem(Item item, float chance) {
    this.addCompostableItem(item, chance, false);
  }

  public void addFuelItem(Item item, int burnTime) {
    this.consumer.accept(new FurnaceFuelsDataMap(item, burnTime));
  }

  @NotNull
  @Override
  public String getName() {
    return "(NeoForge) Item data maps";
  }
}
