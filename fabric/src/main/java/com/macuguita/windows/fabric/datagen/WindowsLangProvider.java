package com.macuguita.windows.fabric.datagen;

import com.macuguita.windows.reg.WindowsBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

import java.util.Locale;

public class WindowsLangProvider extends FabricLanguageProvider {
    public WindowsLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        generateBlockTranslations(WindowsBlocks.WINDOW.get(), translationBuilder);
        generateBlockTranslations(WindowsBlocks.AQUARIUM_GLASS.get(), translationBuilder);
    }

    private String capitalizeString(String string) {
        char[] chars = string.toLowerCase(Locale.getDefault()).toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') {
                found = false;
            }
        }
        return new String(chars);
    }

    private void generateBlockTranslations(Block block, TranslationBuilder translationBuilder) {
        String temp = capitalizeString(Registries.BLOCK.getId(block).getPath().replace("_", " "));
        translationBuilder.add(block, temp);
    }

    private void generateItemTranslations(Item item, TranslationBuilder translationBuilder) {
        String temp = capitalizeString(Registries.ITEM.getId(item).getPath().replace("_", " "));
        translationBuilder.add(item, temp);
    }
}
