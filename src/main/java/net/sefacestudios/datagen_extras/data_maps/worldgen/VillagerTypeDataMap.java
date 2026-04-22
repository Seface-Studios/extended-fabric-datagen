package net.sefacestudios.datagen_extras.data_maps.worldgen;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public record VillagerTypeDataMap(Identifier key, Identifier villagerType) implements WorldgenDataMap<Identifier> {

  public static Codec<VillagerTypeDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      Identifier.CODEC.fieldOf("biome").forGetter(VillagerTypeDataMap::key),
      Identifier.CODEC.fieldOf("villager_type").forGetter(VillagerTypeDataMap::villagerType)
    ).apply(instance, VillagerTypeDataMap::new)
  );

  @Override
  public JsonObject toJson() {
    JsonObject obj = new JsonObject();
    obj.addProperty("villager_type", this.villagerType.toString());
    return obj;
  }
}
