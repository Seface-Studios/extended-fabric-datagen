package net.sefacestudios.datagen_extras.data_maps.item;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public record FurnaceFuelsDataMap(Item item, int burnTime) implements ItemDataMap {

  public static Codec<FurnaceFuelsDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      ResourceKey.codec(Registries.ITEM)
        .xmap(ItemDataMap::getBlockByResourceKey, item -> item.builtInRegistryHolder().key())
        .fieldOf("item")
        .forGetter(FurnaceFuelsDataMap::item),
      Codec.INT.fieldOf("burn_time").forGetter(FurnaceFuelsDataMap::burnTime)
    ).apply(instance, FurnaceFuelsDataMap::new)
  );

  @NotNull
  @Override
  public JsonObject toJson() {
    JsonObject object = CODEC
      .encodeStart(JsonOps.INSTANCE, this)
      .getOrThrow(IllegalStateException::new)
      .getAsJsonObject();

    object.remove("item");

    return object;
  }

  @NotNull
  @Override
  public String getFileName() {
    return "furnace_fuels";
  }
}
