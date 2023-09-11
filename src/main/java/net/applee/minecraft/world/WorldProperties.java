package net.applee.minecraft.world;

import net.applee.minecraft.utils.math.BlockPos;
import net.applee.minecraft.utils.math.Box;
import net.applee.minecraft.utils.math.ChunkPos;
import net.applee.minecraft.utils.math.MathHelper;

public class WorldProperties {
    public BlockPos spawnPos;
    public Border worldBorder;

    public static class Border {
        public int maxRadius = 0;
        public double worldSize = 0;

        public int warningTime;
        public int warningBlocks;
        public double centerX;
        public double centerZ;
        public double sizeLerpTarget;
        public long sizeLerpTime;

        double boundWest = MathHelper.clamp(centerX - worldSize / 2.0, -maxRadius, maxRadius);
        double boundNorth = MathHelper.clamp(centerZ - worldSize / 2.0, -maxRadius, maxRadius);
        double boundEast = MathHelper.clamp(centerX + worldSize / 2.0, -maxRadius, maxRadius);
        double boundSouth = MathHelper.clamp(centerZ + worldSize / 2.0, -maxRadius, maxRadius);

        public boolean contains(BlockPos pos) {
            return (pos.getX() + 1) > boundWest &&
                    pos.getX() < boundEast &&
                    (pos.getZ() + 1) > boundNorth &&
                    pos.getZ() < boundSouth;
        }

        public boolean contains(ChunkPos pos) {
            return pos.getEndX() > boundWest &&
                    pos.getStartX() < boundEast &&
                    pos.getEndZ() > boundNorth &&
                    pos.getStartZ() < boundSouth;
        }

        public boolean contains(double x, double z) {
            return x > boundWest &&
                    x < boundEast &&
                    z > boundNorth &&
                    z < boundSouth;
        }

        public boolean contains(double x, double z, double margin) {
            return x > boundWest - margin &&
                    x < boundEast + margin &&
                    z > boundNorth - margin &&
                    z < boundSouth + margin;
        }

        public boolean contains(Box box) {
            return box.maxX > boundWest &&
                    box.minX < boundEast &&
                    box.maxZ > boundNorth &&
                    box.minZ < boundSouth;
        }


    }
}
