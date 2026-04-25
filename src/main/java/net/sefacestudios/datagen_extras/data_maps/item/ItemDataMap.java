package net.sefacestudios.datagen_extras.data_maps.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.sefacestudios.datagen_extras.data_maps.DataMap;
import org.jetbrains.annotations.NotNull;

public interface ItemDataMap extends DataMap {
  /**
   * The item object to be used in the data map.
   */
  @NotNull
  Item item();

  @NotNull
  @Override
  default String getStringifiedKey() {
    return BuiltInRegistries.ITEM.getKey(this.item()).toString();
  }

  /**
   * Get the item object from the ResourceKey.
   * @param resourceKey The item ResourceKey.
   */
  static Item getItemByResourceKey(ResourceKey<@NotNull Item> resourceKey) {
    return BuiltInRegistries.ITEM.getValueOrThrow(resourceKey);
  }
}
