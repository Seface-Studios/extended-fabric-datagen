package net.sefacestudios.datagen_extras.data_maps.worldgen;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public record VillagerTypeDataMap(ResourceKey<@NotNull Biome> key, ResourceKey<@NotNull VillagerType> villagerType) implements WorldgenDataMap<ResourceKey<@NotNull Biome>> {

  public static Codec<VillagerTypeDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      ResourceKey.codec(Registries.BIOME).fieldOf("biome").forGetter(VillagerTypeDataMap::key),
      ResourceKey.codec(Registries.VILLAGER_TYPE).fieldOf("villager_type").forGetter(VillagerTypeDataMap::villagerType)
    ).apply(instance, VillagerTypeDataMap::new)
  );

  @NotNull
  @Override
  public JsonObject toJson() {
    JsonObject object = CODEC
      .encodeStart(JsonOps.INSTANCE, this)
      .getOrThrow(IllegalStateException::new)
      .getAsJsonObject();

    object.remove("biome");

    return object;
  }

  @NotNull
  @Override
  public String getFileName() {
    return "biome/villager_types";
  }

  @NotNull
  @Override
  public String getStringifiedKey() {
    return this.key().identifier().toString();
  }
}
