package net.sefacestudios.datagen_extras.data_maps.block;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

public record OxidizablesBlockDataMap(Block block, Block nextOxidationStage) implements BlockDataMap {

  public static Codec<OxidizablesBlockDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      Block.CODEC.fieldOf("block").forGetter(OxidizablesBlockDataMap::block),
      Block.CODEC.fieldOf("next_oxidation_stage").forGetter(OxidizablesBlockDataMap::nextOxidationStage)
    ).apply(instance, OxidizablesBlockDataMap::new)
  );

  @Override
  public JsonObject toJson() {
    Identifier blockResourceKey = BuiltInRegistries.BLOCK.getKey(this.nextOxidationStage);
    JsonObject obj = new JsonObject();
    obj.addProperty("next_oxidation_stage", blockResourceKey.toString());
    return obj;
  }
}
