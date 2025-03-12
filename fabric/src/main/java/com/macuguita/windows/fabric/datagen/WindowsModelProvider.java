package com.macuguita.windows.fabric.datagen;

import com.macuguita.windows.Windows;
import com.macuguita.windows.reg.WindowsBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Optional;

public class WindowsModelProvider extends FabricModelProvider {

    public WindowsModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        // Register the window block model
        registerWindowModel(generator, WindowsBlocks.WINDOW.get());
        registerTransparentBlock(generator, WindowsBlocks.AQUARIUM_GLASS.get());
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(WindowsBlocks.WINDOW.get().asItem(), Models.GENERATED);
    }

    private void registerTransparentBlock(BlockStateModelGenerator generator, Block block) {
        // Get block name dynamically
        String blockName = Registries.BLOCK.getId(block).getPath();

        // Define the model with a dynamically generated path
        Model transparentBlockModel = new Model(
                Optional.of(Windows.id("block/transparent_block_template")), // Use template model
                Optional.empty(), // No suffix needed
                TextureKey.ALL // Single texture mapping
        );

        // Upload the model with a texture specific to the block
        Identifier modelId = transparentBlockModel.upload(block, new TextureMap().put(TextureKey.ALL, Windows.id("block/" + blockName)), generator.modelCollector);

        // Register block state definition using the uploaded model
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, modelId)));

        // Register the corresponding item model
        generator.registerParentedItemModel(block, modelId);
    }

    private void registerWindowModel(BlockStateModelGenerator generator, Block windowBlock) {
        // Get block name dynamically
        String blockName = Registries.BLOCK.getId(windowBlock).getPath();

        // Define the model for the window block
        Model windowModel = new Model(
                Optional.of(Windows.id("block/window_template")), // Hardcoded template model
                Optional.empty(), // No suffix needed
                TextureKey.FRONT, TextureKey.SIDE, TextureKey.PARTICLE // Texture keys used in the model
        );

        // Upload the model with dynamically generated texture names
        Identifier modelId = windowModel.upload(windowBlock, new TextureMap()
                        .put(TextureKey.FRONT, Windows.id("block/" + blockName + "_front"))
                        .put(TextureKey.SIDE, Windows.id("block/" + blockName + "_side"))
                        .put(TextureKey.PARTICLE, Windows.id("block/" + blockName + "_front")), // Use the front texture for particles
                generator.modelCollector
        );

        // Create a multipart block state supplier
        MultipartBlockStateSupplier blockStateSupplier = MultipartBlockStateSupplier.create(windowBlock);

        // Add a variant for each direction
        for (Direction direction : Direction.values()) {
            blockStateSupplier.with(
                    When.create().set(Properties.FACING, direction), // Use FACING property
                    rotateModel(BlockStateVariant.create().put(VariantSettings.MODEL, modelId), direction)
            );
        }

        // Register the block state
        generator.blockStateCollector.accept(blockStateSupplier);
    }

    private BlockStateVariant rotateModel(BlockStateVariant variant, Direction direction) {
        return switch (direction) {
            case EAST -> variant.put(VariantSettings.Y, VariantSettings.Rotation.R270);
            case NORTH -> variant.put(VariantSettings.Y, VariantSettings.Rotation.R180);
            case WEST -> variant.put(VariantSettings.Y, VariantSettings.Rotation.R90);
            case UP -> variant.put(VariantSettings.X, VariantSettings.Rotation.R270);
            case DOWN -> variant.put(VariantSettings.X, VariantSettings.Rotation.R90);
            default -> variant;
        };
    }

}