package com.macuguita.windows.fabric.datagen;

import com.macuguita.windows.reg.WindowsBlocks;
import com.macuguita.windows.reg.WindowsTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class WindowsItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public WindowsItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(WindowsTags.Items.WINDOWS)
                .add(WindowsBlocks.WINDOW.get().asItem());
        getOrCreateTagBuilder(WindowsTags.Items.AQUARIUM_GLASS)
                .add(WindowsBlocks.WINDOW.get().asItem())
                .add(WindowsBlocks.AQUARIUM_GLASS.get().asItem());
    }
}
