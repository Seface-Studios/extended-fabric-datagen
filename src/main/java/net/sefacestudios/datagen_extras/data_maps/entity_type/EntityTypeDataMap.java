package net.sefacestudios.datagen_extras.data_maps.entity_type;

import com.google.gson.JsonObject;
import net.minecraft.world.entity.EntityType;

public interface EntityTypeDataMap {
  EntityType<?> entityType();
  JsonObject toJson();
}
