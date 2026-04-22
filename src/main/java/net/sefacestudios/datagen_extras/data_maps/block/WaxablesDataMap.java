package net.sefacestudios.datagen_extras.data_maps.block;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

public record WaxablesDataMap(Block block, Block waxed) implements BlockDataMap {

  public static Codec<WaxablesDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      Block.CODEC.fieldOf("block").forGetter(WaxablesDataMap::block),
      Block.CODEC.fieldOf("waxed").forGetter(WaxablesDataMap::waxed)
    ).apply(instance, WaxablesDataMap::new)
  );

  @Override
  public JsonObject toJson() {
    Identifier blockResourceKey = BuiltInRegistries.BLOCK.getKey(this.waxed);
    JsonObject obj = new JsonObject();
    obj.addProperty("waxed", blockResourceKey.toString());
    return obj;
  }
}
