package net.sefacestudios.datagen_extras.provider.neoforge;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.sefacestudios.datagen_extras.data_maps.DataMap;
import net.sefacestudios.datagen_extras.utils.ModLoaderType;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class AbstractDataMapProvider<T extends DataMap> implements DataProvider {
  private final CompletableFuture<HolderLookup.Provider> lookup;
  protected Consumer<T> consumer;

  private final PackOutput.PathProvider path;

  public AbstractDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookup, String dataMapPath) {
    this.lookup = lookup;
    this.path = output.createPathProvider(PackOutput.Target.DATA_PACK, "data_maps/" + dataMapPath);
  }

  public abstract void generate(HolderLookup.Provider lookup, Consumer<T> consumer);

  @NotNull
  @Override
  public CompletableFuture<?> run(@NotNull CachedOutput writer) {
    final Set<T> dataMaps = Sets.newHashSet();
    this.consumer = dataMaps::add;

    Map<String, JsonObject> roots = new HashMap<>();

    return this.lookup.thenCompose(lookup -> {
      this.generate(lookup, this.consumer);

      for (T dataMap : dataMaps) {
        String fileName = dataMap.getFileName();

        JsonObject root = roots.computeIfAbsent(fileName, k -> {
          JsonObject object = new JsonObject();
          object.add("values", new JsonObject());
          return object;
        });

        JsonObject values = root.getAsJsonObject("values");
        values.add(dataMap.getStringifiedKey(), dataMap.toJson());
      }

      return CompletableFuture.allOf(
        roots.entrySet().stream()
          .filter(e -> !e.getValue().getAsJsonObject("values").isEmpty())
          .map(e -> DataProvider.saveStable(
            writer,
            e.getValue(),
            this.getOutputPath(e.getKey())
          ))
          .toArray(CompletableFuture[]::new)
      );
    });
  }

  public Path getOutputPath(String fileName) {
    return this.path.json(Identifier.fromNamespaceAndPath(ModLoaderType.NEOFORGE.getId(), fileName));
  }
}
