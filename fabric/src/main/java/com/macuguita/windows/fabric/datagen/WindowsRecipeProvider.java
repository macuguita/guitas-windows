package com.macuguita.windows.fabric.datagen;

import com.macuguita.windows.reg.WindowsBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class WindowsRecipeProvider extends FabricRecipeProvider {
    public WindowsRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> consumer) {
        createWindowRecipe(Blocks.GLASS, Items.COPPER_INGOT, consumer);
    }

    private void createWindowRecipe(ItemConvertible glass, ItemConvertible rim, Consumer<RecipeJsonProvider> consumer) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, WindowsBlocks.WINDOW.get(), 16)
                .pattern(" # ")
                .pattern("#$#")
                .pattern(" # ")
                .input('#', rim)
                .input('$', glass)
                .criterion(RecipeProvider.hasItem(glass), RecipeProvider.conditionsFromItem(glass))
                .criterion(RecipeProvider.hasItem(rim), RecipeProvider.conditionsFromItem(rim))
                .offerTo(consumer);
    }
}
