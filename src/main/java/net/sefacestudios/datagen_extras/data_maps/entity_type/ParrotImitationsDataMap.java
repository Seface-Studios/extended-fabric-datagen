package net.sefacestudios.datagen_extras.data_maps.entity_type;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public record ParrotImitationsDataMap(EntityType<?> entityType, SoundEvent sound) implements EntityTypeDataMap {
  public static Codec<ParrotImitationsDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      EntityType.CODEC.fieldOf("entity_type").forGetter(ParrotImitationsDataMap::entityType),
      ResourceKey.codec(Registries.SOUND_EVENT)
        .xmap(
          ParrotImitationsDataMap::getSoundEventByResourceKey,
          soundEvent -> BuiltInRegistries.SOUND_EVENT
            .getResourceKey(soundEvent)
            .orElseThrow()
        )
        .fieldOf("sound")
        .forGetter(ParrotImitationsDataMap::sound)
    ).apply(instance, ParrotImitationsDataMap::new)
  );

  @NotNull
  @Override
  public JsonObject toJson() {
    JsonObject object = CODEC
      .encodeStart(JsonOps.INSTANCE, this)
      .getOrThrow(IllegalStateException::new)
      .getAsJsonObject();

    object.remove("entity_type");

    return object;
  }

  @Override
  public @NotNull String getFileName() {
    return "parrot_imitations";
  }

  /**
   * Get the sound event object from the ResourceKey.
   * @param resourceKey The sound event ResourceKey.
   */
  static SoundEvent getSoundEventByResourceKey(ResourceKey<@NotNull SoundEvent> resourceKey) {
    return BuiltInRegistries.SOUND_EVENT.getValueOrThrow(resourceKey);
  }
}
