package net.sefacestudios.datagen_extras.biome_modifiers;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.sefacestudios.datagen_extras.biome_modifiers.helpers.BiomeModifierTypes;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public record RemoveFeatureBiomeModifierType(
  Identifier identifier,
  BiomeModifierTypes modifierType,
  Either<ResourceKey<@NotNull Biome>, Either<List<ResourceKey<@NotNull Biome>>, TagKey<@NotNull Biome>>> biomes,
  Either<ResourceKey<@NotNull PlacedFeature>, Either<List<ResourceKey<@NotNull PlacedFeature>>, TagKey<@NotNull PlacedFeature>>> features,
  Optional<Either<GenerationStep.Decoration, List<GenerationStep.Decoration>>> steps
) implements BiomeModifier {

  public static final Codec<RemoveFeatureBiomeModifierType> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      Identifier.CODEC.fieldOf("identifier").forGetter(RemoveFeatureBiomeModifierType::identifier),
      BiomeModifierTypes.CODEC.fieldOf("type").forGetter(RemoveFeatureBiomeModifierType::modifierType),
      BiomeModifier.BIOMES_CODEC.fieldOf("biomes").forGetter(RemoveFeatureBiomeModifierType::biomes),
      BiomeModifier.FEATURES_CODEC.fieldOf("features").forGetter(RemoveFeatureBiomeModifierType::features),
      BiomeModifier.STEPS_CODEC.optionalFieldOf("steps").forGetter(RemoveFeatureBiomeModifierType::steps)
    ).apply(instance, RemoveFeatureBiomeModifierType::new)
  );

  @Override
  public JsonObject toJson() {
    JsonObject object = CODEC
      .encodeStart(JsonOps.INSTANCE, this)
      .getOrThrow(IllegalStateException::new)
      .getAsJsonObject();

    object.remove("identifier");

    return object;
  }
}
