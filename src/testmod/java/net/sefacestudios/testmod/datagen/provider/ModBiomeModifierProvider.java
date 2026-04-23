package net.sefacestudios.testmod.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.sefacestudios.datagen_extras.biome_modifiers.BiomeModifier;
import net.sefacestudios.datagen_extras.provider.worldgen.BiomeModifierProvider;
import net.sefacestudios.datagen_extras.utils.ModLoaderType;
import net.sefacestudios.testmod.TestMod;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModBiomeModifierProvider extends BiomeModifierProvider {
  public ModBiomeModifierProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup);
  }

  public ModBiomeModifierProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup, ModLoaderType modLoaderType) {
    super(output, registryLookup, modLoaderType);
  }

  @Override
  public void generate(HolderLookup.Provider registryLookup, Consumer<BiomeModifier> consumer) {
    this.features().add(TestMod.id("your_1st_added_feature"), BiomeTags.HAS_IGLOO, CavePlacements.CAVE_VINES, GenerationStep.Decoration.UNDERGROUND_ORES);
    this.features().add(TestMod.id("your_2nd_added_feature"), Biomes.JUNGLE, CavePlacements.AMETHYST_GEODE, GenerationStep.Decoration.UNDERGROUND_ORES);
    this.features().add(TestMod.id("your_3rd_added_feature"), List.of(Biomes.SAVANNA, Biomes.WINDSWEPT_SAVANNA), CavePlacements.CLASSIC_VINES, GenerationStep.Decoration.UNDERGROUND_ORES);
    this.features().add(TestMod.id("your_4rd_added_feature"), List.of(Biomes.SAVANNA, Biomes.WINDSWEPT_SAVANNA), List.of(CavePlacements.CLASSIC_VINES, CavePlacements.AMETHYST_GEODE), GenerationStep.Decoration.UNDERGROUND_ORES);

    this.features().remove(TestMod.id("your_1st_removed_feature"), BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS, CavePlacements.CAVE_VINES, GenerationStep.Decoration.UNDERGROUND_ORES);
    this.features().remove(TestMod.id("your_2nd_removed_feature"), Biomes.DEEP_FROZEN_OCEAN, CavePlacements.AMETHYST_GEODE, List.of(GenerationStep.Decoration.UNDERGROUND_ORES, GenerationStep.Decoration.UNDERGROUND_DECORATION));
    this.features().remove(TestMod.id("your_3rd_removed_feature"), List.of(Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_COLD_OCEAN), CavePlacements.CLASSIC_VINES);
    this.features().remove(TestMod.id("your_4th_removed_feature"), List.of(Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_COLD_OCEAN), List.of(CavePlacements.CLASSIC_VINES, CavePlacements.AMETHYST_GEODE));
  }
}
