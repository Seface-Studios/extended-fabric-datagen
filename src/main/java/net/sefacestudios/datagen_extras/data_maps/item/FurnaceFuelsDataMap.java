package net.sefacestudios.datagen_extras.data_maps.item;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public record FurnaceFuelsDataMap(Holder<@NotNull Item> item, int burnTime) implements ItemDataMap {
  public static Codec<FurnaceFuelsDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      Item.CODEC.fieldOf("item").forGetter(FurnaceFuelsDataMap::item),
      Codec.INT.fieldOf("burn_time").forGetter(FurnaceFuelsDataMap::burnTime)
    ).apply(instance, FurnaceFuelsDataMap::new)
  );

  @Override
  public JsonObject toJson() {
    JsonObject obj = new JsonObject();
    obj.addProperty("burn_time", this.burnTime);
    return obj;
  }
}
