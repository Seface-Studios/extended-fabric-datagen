package net.sefacestudios.datagen_extras.data_maps.worldgen;

import net.sefacestudios.datagen_extras.data_maps.DataMap;
import org.jetbrains.annotations.NotNull;

public interface WorldgenDataMap<T> extends DataMap {
  @NotNull
  T key();
}
