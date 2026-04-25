package net.sefacestudios.datagen_extras.data_maps;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public interface DataMap {
  /**
   * Converts the current data into JsonObject.
   * @return The JsonObject of parsed data.
   */
  @NotNull
  JsonObject toJson();

  /**
   * Get the file name to be used for this data map.
   */
  @NotNull
  String getFileName();

  /**
   * Get the stringified version of JsonObject key.
   */
  @NotNull
  String getStringifiedKey();
}
