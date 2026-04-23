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

public record AddFeatureBiomeModifierType(
  Identifier identifier,
  BiomeModifierTypes modifierType,
  Either<ResourceKey<@NotNull Biome>, Either<List<ResourceKey<@NotNull Biome>>, TagKey<@NotNull Biome>>> biomes,
  Either<ResourceKey<@NotNull PlacedFeature>, Either<List<ResourceKey<@NotNull PlacedFeature>>, TagKey<@NotNull PlacedFeature>>> features,
  GenerationStep.Decoration step
) implements BiomeModifier {

  public static final Codec<AddFeatureBiomeModifierType> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      Identifier.CODEC.fieldOf("identifier").forGetter(AddFeatureBiomeModifierType::identifier),
      BiomeModifierTypes.CODEC.fieldOf("type").forGetter(AddFeatureBiomeModifierType::modifierType),
      BiomeModifier.BIOMES_CODEC.fieldOf("biomes").forGetter(AddFeatureBiomeModifierType::biomes),
      BiomeModifier.FEATURES_CODEC.fieldOf("features").forGetter(AddFeatureBiomeModifierType::features),
      GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(AddFeatureBiomeModifierType::step)
    ).apply(instance, AddFeatureBiomeModifierType::new)
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
