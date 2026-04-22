package net.sefacestudios.datagen_extras.data_maps.entity_type;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.EntityType;

public record AcceptableVillagerDistancesDataMap(EntityType<?> entityType, float acceptableDistance) implements EntityTypeDataMap {
  public static Codec<AcceptableVillagerDistancesDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      EntityType.CODEC.fieldOf("entity_type").forGetter(AcceptableVillagerDistancesDataMap::entityType),
      Codec.FLOAT.fieldOf("acceptable_villager_distance").forGetter(AcceptableVillagerDistancesDataMap::acceptableDistance)
    ).apply(instance, AcceptableVillagerDistancesDataMap::new)
  );

  @Override
  public JsonObject toJson() {
    JsonObject obj = new JsonObject();
    obj.addProperty("acceptable_villager_distance", this.acceptableDistance);
    return obj;
  }
}
