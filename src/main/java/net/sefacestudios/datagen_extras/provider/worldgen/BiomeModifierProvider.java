package net.sefacestudios.datagen_extras.provider.worldgen;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.sefacestudios.datagen_extras.biome_modifiers.BiomeModifier;
import net.sefacestudios.datagen_extras.provider.worldgen.builders.FeaturesModifierBuilder;
import net.sefacestudios.datagen_extras.utils.ModLoaderType;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class BiomeModifierProvider implements DataProvider {
  private final FabricPackOutput output;
  private final CompletableFuture<HolderLookup.Provider> registryLookup;
  private Consumer<BiomeModifier> consumer;

  private PackOutput.PathProvider pathResolver;
  private final ModLoaderType modLoaderType;

  public BiomeModifierProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    this(output, registryLookup, ModLoaderType.FORGE);
  }

  public BiomeModifierProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup, ModLoaderType modLoaderType) {
    this.output = output;
    this.registryLookup = registryLookup;
    this.modLoaderType = modLoaderType;
  }

  public abstract void generate(HolderLookup.Provider registryLookup, Consumer<BiomeModifier> consumer);

  @NotNull
  @Override
  public CompletableFuture<?> run(@NotNull CachedOutput writer) {
    this.pathResolver = this.output.createPathProvider(PackOutput.Target.DATA_PACK, this.modLoaderType.getId() + "/biome_modifier");

    return this.registryLookup.thenCompose(lookup -> {
      final Set<BiomeModifier> biomeModifiers = Sets.newHashSet();
      this.consumer = biomeModifiers::add;

      this.generate(lookup, this.consumer);

      List<CompletableFuture<?>> futures = new ArrayList<>();

      for (BiomeModifier biomeModifier : biomeModifiers) {
        JsonObject object = biomeModifier.toJson();
        object.addProperty("type", this.modLoaderType.getId() + ":" + biomeModifier.modifierType().getPath());

        futures.add(DataProvider.saveStable(writer, object, getOutputPath(biomeModifier.identifier())));
      }

      return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    });
  }

  private Path getOutputPath(Identifier identifier) {
    return pathResolver.json(identifier);
  }

  public FeaturesModifierBuilder features() {
    return new FeaturesModifierBuilder(this.consumer);
  }

  @NotNull
  @Override
  public String getName() {
    return "(" + this.modLoaderType.getName() + ") Biome Modifier";
  }
}
