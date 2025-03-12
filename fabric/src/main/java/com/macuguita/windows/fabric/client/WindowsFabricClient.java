package com.macuguita.windows.fabric.client;

import com.macuguita.windows.reg.WindowsBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public final class WindowsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // put all block in the registry into the transparent render layer or whatever it is called
        WindowsBlocks.BLOCKS.stream().forEach( entry ->
                BlockRenderLayerMap.INSTANCE.putBlock(entry.get(), RenderLayer.getCutout()));
    }
}
