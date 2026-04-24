package net.sefacestudios.datagen_extras.data_maps.block;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record OxidizablesBlockDataMap(Block block, Block nextOxidationStage) implements BlockDataMap {

  public static Codec<OxidizablesBlockDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      Block.CODEC.fieldOf("block").forGetter(OxidizablesBlockDataMap::block),
      ResourceKey.codec(Registries.BLOCK)
        .xmap(BlockDataMap::getBlockByResourceKey, block -> block.builtInRegistryHolder().key())
        .fieldOf("next_oxidation_stage")
        .forGetter(OxidizablesBlockDataMap::nextOxidationStage)
    ).apply(instance, OxidizablesBlockDataMap::new)
  );

  @NotNull
  @Override
  public JsonObject toJson() {
    JsonObject object = CODEC
      .encodeStart(JsonOps.INSTANCE, this)
      .getOrThrow(IllegalStateException::new)
      .getAsJsonObject();

    object.remove("block");

    return object;
  }

  @NotNull
  @Override
  public String getFileName() {
    return "oxidizables";
  }
}
