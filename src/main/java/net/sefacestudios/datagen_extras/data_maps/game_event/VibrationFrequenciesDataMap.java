package net.sefacestudios.datagen_extras.data_maps.game_event;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public record VibrationFrequenciesDataMap(Holder<@NotNull GameEvent> gameEvent, int frequency) implements GameEventDataMap {
  public static Codec<VibrationFrequenciesDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      GameEvent.CODEC.fieldOf("game_event").forGetter(VibrationFrequenciesDataMap::gameEvent),
      Codec.intRange(1, 15).fieldOf("frequency").forGetter(VibrationFrequenciesDataMap::frequency)
    ).apply(instance, VibrationFrequenciesDataMap::new)
  );

  @Override
  public JsonObject toJson() {
    JsonObject obj = new JsonObject();
    if (!(this.frequency >= 1 && this.frequency <= 15)) {
      throw new IllegalStateException("The frequency range must be between 1 and 15. Frequency declared: " + this.frequency);
    }

    obj.addProperty("frequency", this.frequency);
    return obj;
  }
}
