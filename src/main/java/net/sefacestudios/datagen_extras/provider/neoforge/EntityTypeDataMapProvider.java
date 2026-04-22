package net.sefacestudios.datagen_extras.provider.neoforge;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.sefacestudios.datagen_extras.data_maps.entity_type.AcceptableVillagerDistancesDataMap;
import net.sefacestudios.datagen_extras.data_maps.entity_type.EntityTypeDataMap;
import net.sefacestudios.datagen_extras.data_maps.entity_type.MonsterRoomMobsDataMap;
import net.sefacestudios.datagen_extras.data_maps.entity_type.ParrotImitationsDataMap;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class EntityTypeDataMapProvider implements DataProvider {
  private final FabricPackOutput output;
  private final CompletableFuture<HolderLookup.Provider> registryLookup;
  private Consumer<EntityTypeDataMap> consumer;

  private PackOutput.PathProvider pathResolver;

  public EntityTypeDataMapProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    this.output = output;
    this.registryLookup = registryLookup;
  }

  public abstract void generate(HolderLookup.Provider registryLookup, Consumer<EntityTypeDataMap> consumer);

  @NotNull
  @Override
  public CompletableFuture<?> run(@NotNull CachedOutput writer) {
    this.pathResolver = this.output.createPathProvider(PackOutput.Target.DATA_PACK, "data_maps/entity_type");

    return this.registryLookup.thenCompose(lookup -> {
      final Set<EntityTypeDataMap> dataMaps = Sets.newHashSet();
      this.consumer = dataMaps::add;

      this.generate(lookup, this.consumer);

      JsonObject villagerRoot = new JsonObject();
      final JsonObject villagerValues = new JsonObject();

      JsonObject monsterRoot = new JsonObject();
      final JsonObject monsterValues = new JsonObject();

      JsonObject parrotRoot = new JsonObject();
      final JsonObject parrotValues = new JsonObject();

      for (EntityTypeDataMap dataMap : dataMaps) {
        String entityTypeId = BuiltInRegistries.ENTITY_TYPE.getKey(dataMap.entityType()).toString();
        JsonObject entry = dataMap.toJson();

        switch (dataMap) {
          case AcceptableVillagerDistancesDataMap _ -> villagerValues.add(entityTypeId, entry);
          case MonsterRoomMobsDataMap _ -> monsterValues.add(entityTypeId, entry);
          case ParrotImitationsDataMap _ -> parrotValues.add(entityTypeId, entry);

          default -> throw new IllegalStateException("Unknown entity data map type: " + dataMap);
        }
      }

      villagerRoot.add("values", villagerValues);
      monsterRoot.add("values", monsterValues);
      parrotRoot.add("values", parrotValues);

      return CompletableFuture.allOf(
        DataProvider.saveStable(writer, villagerRoot, getOutputPath("acceptable_villager_distances")),
        DataProvider.saveStable(writer, monsterRoot, getOutputPath("monster_room_mobs")),
        DataProvider.saveStable(writer, parrotRoot, getOutputPath("parrot_imitations"))
      );
    });
  }

  private Path getOutputPath(String fileName) {
    return pathResolver.json(Identifier.fromNamespaceAndPath("neoforge", fileName));
  }

  public void addAcceptableVillagerDistance(EntityType<?> entityType, float acceptableVillagerDistance) {
    this.consumer.accept(new AcceptableVillagerDistancesDataMap(entityType, acceptableVillagerDistance));
  }

  public void addMonsterRoomMobs(EntityType<?> entityType, int weight) {
    this.consumer.accept(new MonsterRoomMobsDataMap(entityType, weight));
  }

  public void addParrotImitationSound(EntityType<?> entityType, Holder<@NotNull SoundEvent> sound) {
    this.consumer.accept(new ParrotImitationsDataMap(entityType, sound));
  }

  @NotNull
  @Override
  public String getName() {
    return "(NeoForge) Entity Type Data Maps";
  }
}
