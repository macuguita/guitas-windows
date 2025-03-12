package com.macuguita.windows.reg;

import com.macuguita.windows.Windows;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class WindowsTags {

    public class Blocks {

        public static final TagKey<Block> WINDOWS = createTag("windows");
        public static final TagKey<Block> AQUARIUM_GLASS = createTag("aquarium_glass");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Windows.id(name));
        }
    }

    public class Items {

        public static final TagKey<Item> WINDOWS = createTag("windows");
        public static final TagKey<Item> AQUARIUM_GLASS = createTag("aquarium_glass");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Windows.id(name));
        }
    }
}
