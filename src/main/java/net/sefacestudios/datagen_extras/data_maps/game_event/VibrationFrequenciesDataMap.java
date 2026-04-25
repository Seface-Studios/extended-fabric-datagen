package net.sefacestudios.datagen_extras.data_maps.game_event;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public record VibrationFrequenciesDataMap(GameEvent gameEvent, int frequency) implements GameEventDataMap {
  public static Codec<VibrationFrequenciesDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      ResourceKey.codec(Registries.GAME_EVENT)
        .xmap(
          GameEventDataMap::getGameEventByResourceKey,
          gameEvent -> BuiltInRegistries.GAME_EVENT
            .getResourceKey(gameEvent)
            .orElseThrow()
        )
        .fieldOf("game_event")
        .forGetter(VibrationFrequenciesDataMap::gameEvent),
      Codec.intRange(1, 15).fieldOf("frequency").forGetter(VibrationFrequenciesDataMap::frequency)
    ).apply(instance, VibrationFrequenciesDataMap::new)
  );

  @NotNull
  @Override
  public JsonObject toJson() {
    JsonObject object = CODEC
      .encodeStart(JsonOps.INSTANCE, this)
      .getOrThrow(IllegalStateException::new)
      .getAsJsonObject();

    object.remove("game_event");

    return object;
  }

  @Override
  public @NotNull String getFileName() {
    return "vibration_frequencies";
  }
}
