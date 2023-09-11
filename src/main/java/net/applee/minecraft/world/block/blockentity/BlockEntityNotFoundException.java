package net.applee.minecraft.world.block.blockentity;

public class BlockEntityNotFoundException extends RuntimeException {

    public BlockEntityNotFoundException(int id) {
        super("Block entity with id %s not found.".formatted(id));
    }
}
