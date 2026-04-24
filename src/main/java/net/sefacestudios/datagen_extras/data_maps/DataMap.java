package net.sefacestudios.datagen_extras.data_maps;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public interface DataMap {
  @NotNull
  JsonObject toJson();

  @NotNull
  String getFileName();

  @NotNull
  String getStringfiedKey();
}
