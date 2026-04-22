package net.sefacestudios.testmod.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.sefacestudios.datagen_extras.data_maps.entity_type.AcceptableVillagerDistancesDataMap;
import net.sefacestudios.datagen_extras.data_maps.entity_type.EntityTypeDataMap;
import net.sefacestudios.datagen_extras.provider.neoforge.EntityDataMapProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModEntityDataMapProvider extends EntityDataMapProvider {
  public ModEntityDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup);
  }

  @Override
  public void generate(HolderLookup.Provider registryLookup, Consumer<EntityTypeDataMap> consumer) {
    this.addAcceptableVillagerDistance(EntityType.BLAZE, 4.0f);
    this.addMonsterRoomMobs(EntityType.SQUID, 100);
    this.addParrotImitationSound(EntityType.ALLAY, SoundEvents.AMBIENT_CAVE);
  }
}
