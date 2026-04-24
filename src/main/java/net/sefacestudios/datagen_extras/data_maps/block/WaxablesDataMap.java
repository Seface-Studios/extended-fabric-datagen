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

public record WaxablesDataMap(Block block, Block waxed) implements BlockDataMap {

  public static Codec<WaxablesDataMap> CODEC = RecordCodecBuilder.create(
    (instance) -> instance.group(
      Block.CODEC.fieldOf("block").forGetter(WaxablesDataMap::block),
      ResourceKey.codec(Registries.BLOCK)
        .xmap(BlockDataMap::getBlockByResourceKey, block -> block.builtInRegistryHolder().key())
        .fieldOf("waxed")
        .forGetter(WaxablesDataMap::waxed)
    ).apply(instance, WaxablesDataMap::new)
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
    return "waxables";
  }
}
