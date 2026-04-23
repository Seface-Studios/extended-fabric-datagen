package net.sefacestudios.testmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.sefacestudios.datagen_extras.utils.ModLoaderType;
import net.sefacestudios.testmod.datagen.provider.*;

public class Main implements DataGeneratorEntrypoint {

  @Override
  public void onInitializeDataGenerator(FabricDataGenerator datagen) {
    final FabricDataGenerator.Pack pack = datagen.createPack();

    pack.addProvider(ModEntityTypeDataMapProvider::new);
    pack.addProvider(ModItemDataMapProvider::new);
    pack.addProvider(ModGameEventDataMapProvider::new);
    pack.addProvider(ModVillagerProfessionDataMapProvider::new);
    pack.addProvider(ModBlockDataMapProvider::new);
    pack.addProvider(ModWorlgenDataMapProvider::new);
    pack.addProvider(ModBiomeModifierProvider::new);
    pack.addProvider((o, rf) -> new ModBiomeModifierProvider(o, rf, ModLoaderType.NEOFORGE));
  }
}
