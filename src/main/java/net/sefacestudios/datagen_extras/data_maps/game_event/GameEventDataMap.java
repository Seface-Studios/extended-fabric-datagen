package net.sefacestudios.datagen_extras.data_maps.game_event;

import com.google.gson.JsonObject;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public interface GameEventDataMap {
  Holder<@NotNull GameEvent> gameEvent();
  JsonObject toJson();
}
