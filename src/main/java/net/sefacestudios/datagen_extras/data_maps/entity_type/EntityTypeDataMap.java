package net.sefacestudios.datagen_extras.data_maps.entity_type;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.sefacestudios.datagen_extras.data_maps.DataMap;
import org.jetbrains.annotations.NotNull;

public interface EntityTypeDataMap extends DataMap {
  EntityType<?> entityType();

  @NotNull
  @Override
  default String getStringfiedKey() {
    return BuiltInRegistries.ENTITY_TYPE.getKey(this.entityType()).toString();
  }
}
