package net.sefacestudios.datagen_extras.data_maps.block;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.sefacestudios.datagen_extras.data_maps.DataMap;
import org.jetbrains.annotations.NotNull;

public interface BlockDataMap extends DataMap {
  @NotNull
  Block block();

  @NotNull
  @Override
  default String getStringfiedKey() {
    return BuiltInRegistries.BLOCK.getKey(this.block()).toString();
  }

  static Block getBlockByResourceKey(ResourceKey<@NotNull Block> resourceKey) {
    return BuiltInRegistries.BLOCK.getValueOrThrow(resourceKey);
  }
}
