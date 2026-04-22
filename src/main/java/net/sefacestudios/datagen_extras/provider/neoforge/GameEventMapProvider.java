package net.sefacestudios.datagen_extras.provider.neoforge;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.gameevent.GameEvent;
import net.sefacestudios.datagen_extras.data_maps.game_event.GameEventDataMap;
import net.sefacestudios.datagen_extras.data_maps.game_event.VibrationFrequenciesDataMap;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class GameEventMapProvider implements DataProvider {
  private final FabricPackOutput output;
  private final CompletableFuture<HolderLookup.Provider> registryLookup;
  private Consumer<GameEventDataMap> consumer;

  private PackOutput.PathProvider pathResolver;

  public GameEventMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    this.output = output;
    this.registryLookup = registryLookup;
  }

  public abstract void generate(HolderLookup.Provider registryLookup, Consumer<GameEventDataMap> consumer);

  @NotNull
  @Override
  public CompletableFuture<?> run(@NotNull CachedOutput writer) {
    this.pathResolver = this.output.createPathProvider(PackOutput.Target.DATA_PACK, "data_maps/game_event");

    return this.registryLookup.thenCompose(lookup -> {
      final Set<GameEventDataMap> dataMaps = Sets.newHashSet();
      this.consumer = dataMaps::add;

      this.generate(lookup, this.consumer);

      JsonObject frequencyRoot = new JsonObject();
      final JsonObject frequencyValues = new JsonObject();

      for (GameEventDataMap dataMap : dataMaps) {
        String itemId = dataMap.gameEvent().getRegisteredName();
        JsonObject entry = dataMap.toJson();

        switch (dataMap) {
          case VibrationFrequenciesDataMap _ -> frequencyValues.add(itemId, entry);

          default -> throw new IllegalStateException("Unknown game event data map type: " + dataMap);
        }
      }

      frequencyRoot.add("values", frequencyValues);

      return CompletableFuture.allOf(
        DataProvider.saveStable(writer, frequencyRoot, getOutputPath("vibration_frequencies"))
      );
    });
  }

  private Path getOutputPath(String fileName) {
    return pathResolver.json(Identifier.fromNamespaceAndPath("neoforge", fileName));
  }

  public void addVibrationFrequency(Holder<@NotNull GameEvent> gameEvent, int frequency) {
    this.consumer.accept(new VibrationFrequenciesDataMap(gameEvent, frequency));
  }

  @NotNull
  @Override
  public String getName() {
    return "(NeoForge) Game Event Data Maps";
  }
}
