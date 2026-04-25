package net.sefacestudios.datagen_extras.data_maps.entity_type;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.sefacestudios.datagen_extras.data_maps.DataMap;
import org.jetbrains.annotations.NotNull;

public interface EntityTypeDataMap extends DataMap {
  /**
   * The entity type object to be used in the data map.
   */
  @NotNull
  EntityType<?> entityType();

  @NotNull
  @Override
  default String getStringifiedKey() {
    return BuiltInRegistries.ENTITY_TYPE.getKey(this.entityType()).toString();
  }
}
