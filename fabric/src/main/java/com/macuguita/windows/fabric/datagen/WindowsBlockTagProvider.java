package com.macuguita.windows.fabric.datagen;

import com.macuguita.windows.reg.WindowsBlocks;
import com.macuguita.windows.reg.WindowsTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class WindowsBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public WindowsBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(WindowsTags.Blocks.WINDOWS)
                .add(WindowsBlocks.WINDOW.get());
        getOrCreateTagBuilder(WindowsTags.Blocks.AQUARIUM_GLASS)
                .add(WindowsBlocks.WINDOW.get())
                .add(WindowsBlocks.AQUARIUM_GLASS.get());
    }
}
