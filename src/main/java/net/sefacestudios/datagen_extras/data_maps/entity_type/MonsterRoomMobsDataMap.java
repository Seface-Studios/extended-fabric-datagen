package net.sefacestudios.datagen_extras.data_maps.entity_type;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.EntityType;

public record MonsterRoomMobsDataMap(EntityType<?> entityType, int weight) implements EntityTypeDataMap {
  public static Codec<MonsterRoomMobsDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      EntityType.CODEC.fieldOf("entity_type").forGetter(MonsterRoomMobsDataMap::entityType),
      Codec.INT.fieldOf("weight").forGetter(MonsterRoomMobsDataMap::weight)
    ).apply(instance, MonsterRoomMobsDataMap::new)
  );

  @Override
  public JsonObject toJson() {
    JsonObject obj = new JsonObject();
    obj.addProperty("weight", this.weight);
    return obj;
  }
}
