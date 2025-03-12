package com.macuguita.windows.fabric;

import com.macuguita.windows.Windows;
import com.macuguita.windows.fabric.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class WindowsDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

		Windows.LOGGER.info("running datagen");

		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(WindowsModelProvider::new);
		pack.addProvider(WindowsRecipeProvider::new);
		pack.addProvider(WindowsLootTableProvider::new);
		pack.addProvider(WindowsLangProvider::new);
		pack.addProvider(WindowsBlockTagProvider::new);
		pack.addProvider(WindowsItemTagProvider::new);
	}
}
