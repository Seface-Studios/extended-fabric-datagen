package net.sefacestudios.datagen_extras.data_maps.worldgen;

import com.google.gson.JsonObject;
import net.minecraft.world.level.block.Block;

public interface WorldgenDataMap<T>{
  T key();
  JsonObject toJson();
}
