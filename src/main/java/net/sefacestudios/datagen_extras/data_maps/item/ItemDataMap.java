package net.sefacestudios.datagen_extras.data_maps.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.sefacestudios.datagen_extras.data_maps.DataMap;
import org.jetbrains.annotations.NotNull;

public interface ItemDataMap extends DataMap {
  @NotNull
  Item item();

  @NotNull
  @Override
  default String getStringfiedKey() {
    return BuiltInRegistries.ITEM.getKey(this.item()).toString();
  }

  static Item getBlockByResourceKey(ResourceKey<@NotNull Item> resourceKey) {
    return BuiltInRegistries.ITEM.getValueOrThrow(resourceKey);
  }
}
