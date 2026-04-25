package net.sefacestudios.datagen_extras.data_maps.villager_profession;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

public record RaidHeroGiftsDataMap(VillagerProfession profession, ResourceKey<@NotNull LootTable> lootTable) implements VillagerProfessionDataMap {
  public static Codec<RaidHeroGiftsDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      BuiltInRegistries.VILLAGER_PROFESSION.byNameCodec().fieldOf("profession").forGetter(RaidHeroGiftsDataMap::profession),
      ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("loot_table").forGetter(RaidHeroGiftsDataMap::lootTable)
    ).apply(instance, RaidHeroGiftsDataMap::new)
  );

  @NotNull
  @Override
  public JsonObject toJson() {
    JsonObject object = CODEC
      .encodeStart(JsonOps.INSTANCE, this)
      .getOrThrow(IllegalStateException::new)
      .getAsJsonObject();

    object.remove("profession");

    return object;
  }

  @NotNull
  @Override
  public String getFileName() {
    return "raid_hero_gifts";
  }
}
