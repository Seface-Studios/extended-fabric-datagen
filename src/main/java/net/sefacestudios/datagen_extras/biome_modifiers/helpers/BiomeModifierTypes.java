package net.sefacestudios.datagen_extras.biome_modifiers.helpers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

public enum BiomeModifierTypes {
  NONE(0 ,"none"),
  ADD_FEATURE(1 ,"add_features"),
  REMOVE_FEATURE(2 ,"remove_features"),
  ADD_SPAWNS(3 ,"add_spawns"),
  REMOVE_SPAWNS(4 ,"remove_spawns"),
  ADD_SPAWN_COSTS(5 ,"add_spawn_costs", true),
  REMOVE_SPAWN_COSTS(6 ,"remove_spawn_costs", true),
  ADD_CARVERS(7 ,"add_carvers", true),
  REMOVE_CARVERS(8 ,"remove_carvers", true);

  public static final Codec<BiomeModifierTypes> CODEC = Codec.STRING.comapFlatMap(
    path -> {
      for (BiomeModifierTypes t : values()) {
        if (t.path.equals(path)) {
          return DataResult.success(t);
        }
      }
      return DataResult.error(() -> "Unknown BiomeModifierTypes: " + path);
    },
    BiomeModifierTypes::getPath
  );

  private final int index;
  private final String path;
  private final boolean neoForgeOnly;

  BiomeModifierTypes(int i, String path) {
    this(i, path, false);
  }

  BiomeModifierTypes(int i, String path, boolean neoForgeOnly) {
    this.index = i;
    this.path = path;
    this.neoForgeOnly = neoForgeOnly;
  }

  public String getPath() {
    return this.path;
  }

  public boolean isNeoForgeOnly() {
    return this.neoForgeOnly;
  }
}
