package net.sefacestudios.datagen_extras.data_maps.villager_profession;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

public record RaidHeroGiftsDataMap(VillagerProfession profession, Identifier lootTable) implements VillagerProfessionDataMap {
  public static Codec<RaidHeroGiftsDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      BuiltInRegistries.VILLAGER_PROFESSION.byNameCodec().fieldOf("profession").forGetter(RaidHeroGiftsDataMap::profession),
      Identifier.CODEC.fieldOf("loot_table").forGetter(RaidHeroGiftsDataMap::lootTable)
    ).apply(instance, RaidHeroGiftsDataMap::new)
  );

  @Override
  public JsonObject toJson() {
    JsonObject obj = new JsonObject();
    obj.addProperty("loot_table", this.lootTable.toString());
    return obj;
  }
}
