package net.sefacestudios.datagen_extras.data_maps.entity_type;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public record MonsterRoomMobsDataMap(EntityType<?> entityType, int weight) implements EntityTypeDataMap {
  public static Codec<MonsterRoomMobsDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      EntityType.CODEC.fieldOf("entity_type").forGetter(MonsterRoomMobsDataMap::entityType),
      Codec.INT.fieldOf("weight").forGetter(MonsterRoomMobsDataMap::weight)
    ).apply(instance, MonsterRoomMobsDataMap::new)
  );

  @NotNull
  @Override
  public JsonObject toJson() {
    JsonObject object = CODEC
      .encodeStart(JsonOps.INSTANCE, this)
      .getOrThrow(IllegalStateException::new)
      .getAsJsonObject();

    object.remove("entity_type");

    return object;
  }

  @NotNull
  @Override
  public String getFileName() {
    return "monster_room_mobs";
  }
}
