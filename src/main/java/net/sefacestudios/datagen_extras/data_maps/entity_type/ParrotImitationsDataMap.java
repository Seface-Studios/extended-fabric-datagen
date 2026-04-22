package net.sefacestudios.datagen_extras.data_maps.entity_type;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public record ParrotImitationsDataMap(EntityType<?> entityType, Holder<@NotNull SoundEvent> sound) implements EntityTypeDataMap {
  public static Codec<ParrotImitationsDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      EntityType.CODEC.fieldOf("entity_type").forGetter(ParrotImitationsDataMap::entityType),
      SoundEvent.CODEC.fieldOf("sound").forGetter(ParrotImitationsDataMap::sound)
    ).apply(instance, ParrotImitationsDataMap::new)
  );

  @Override
  public JsonObject toJson() {
    JsonObject obj = new JsonObject();
    obj.addProperty("sound", this.sound.getRegisteredName());
    return obj;
  }
}
