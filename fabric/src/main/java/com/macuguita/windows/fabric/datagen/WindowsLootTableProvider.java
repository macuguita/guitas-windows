package com.macuguita.windows.fabric.datagen;

import com.macuguita.windows.reg.WindowsBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class WindowsLootTableProvider extends FabricBlockLootTableProvider {
    public WindowsLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        dropsWithSilkTouch(WindowsBlocks.WINDOW.get());
        dropsWithSilkTouch(WindowsBlocks.AQUARIUM_GLASS.get());
    }
}
