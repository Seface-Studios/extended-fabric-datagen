package net.sefacestudios.datagen_extras.data_maps.block;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public record StrippablesDataMap(Block block, Block strippedBlock) implements BlockDataMap {

  public static Codec<StrippablesDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      Block.CODEC.fieldOf("block").forGetter(StrippablesDataMap::block),
      ResourceKey.codec(Registries.BLOCK)
        .xmap(BlockDataMap::getBlockByResourceKey, block -> block.builtInRegistryHolder().key())
        .fieldOf("stripped_block")
        .forGetter(StrippablesDataMap::strippedBlock)
    ).apply(instance, StrippablesDataMap::new)
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
    return "strippables";
  }
}
