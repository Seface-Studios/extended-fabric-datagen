package net.sefacestudios.datagen_extras.data_maps.item;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public record CompostableDataMap(Item item, float chance, boolean canVillagerCompost) implements ItemDataMap {

  public static Codec<CompostableDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      ResourceKey.codec(Registries.ITEM)
        .xmap(ItemDataMap::getBlockByResourceKey, item -> item.builtInRegistryHolder().key())
        .fieldOf("item")
        .forGetter(CompostableDataMap::item),
      Codec.floatRange(0, 1).fieldOf("chance").forGetter(CompostableDataMap::chance),
      Codec.BOOL.optionalFieldOf("can_villager_compost", false).forGetter(CompostableDataMap::canVillagerCompost)
    ).apply(instance, CompostableDataMap::new)
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
    return "compostables";
  }
}
