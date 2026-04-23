package net.sefacestudios.datagen_extras.provider.worldgen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.sefacestudios.datagen_extras.utils.ModLoaderType;

import java.util.concurrent.CompletableFuture;

public abstract class NeoForgeBiomeModifierProvider extends BiomeModifierProvider {
  public NeoForgeBiomeModifierProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup, ModLoaderType.NEOFORGE);
  }
}
