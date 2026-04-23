package net.sefacestudios.datagen_extras.provider.worldgen.builders;

import com.mojang.datafixers.util.Either;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.sefacestudios.datagen_extras.biome_modifiers.AddFeatureBiomeModifierType;
import net.sefacestudios.datagen_extras.biome_modifiers.BiomeModifier;
import net.sefacestudios.datagen_extras.biome_modifiers.RemoveFeatureBiomeModifierType;
import net.sefacestudios.datagen_extras.biome_modifiers.helpers.BiomeModifierTypes;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record FeaturesModifierBuilder(Consumer<BiomeModifier> consumer) {

  // ADD
  public FeaturesModifierBuilder add(Identifier id, ResourceKey<@NotNull Biome> biome, ResourceKey<@NotNull PlacedFeature> feature) {
    return add(id, biome, feature, GenerationStep.Decoration.VEGETAL_DECORATION);
  }

  public FeaturesModifierBuilder add(Identifier id, ResourceKey<@NotNull Biome> biome, List<@NotNull ResourceKey<@NotNull PlacedFeature>> features) {
    return add(id, biome, features, GenerationStep.Decoration.VEGETAL_DECORATION);
  }

  public FeaturesModifierBuilder add(Identifier id, ResourceKey<@NotNull Biome> biome, TagKey<@NotNull PlacedFeature> featureTag) {
    return add(id, biome, featureTag, GenerationStep.Decoration.VEGETAL_DECORATION);
  }

  public FeaturesModifierBuilder add(Identifier id, List<@NotNull ResourceKey<@NotNull Biome>> biomes, ResourceKey<@NotNull PlacedFeature> feature) {
    return add(id, biomes, feature, GenerationStep.Decoration.VEGETAL_DECORATION);
  }

  public FeaturesModifierBuilder add(Identifier id, List<@NotNull ResourceKey<@NotNull Biome>> biomes, List<@NotNull ResourceKey<@NotNull PlacedFeature>> features) {
    return add(id, biomes, features, GenerationStep.Decoration.VEGETAL_DECORATION);
  }

  public FeaturesModifierBuilder add(Identifier id, List<@NotNull ResourceKey<@NotNull Biome>> biomes, TagKey<@NotNull PlacedFeature> featureTag) {
    return add(id, biomes, featureTag, GenerationStep.Decoration.VEGETAL_DECORATION);
  }

  public FeaturesModifierBuilder add(Identifier id, TagKey<@NotNull Biome> biomeTag, ResourceKey<@NotNull PlacedFeature> feature) {
    return add(id, biomeTag, feature, GenerationStep.Decoration.VEGETAL_DECORATION);
  }

  public FeaturesModifierBuilder add(Identifier id, TagKey<@NotNull Biome> biomeTag, List<@NotNull ResourceKey<@NotNull PlacedFeature>> features) {
    return add(id, biomeTag, features, GenerationStep.Decoration.VEGETAL_DECORATION);
  }

  public FeaturesModifierBuilder add(Identifier id, TagKey<@NotNull Biome> biomeTag, TagKey<@NotNull PlacedFeature> featureTag) {
    return add(id, biomeTag, featureTag, GenerationStep.Decoration.VEGETAL_DECORATION);
  }

  // ADD

  public FeaturesModifierBuilder add(Identifier id, ResourceKey<@NotNull Biome> biome, ResourceKey<@NotNull PlacedFeature> feature, GenerationStep.Decoration step) {
    return add0(id, bKey(biome), fKey(feature), step);
  }

  public FeaturesModifierBuilder add(Identifier id, ResourceKey<@NotNull Biome> biome, List<@NotNull ResourceKey<@NotNull PlacedFeature>> features, GenerationStep.Decoration step) {
    return add0(id, bKey(biome), fList(features), step);
  }

  public FeaturesModifierBuilder add(Identifier id, ResourceKey<@NotNull Biome> biome, TagKey<@NotNull PlacedFeature> featureTag, GenerationStep.Decoration step) {
    return add0(id, bKey(biome), fTag(featureTag), step);
  }

  public FeaturesModifierBuilder add(Identifier id, List<@NotNull ResourceKey<@NotNull Biome>> biomes, ResourceKey<@NotNull PlacedFeature> feature, GenerationStep.Decoration step) {
    return add0(id, bList(biomes), fKey(feature), step);
  }

  public FeaturesModifierBuilder add(Identifier id, List<@NotNull ResourceKey<@NotNull Biome>> biomes, List<@NotNull ResourceKey<@NotNull PlacedFeature>> features, GenerationStep.Decoration step) {
    return add0(id, bList(biomes), fList(features), step);
  }

  public FeaturesModifierBuilder add(Identifier id, List<@NotNull ResourceKey<@NotNull Biome>> biomes, TagKey<@NotNull PlacedFeature> featureTag, GenerationStep.Decoration step) {
    return add0(id, bList(biomes), fTag(featureTag), step);
  }

  public FeaturesModifierBuilder add(Identifier id, TagKey<@NotNull Biome> biomeTag, ResourceKey<@NotNull PlacedFeature> feature, GenerationStep.Decoration step) {
    return add0(id, bTag(biomeTag), fKey(feature), step);
  }

  public FeaturesModifierBuilder add(Identifier id, TagKey<@NotNull Biome> biomeTag, List<@NotNull ResourceKey<@NotNull PlacedFeature>> features, GenerationStep.Decoration step) {
    return add0(id, bTag(biomeTag), fList(features), step);
  }

  public FeaturesModifierBuilder add(Identifier id, TagKey<@NotNull Biome> biomeTag, TagKey<@NotNull PlacedFeature> featureTag, GenerationStep.Decoration step) {
    return add0(id, bTag(biomeTag), fTag(featureTag), step);
  }

  private FeaturesModifierBuilder add0(
    Identifier id,
    Either<ResourceKey<@NotNull Biome>, Either<List<ResourceKey<@NotNull Biome>>, TagKey<@NotNull Biome>>> biomes,
    Either<ResourceKey<@NotNull PlacedFeature>, Either<List<ResourceKey<@NotNull PlacedFeature>>, TagKey<@NotNull PlacedFeature>>> features,
    GenerationStep.Decoration step
  ) {
    this.consumer.accept(new AddFeatureBiomeModifierType(id, BiomeModifierTypes.ADD_FEATURE, biomes, features, step));
    return this;
  }

  // REMOVE

  public FeaturesModifierBuilder remove(Identifier id, ResourceKey<@NotNull Biome> biome, ResourceKey<@NotNull PlacedFeature> feature, GenerationStep.Decoration step) {
    return remove0(id, bKey(biome), fKey(feature), Optional.of(Either.left(step)));
  }

  public FeaturesModifierBuilder remove(Identifier id, ResourceKey<@NotNull Biome> biome, ResourceKey<@NotNull PlacedFeature> feature, List<GenerationStep.@NotNull Decoration> steps) {
    return remove0(id, bKey(biome), fKey(feature), Optional.of(Either.right(steps)));
  }

  public FeaturesModifierBuilder remove(Identifier id, ResourceKey<@NotNull Biome> biome, ResourceKey<@NotNull PlacedFeature> feature) {
    return remove0(id, bKey(biome), fKey(feature), Optional.empty());
  }

  public FeaturesModifierBuilder remove(Identifier id, ResourceKey<@NotNull Biome> biome, List<@NotNull ResourceKey<@NotNull PlacedFeature>> features, GenerationStep.Decoration step) {
    return remove0(id, bKey(biome), fList(features), Optional.of(Either.left(step)));
  }

  public FeaturesModifierBuilder remove(Identifier id, ResourceKey<@NotNull Biome> biome, List<@NotNull ResourceKey<@NotNull PlacedFeature>> features, List<GenerationStep.@NotNull Decoration> steps) {
    return remove0(id, bKey(biome), fList(features), Optional.of(Either.right(steps)));
  }

  public FeaturesModifierBuilder remove(Identifier id, ResourceKey<@NotNull Biome> biome, List<@NotNull ResourceKey<@NotNull PlacedFeature>> features) {
    return remove0(id, bKey(biome), fList(features), Optional.empty());
  }

  public FeaturesModifierBuilder remove(Identifier id, ResourceKey<@NotNull Biome> biome, TagKey<@NotNull PlacedFeature> featureTag, GenerationStep.Decoration step) {
    return remove0(id, bKey(biome), fTag(featureTag), Optional.of(Either.left(step)));
  }

  public FeaturesModifierBuilder remove(Identifier id, ResourceKey<@NotNull Biome> biome, TagKey<@NotNull PlacedFeature> featureTag, List<GenerationStep.@NotNull Decoration> steps) {
    return remove0(id, bKey(biome), fTag(featureTag), Optional.of(Either.right(steps)));
  }

  public FeaturesModifierBuilder remove(Identifier id, ResourceKey<@NotNull Biome> biome, TagKey<@NotNull PlacedFeature> featureTag) {
    return remove0(id, bKey(biome), fTag(featureTag), Optional.empty());
  }

  public FeaturesModifierBuilder remove(Identifier id, List<@NotNull ResourceKey<@NotNull Biome>> biomes, ResourceKey<@NotNull PlacedFeature> feature, GenerationStep.Decoration step) {
    return remove0(id, bList(biomes), fKey(feature), Optional.of(Either.left(step)));
  }

  public FeaturesModifierBuilder remove(Identifier id, List<@NotNull ResourceKey<@NotNull Biome>> biomes, ResourceKey<@NotNull PlacedFeature> feature, List<GenerationStep.@NotNull Decoration> steps) {
    return remove0(id, bList(biomes), fKey(feature), Optional.of(Either.right(steps)));
  }

  public FeaturesModifierBuilder remove(Identifier id, List<@NotNull ResourceKey<@NotNull Biome>> biomes, ResourceKey<@NotNull PlacedFeature> feature) {
    return remove0(id, bList(biomes), fKey(feature), Optional.empty());
  }

  public FeaturesModifierBuilder remove(Identifier id, List<@NotNull ResourceKey<@NotNull Biome>> biomes, List<@NotNull ResourceKey<@NotNull PlacedFeature>> features, GenerationStep.Decoration step) {
    return remove0(id, bList(biomes), fList(features), Optional.of(Either.left(step)));
  }

  public FeaturesModifierBuilder remove(Identifier id, List<@NotNull ResourceKey<@NotNull Biome>> biomes, List<@NotNull ResourceKey<@NotNull PlacedFeature>> features, List<GenerationStep.@NotNull Decoration> steps) {
    return remove0(id, bList(biomes), fList(features), Optional.of(Either.right(steps)));
  }

  public FeaturesModifierBuilder remove(Identifier id, List<@NotNull ResourceKey<@NotNull Biome>> biomes, List<@NotNull ResourceKey<@NotNull PlacedFeature>> features) {
    return remove0(id, bList(biomes), fList(features), Optional.empty());
  }

  public FeaturesModifierBuilder remove(Identifier id, List<@NotNull ResourceKey<@NotNull Biome>> biomes, TagKey<@NotNull PlacedFeature> featureTag, GenerationStep.Decoration step) {
    return remove0(id, bList(biomes), fTag(featureTag), Optional.of(Either.left(step)));
  }

  public FeaturesModifierBuilder remove(Identifier id, List<@NotNull ResourceKey<@NotNull Biome>> biomes, TagKey<@NotNull PlacedFeature> featureTag, List<GenerationStep.@NotNull Decoration> steps) {
    return remove0(id, bList(biomes), fTag(featureTag), Optional.of(Either.right(steps)));
  }

  public FeaturesModifierBuilder remove(Identifier id, List<@NotNull ResourceKey<@NotNull Biome>> biomes, TagKey<@NotNull PlacedFeature> featureTag) {
    return remove0(id, bList(biomes), fTag(featureTag), Optional.empty());
  }

  public FeaturesModifierBuilder remove(Identifier id, TagKey<@NotNull Biome> biomeTag, ResourceKey<@NotNull PlacedFeature> feature, GenerationStep.Decoration step) {
    return remove0(id, bTag(biomeTag), fKey(feature), Optional.of(Either.left(step)));
  }

  public FeaturesModifierBuilder remove(Identifier id, TagKey<@NotNull Biome> biomeTag, ResourceKey<@NotNull PlacedFeature> feature, List<GenerationStep.@NotNull Decoration> steps) {
    return remove0(id, bTag(biomeTag), fKey(feature), Optional.of(Either.right(steps)));
  }

  public FeaturesModifierBuilder remove(Identifier id, TagKey<@NotNull Biome> biomeTag, ResourceKey<@NotNull PlacedFeature> feature) {
    return remove0(id, bTag(biomeTag), fKey(feature), Optional.empty());
  }

  public FeaturesModifierBuilder remove(Identifier id, TagKey<@NotNull Biome> biomeTag, List<@NotNull ResourceKey<@NotNull PlacedFeature>> features, GenerationStep.Decoration step) {
    return remove0(id, bTag(biomeTag), fList(features), Optional.of(Either.left(step)));
  }

  public FeaturesModifierBuilder remove(Identifier id, TagKey<@NotNull Biome> biomeTag, List<@NotNull ResourceKey<@NotNull PlacedFeature>> features, List<GenerationStep.@NotNull Decoration> steps) {
    return remove0(id, bTag(biomeTag), fList(features), Optional.of(Either.right(steps)));
  }

  public FeaturesModifierBuilder remove(Identifier id, TagKey<@NotNull Biome> biomeTag, List<@NotNull ResourceKey<@NotNull PlacedFeature>> features) {
    return remove0(id, bTag(biomeTag), fList(features), Optional.empty());
  }

  public FeaturesModifierBuilder remove(Identifier id, TagKey<@NotNull Biome> biomeTag, TagKey<@NotNull PlacedFeature> featureTag, GenerationStep.Decoration step) {
    return remove0(id, bTag(biomeTag), fTag(featureTag), Optional.of(Either.left(step)));
  }

  public FeaturesModifierBuilder remove(Identifier id, TagKey<@NotNull Biome> biomeTag, TagKey<@NotNull PlacedFeature> featureTag, List<GenerationStep.@NotNull Decoration> steps) {
    return remove0(id, bTag(biomeTag), fTag(featureTag), Optional.of(Either.right(steps)));
  }

  public FeaturesModifierBuilder remove(Identifier id, TagKey<@NotNull Biome> biomeTag, TagKey<@NotNull PlacedFeature> featureTag) {
    return remove0(id, bTag(biomeTag), fTag(featureTag), Optional.empty());
  }

  private FeaturesModifierBuilder remove0(
    Identifier id,
    Either<ResourceKey<@NotNull Biome>, Either<List<ResourceKey<@NotNull Biome>>, TagKey<@NotNull Biome>>> biomes,
    Either<ResourceKey<@NotNull PlacedFeature>, Either<List<ResourceKey<@NotNull PlacedFeature>>, TagKey<@NotNull PlacedFeature>>> features,
    Optional<Either<GenerationStep.Decoration, List<GenerationStep.Decoration>>> steps
  ) {
    this.consumer.accept(new RemoveFeatureBiomeModifierType(id, BiomeModifierTypes.REMOVE_FEATURE, biomes, features, steps));
    return this;
  }

  private static Either<ResourceKey<@NotNull Biome>, Either<List<ResourceKey<@NotNull Biome>>, TagKey<@NotNull Biome>>> bKey(ResourceKey<@NotNull Biome> b) {
    return Either.left(b);
  }

  private static Either<ResourceKey<@NotNull Biome>, Either<List<ResourceKey<@NotNull Biome>>, TagKey<@NotNull Biome>>> bList(List<@NotNull ResourceKey<@NotNull Biome>> b) {
    return Either.right(Either.left(b));
  }

  private static Either<ResourceKey<@NotNull Biome>, Either<List<ResourceKey<@NotNull Biome>>, TagKey<@NotNull Biome>>> bTag(TagKey<@NotNull Biome> t) {
    return Either.right(Either.right(t));
  }

  private static Either<ResourceKey<@NotNull PlacedFeature>, Either<List<ResourceKey<@NotNull PlacedFeature>>, TagKey<@NotNull PlacedFeature>>> fKey(ResourceKey<@NotNull PlacedFeature> f) {
    return Either.left(f);
  }

  private static Either<ResourceKey<@NotNull PlacedFeature>, Either<List<ResourceKey<@NotNull PlacedFeature>>, TagKey<@NotNull PlacedFeature>>> fList(List<@NotNull ResourceKey<@NotNull PlacedFeature>> f) {
    return Either.right(Either.left(f));
  }

  private static Either<ResourceKey<@NotNull PlacedFeature>, Either<List<ResourceKey<@NotNull PlacedFeature>>, TagKey<@NotNull PlacedFeature>>> fTag(TagKey<@NotNull PlacedFeature> t) {
    return Either.right(Either.right(t));
  }
}
