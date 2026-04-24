package net.sefacestudios.datagen_extras.data_maps.villager_profession;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.sefacestudios.datagen_extras.data_maps.DataMap;
import org.jetbrains.annotations.NotNull;

public interface VillagerProfessionDataMap extends DataMap {
  @NotNull
  VillagerProfession profession();

  @NotNull
  @Override
  default String getStringfiedKey() {
    return BuiltInRegistries.VILLAGER_PROFESSION.getKey(this.profession()).toString();
  }
}
