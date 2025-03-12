package com.macuguita.windows.mixin;

import com.macuguita.windows.reg.WindowsTags;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin {

    @Inject(method = "shouldRenderSide",
            at = @At("HEAD"),
            cancellable = true)
    private static void shouldRenderSide(BlockRenderView world, BlockPos pos, FluidState fluidState, BlockState blockState, Direction direction, FluidState neighborFluidState, CallbackInfoReturnable<Boolean> cir) {
        if (!fluidState.isIn(FluidTags.WATER)) return;

        for (boolean isOpposite : new boolean[]{false, true}) {
            BlockPos checkPos = pos.offset(isOpposite ? direction : direction.getOpposite());
            BlockState checkState = world.getBlockState(checkPos);

            if (checkState.isIn(WindowsTags.Blocks.AQUARIUM_GLASS)) {
                if (!checkState.contains(Properties.FACING) || checkState.get(Properties.FACING) == (isOpposite ? direction : direction.getOpposite())) {
                    cir.setReturnValue(false);
                    return;
                }
            }
        }
    }
}
