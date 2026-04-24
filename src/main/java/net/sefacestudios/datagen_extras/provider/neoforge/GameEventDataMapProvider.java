package net.sefacestudios.datagen_extras.provider.neoforge;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.gameevent.GameEvent;
import net.sefacestudios.datagen_extras.data_maps.game_event.GameEventDataMap;
import net.sefacestudios.datagen_extras.data_maps.game_event.VibrationFrequenciesDataMap;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public abstract class GameEventDataMapProvider extends AbstractDataMapProvider<GameEventDataMap> {
  public GameEventDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup, "game_event");
  }

  public void addVibrationFrequency(GameEvent gameEvent, int frequency) {
    this.consumer.accept(new VibrationFrequenciesDataMap(gameEvent, frequency));
  }

  public void addVibrationFrequency(Holder<@NotNull GameEvent> gameEvent, int frequency) {
    this.addVibrationFrequency(gameEvent.value(), frequency);
  }

  @NotNull
  @Override
  public String getName() {
    return "(NeoForge) Game Event data maps";
  }
}
