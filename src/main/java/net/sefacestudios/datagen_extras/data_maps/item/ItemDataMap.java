package net.sefacestudios.datagen_extras.data_maps.item;

import com.google.gson.JsonObject;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public interface ItemDataMap {
  Holder<@NotNull Item> item();
  JsonObject toJson();
}
