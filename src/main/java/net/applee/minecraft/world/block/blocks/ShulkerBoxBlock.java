package net.applee.minecraft.world.block.blocks;

import net.applee.minecraft.enums.DyeColor;
import net.applee.minecraft.world.block.Block;
import net.applee.minecraft.world.block.BlockSettings;
import net.applee.minecraft.world.block.BlockState;
import net.applee.minecraft.world.block.Blocks;
import net.applee.minecraft.world.block.piston.PistonBehavior;
import org.jetbrains.annotations.Nullable;

public class ShulkerBoxBlock extends Block {
    @Nullable
    private final DyeColor color;

    public ShulkerBoxBlock(@Nullable DyeColor color, BlockSettings settings) {
        super(settings);
        this.color = color;
    }

//    @Nullable
//    public static DyeColor getColor(Item item) {
//        return ShulkerBoxBlock.getColor(Block.getBlockFromItem(item));
//    }

    @Nullable
    public static DyeColor getColor(Block block) {
        if (block instanceof ShulkerBoxBlock) {
            return ((ShulkerBoxBlock) block).getColor();
        }
        return null;
    }

    public static Block get(@Nullable DyeColor dyeColor) {
        if (dyeColor == null) {
            return Blocks.SHULKER_BOX;
        }
        switch (dyeColor) {
            case WHITE: {
                return Blocks.WHITE_SHULKER_BOX;
            }
            case ORANGE: {
                return Blocks.ORANGE_SHULKER_BOX;
            }
            case MAGENTA: {
                return Blocks.MAGENTA_SHULKER_BOX;
            }
            case LIGHT_BLUE: {
                return Blocks.LIGHT_BLUE_SHULKER_BOX;
            }
            case YELLOW: {
                return Blocks.YELLOW_SHULKER_BOX;
            }
            case LIME: {
                return Blocks.LIME_SHULKER_BOX;
            }
            case PINK: {
                return Blocks.PINK_SHULKER_BOX;
            }
            case GRAY: {
                return Blocks.GRAY_SHULKER_BOX;
            }
            case LIGHT_GRAY: {
                return Blocks.LIGHT_GRAY_SHULKER_BOX;
            }
            case CYAN: {
                return Blocks.CYAN_SHULKER_BOX;
            }
            default: {
                return Blocks.PURPLE_SHULKER_BOX;
            }
            case BLUE: {
                return Blocks.BLUE_SHULKER_BOX;
            }
            case BROWN: {
                return Blocks.BROWN_SHULKER_BOX;
            }
            case GREEN: {
                return Blocks.GREEN_SHULKER_BOX;
            }
            case RED: {
                return Blocks.RED_SHULKER_BOX;
            }
            case BLACK:
        }
        return Blocks.BLACK_SHULKER_BOX;
    }

//    public static ItemStack getItemStack(@Nullable DyeColor color) {
//        return new ItemStack(ShulkerBoxBlock.get(color));
//    }

//    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
//        BlockEntity blockEntity = builder.getNullable(LootContextParameters.BLOCK_ENTITY);
//        if (blockEntity instanceof ShulkerBoxBlockEntity shulkerBoxBlockEntity) {
//            builder = builder.putDrop(CONTENTS, (context, consumer) -> {
//                for (int i = 0; i < shulkerBoxBlockEntity.size(); ++i) {
//                    consumer.accept(shulkerBoxBlockEntity.getStack(i));
//                }
//            });
//        }
//        return super.getDroppedStacks(state, builder);
//    }

//    public void onPlaced(World world, BlockPos pos, BlockState state, net.minecraft.entity.LivingEntity placer, ItemStack itemStack) {
//        BlockEntity blockEntity;
//        if (itemStack.hasCustomName() && (blockEntity = world.getBlockEntity(pos)) instanceof ShulkerBoxBlockEntity) {
//            ((ShulkerBoxBlockEntity) blockEntity).setCustomName(itemStack.getName());
//        }
//    }

//    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
//        if (state.isOf(newState.getBlock())) {
//            return;
//        }
//        BlockEntity blockEntity = world.getBlockEntity(pos);
//        if (blockEntity instanceof ShulkerBoxBlockEntity) {
//            world.updateComparators(pos, state.getBlock());
//        }
//        super.onStateReplaced(state, world, pos, newState, moved);
//    }

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

//    public ItemStack getPickStack(World world, BlockPos pos, BlockState state) {
//        ItemStack itemStack = super.getPickStack(world, pos, state);
//        world.getBlockEntity(pos, BlockEntityType.SHULKER_BOX).ifPresent(blockEntity -> blockEntity.setStackNbt(itemStack));
//        return itemStack;
//    }

    @Nullable
    public DyeColor getColor() {
        return this.color;
    }

}
