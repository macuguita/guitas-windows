package com.macuguita.windows.reg;

import com.macuguita.lib.platform.registry.GuitaRegistries;
import com.macuguita.lib.platform.registry.GuitaRegistry;
import com.macuguita.lib.platform.registry.GuitaRegistryEntry;
import com.macuguita.windows.Windows;
import com.macuguita.windows.block.WindowBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GlassBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

import java.util.function.Supplier;

public class WindowsBlocks {

    public static final GuitaRegistry<Block> BLOCKS = GuitaRegistries.create(Registries.BLOCK, Windows.MOD_ID);
    public static final GuitaRegistry<Item> ITEMS = GuitaRegistries.create(Registries.ITEM, Windows.MOD_ID);

    public static final GuitaRegistryEntry<Block> WINDOW = registerWithItem("window", () -> new WindowBlock(AbstractBlock.Settings.copy(Blocks.GLASS).nonOpaque()));
    public static final GuitaRegistryEntry<Block> AQUARIUM_GLASS = registerWithItem("aquarium_glass", () -> new GlassBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));

    public static <T extends Block> GuitaRegistryEntry<T> registerWithItem(String name, Supplier<T> block) {
        GuitaRegistryEntry<T> toReturn = BLOCKS.register(name, block);
        ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Settings()));
        return toReturn;
    }

    public static void init() {
        BLOCKS.init();
        ITEMS.init();
        Windows.LOGGER.info("Registering mod blocks for " + Windows.MOD_ID);
    }
}
