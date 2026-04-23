package net.sefacestudios.datagen_extras.biome_modifiers;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
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

public interface BiomeModifier {
  Identifier identifier();
  BiomeModifierTypes modifierType();
  Either<ResourceKey<@NotNull Biome>, Either<List<ResourceKey<@NotNull Biome>>, TagKey<@NotNull Biome>>> biomes();

  JsonObject toJson();

  Codec<TagKey<@NotNull Biome>> BIOME_TAG_CODEC = Codec.STRING.comapFlatMap(
    str -> {
      if (str.startsWith("#")) {
        Identifier location = Identifier.tryParse(str.substring(1));
        if (location != null) {
          return DataResult.success(TagKey.create(Registries.BIOME, location));
        }
        return DataResult.error(() -> "Invalid biome tag id: " + str);
      }
      return DataResult.error(() -> "Biome tag must start with '#': " + str);
    },
    tagKey -> "#" + tagKey.location()
  );

  Codec<Either<ResourceKey<@NotNull Biome>, Either<List<ResourceKey<@NotNull Biome>>, TagKey<@NotNull Biome>>>> BIOMES_CODEC =
    Codec.either(
      ResourceKey.codec(Registries.BIOME),
      Codec.either(
        ResourceKey.codec(Registries.BIOME).listOf(),
        BIOME_TAG_CODEC
      )
    );

  Codec<TagKey<@NotNull PlacedFeature>> FEATURES_TAG_CODEC = Codec.STRING.comapFlatMap(
    str -> {
      if (str.startsWith("#")) {
        Identifier location = Identifier.tryParse(str.substring(1));
        if (location != null) {
          return DataResult.success(TagKey.create(Registries.PLACED_FEATURE, location));
        }
        return DataResult.error(() -> "Invalid feature tag id: " + str);
      }
      return DataResult.error(() -> "Feature tag must start with '#': " + str);
    },
    tagKey -> "#" + tagKey.location()
  );

  Codec<Either<ResourceKey<@NotNull PlacedFeature>, Either<List<ResourceKey<@NotNull PlacedFeature>>, TagKey<@NotNull PlacedFeature>>>> FEATURES_CODEC =
    Codec.either(
      ResourceKey.codec(Registries.PLACED_FEATURE),
      Codec.either(
        ResourceKey.codec(Registries.PLACED_FEATURE).listOf(),
        FEATURES_TAG_CODEC
      )
    );

  Codec<Either<GenerationStep.Decoration, List<GenerationStep.Decoration>>> STEPS_CODEC = Codec.either(
    GenerationStep.Decoration.CODEC,
    GenerationStep.Decoration.CODEC.listOf()
  );
}
