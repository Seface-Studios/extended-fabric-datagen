package net.sefacestudios.datagen_extras.data_maps.game_event;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.gameevent.GameEvent;
import net.sefacestudios.datagen_extras.data_maps.DataMap;
import org.jetbrains.annotations.NotNull;

public interface GameEventDataMap extends DataMap {
  /**
   * The game event object to be used in the data map.
   */
  @NotNull
  GameEvent gameEvent();

  @NotNull
  @Override
  default String getStringifiedKey() {
    return BuiltInRegistries.GAME_EVENT.getKey(this.gameEvent()).toString();
  }

  /**
   * Get the game event object from the ResourceKey.
   * @param resourceKey The game event ResourceKey.
   */
  static GameEvent getGameEventByResourceKey(ResourceKey<@NotNull GameEvent> resourceKey) {
    return BuiltInRegistries.GAME_EVENT.getValueOrThrow(resourceKey);
  }
}
