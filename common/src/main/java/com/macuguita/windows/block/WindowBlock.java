package com.macuguita.windows.block;

import com.macuguita.windows.reg.WindowsTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

@SuppressWarnings({"deprecation", "extract"})
public class WindowBlock extends Block implements Waterloggable {
    // Use a FACING property to determine the block's orientation
    public static final DirectionProperty FACING = Properties.FACING;

    // Waterlogged property
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    // Voxel shapes for each face
    private static final VoxelShape UP_SHAPE = Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape DOWN_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);

    public WindowBlock(Settings settings) {
        super(settings);
        // Set the default state with FACING and WATERLOGGED
        this.setDefaultState(this.getDefaultState()
                .with(FACING, Direction.NORTH) // Default facing direction
                .with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        // Add FACING and WATERLOGGED to the block state
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        Direction lookDirection = ctx.getPlayerLookDirection();
        BlockPos pos = ctx.getBlockPos();
        FluidState fluidState = world.getFluidState(pos);
        Vec3d hitPos = ctx.getHitPos().subtract(pos.getX(), pos.getY(), pos.getZ());

        Direction facing;
            facing = switch (lookDirection) {
                case NORTH -> (hitPos.z < 0.5) ? Direction.SOUTH : Direction.NORTH;
                case SOUTH -> (hitPos.z > 0.5) ? Direction.NORTH : Direction.SOUTH;
                case EAST -> (hitPos.x > 0.5) ? Direction.WEST : Direction.EAST;
                case WEST -> (hitPos.x < 0.5) ? Direction.EAST : Direction.WEST ;
                case UP -> (hitPos.y > 0.5)? Direction.UP : Direction.DOWN;
                case DOWN -> (hitPos.y < 0.5)? Direction.DOWN : Direction.UP;
        };

        return this.getDefaultState()
                .with(FACING, facing)
                .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Return the shape based on the FACING direction
        Direction facing = state.get(FACING);
        return switch (facing) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            case UP -> UP_SHAPE;
            case DOWN -> DOWN_SHAPE;
        };
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState neighborState, Direction direction) {
        // Check if both blocks are in the WINDOWS tag
        if ((state.isIn(WindowsTags.Blocks.WINDOWS) && neighborState.isIn(WindowsTags.Blocks.WINDOWS))) {
            Direction facing = state.get(FACING); // Facing direction of the original block
            Direction neighborFacing = neighborState.get(FACING); // Facing direction of the neighboring block

            // Check if the neighboring block is in the same direction as the original block's facing
            if ((direction == facing || direction == facing.getOpposite()) && neighborFacing == facing) {
                return false;
            }
            return true;
        }
        return super.isSideInvisible(state, neighborState, direction);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        // Handle waterlogging
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return state;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
}