package net.sefacestudios.datagen_extras.data_maps.entity_type;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public record AcceptableVillagerDistancesDataMap(EntityType<?> entityType, float acceptableDistance) implements EntityTypeDataMap {
  public static Codec<AcceptableVillagerDistancesDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      EntityType.CODEC.fieldOf("entity_type").forGetter(AcceptableVillagerDistancesDataMap::entityType),
      Codec.FLOAT.fieldOf("acceptable_villager_distance").forGetter(AcceptableVillagerDistancesDataMap::acceptableDistance)
    ).apply(instance, AcceptableVillagerDistancesDataMap::new)
  );

  @Override
  public @NotNull JsonObject toJson() {
    JsonObject object = CODEC
      .encodeStart(JsonOps.INSTANCE, this)
      .getOrThrow(IllegalStateException::new)
      .getAsJsonObject();

    object.remove("entity_type");

    return object;
  }

  @Override
  public @NotNull String getFileName() {
    return "acceptable_villager_distance";
  }
}
