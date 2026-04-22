package net.sefacestudios.testmod.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.gameevent.GameEvent;
import net.sefacestudios.datagen_extras.data_maps.game_event.GameEventDataMap;
import net.sefacestudios.datagen_extras.provider.neoforge.GameEventMapProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModGameEventDataMapProvider extends GameEventMapProvider {
  public ModGameEventDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup);
  }

  @Override
  public void generate(HolderLookup.Provider registryLookup, Consumer<GameEventDataMap> consumer) {
    this.addVibrationFrequency(GameEvent.SPLASH, 2);
  }
}
