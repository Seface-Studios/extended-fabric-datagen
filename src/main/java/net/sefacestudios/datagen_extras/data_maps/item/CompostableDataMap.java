package net.sefacestudios.datagen_extras.data_maps.item;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public record CompostableDataMap(Holder<@NotNull Item> item, float chance, boolean canVillagerCompost) implements ItemDataMap {
  public static Codec<CompostableDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      Item.CODEC.fieldOf("item").forGetter(CompostableDataMap::item),
      Codec.floatRange(0, 1).fieldOf("chance").forGetter(CompostableDataMap::chance),
      Codec.BOOL.optionalFieldOf("can_villager_compost", false).forGetter(CompostableDataMap::canVillagerCompost)
    ).apply(instance, CompostableDataMap::new)
  );

  @Override
  public JsonObject toJson() {
    JsonObject obj = new JsonObject();
    obj.addProperty("weight", this.chance);

    if (this.canVillagerCompost) {
      obj.addProperty("can_villager_compost", true);
    }

    return obj;
  }
}
