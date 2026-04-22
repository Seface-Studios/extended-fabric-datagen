package net.sefacestudios.datagen_extras.data_maps.block;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

public record StrippablesDataMap(Block block, Block strippedBlock) implements BlockDataMap {

  public static Codec<StrippablesDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      Block.CODEC.fieldOf("block").forGetter(StrippablesDataMap::block),
      Block.CODEC.fieldOf("stripped_block").forGetter(StrippablesDataMap::strippedBlock)
    ).apply(instance, StrippablesDataMap::new)
  );

  @Override
  public JsonObject toJson() {
    Identifier blockResourceKey = BuiltInRegistries.BLOCK.getKey(this.strippedBlock);
    JsonObject obj = new JsonObject();
    obj.addProperty("stripped_block", blockResourceKey.toString());
    return obj;
  }
}
