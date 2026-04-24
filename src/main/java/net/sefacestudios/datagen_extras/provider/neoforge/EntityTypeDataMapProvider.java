package net.sefacestudios.datagen_extras.provider.neoforge;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.sefacestudios.datagen_extras.data_maps.entity_type.AcceptableVillagerDistancesDataMap;
import net.sefacestudios.datagen_extras.data_maps.entity_type.EntityTypeDataMap;
import net.sefacestudios.datagen_extras.data_maps.entity_type.MonsterRoomMobsDataMap;
import net.sefacestudios.datagen_extras.data_maps.entity_type.ParrotImitationsDataMap;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public abstract class EntityTypeDataMapProvider extends AbstractDataMapProvider<EntityTypeDataMap> {
  public EntityTypeDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup, "entity_type");
  }

  public void addAcceptableVillagerDistance(EntityType<?> entityType, float acceptableVillagerDistance) {
    this.consumer.accept(new AcceptableVillagerDistancesDataMap(entityType, acceptableVillagerDistance));
  }

  public void addMonsterRoomMobs(EntityType<?> entityType, int weight) {
    this.consumer.accept(new MonsterRoomMobsDataMap(entityType, weight));
  }

  public void addParrotImitationSound(EntityType<?> entityType, SoundEvent sound) {
    this.consumer.accept(new ParrotImitationsDataMap(entityType, sound));
  }

  public void addParrotImitationSound(EntityType<?> entityType, Holder<@NotNull SoundEvent> sound) {
    this.addParrotImitationSound(entityType, sound.value());
  }

  @NotNull
  @Override
  public String getName() {
    return "(NeoForge) Entity Type data maps";
  }
}
