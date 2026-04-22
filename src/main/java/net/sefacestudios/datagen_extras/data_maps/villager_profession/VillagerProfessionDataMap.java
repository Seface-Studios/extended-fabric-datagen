package net.sefacestudios.datagen_extras.data_maps.villager_profession;

import com.google.gson.JsonObject;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public interface VillagerProfessionDataMap {
  VillagerProfession profession();
  JsonObject toJson();
}
