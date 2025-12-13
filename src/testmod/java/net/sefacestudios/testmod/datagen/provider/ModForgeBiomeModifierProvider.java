package net.sefacestudios.testmod.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.sefacestudios.datagen_extras.modifiers.ForgedBiomeModifier;
import net.sefacestudios.datagen_extras.modifiers.ForgedBiomeModifierTypes;
import net.sefacestudios.datagen_extras.provider.ForgifiedBiomeModifierProvider;
import net.sefacestudios.datagen_extras.utils.ForgedModLoaders;
import net.sefacestudios.testmod.TestMod;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModForgeBiomeModifierProvider extends ForgifiedBiomeModifierProvider {
  private static final ResourceKey<ConfiguredFeature<?, ?>> CUSTOM_FEATURE_1 = TestMod.key(Registries.CONFIGURED_FEATURE, "custom_feature_1");
  private static final ResourceKey<ConfiguredFeature<?, ?>> CUSTOM_FEATURE_2 = TestMod.key(Registries.CONFIGURED_FEATURE, "custom_feature_2");
  private static final ResourceKey<ConfiguredFeature<?, ?>> CUSTOM_FEATURE_3 = TestMod.key(Registries.CONFIGURED_FEATURE, "custom_feature_3");

  public ModForgeBiomeModifierProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup, ForgedModLoaders loader) {
    super(output, registryLookup, loader);
  }

  @Override
  public void generate(HolderLookup.Provider registryLookup, Consumer<ForgedBiomeModifier> consumer, ForgedModLoaders modLoader) {
    /* Using TagKey<Biome> */
    ForgedBiomeModifier.Builder.biomeModifier(modLoader)
      .type(ForgedBiomeModifierTypes.ADD_FEATURES)
      .biomes(BiomeTags.IS_FOREST)
      .features(ModForgeBiomeModifierProvider.CUSTOM_FEATURE_1)
      .step(GenerationStep.Decoration.VEGETAL_DECORATION)
      .save(consumer);

    /* Using ResourceKey<Biome> */
    ForgedBiomeModifier.Builder.biomeModifier(modLoader)
      .type(ForgedBiomeModifierTypes.ADD_FEATURES)
      .biomes(Biomes.FOREST)
      .features(ModForgeBiomeModifierProvider.CUSTOM_FEATURE_2)
      .step(GenerationStep.Decoration.VEGETAL_DECORATION)
      .save(consumer);

    /* Using ResourceKey<Biome> array */
    ForgedBiomeModifier.Builder.biomeModifier(modLoader)
      .type(ForgedBiomeModifierTypes.ADD_FEATURES)
      .biomes(Biomes.BADLANDS, Biomes.DESERT)
      .features(ModForgeBiomeModifierProvider.CUSTOM_FEATURE_3)
      .step(GenerationStep.Decoration.VEGETAL_DECORATION)
      .save(consumer);
  }
}
