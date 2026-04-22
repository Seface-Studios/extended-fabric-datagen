package net.sefacestudios.datagen_extras.data_maps.block;

import com.google.gson.JsonObject;
import net.minecraft.world.level.block.Block;

public interface BlockDataMap {
  Block block();
  JsonObject toJson();
}
