package net.applee.minecraft.utils.math;

import net.applee.minecraft.utils.math.Direction.Axis;

public class BlockPos extends Vec3i {

    public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);
    private static final int SIZE_BITS_X = 1 + MathHelper.floorLog2(MathHelper.smallestEncompassingPowerOfTwo(30000000));
    private static final int SIZE_BITS_Z = SIZE_BITS_X;
    public static final int SIZE_BITS_Y = 64 - SIZE_BITS_X - SIZE_BITS_Z;
    private static final long BITS_Y = (1L << SIZE_BITS_Y) - 1L;
    private static final int BIT_SHIFT_Z = SIZE_BITS_Y;
    private static final int BIT_SHIFT_X = SIZE_BITS_Y + SIZE_BITS_Z;
    private static final long BITS_Z = (1L << SIZE_BITS_Z) - 1L;
    private static final long BITS_X = (1L << SIZE_BITS_X) - 1L;

    public BlockPos(int i, int j, int k) {
        super(i, j, k);
    }

    public BlockPos(double d, double e, double f) {
        super(d, e, f);
    }

    public BlockPos(Vec3d pos) {
        this(pos.x, pos.y, pos.z);
    }

    public BlockPos(Position pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }

    public BlockPos(Vec3i pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }

    public static long offset(long value, Direction direction) {
        return add(value, direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ());
    }

    public static long add(long value, int x, int y, int z) {
        return asLong(unpackLongX(value) + x, unpackLongY(value) + y, unpackLongZ(value) + z);
    }

    public static int unpackLongX(long packedPos) {
        return (int) (packedPos << 64 - BIT_SHIFT_X - SIZE_BITS_X >> 64 - SIZE_BITS_X);
    }

    public static int unpackLongY(long packedPos) {
        return (int) (packedPos << 64 - SIZE_BITS_Y >> 64 - SIZE_BITS_Y);
    }

    public static int unpackLongZ(long packedPos) {
        return (int) (packedPos << 64 - BIT_SHIFT_Z - SIZE_BITS_Z >> 64 - SIZE_BITS_Z);
    }

    public static BlockPos fromLong(long packedPos) {
        return new BlockPos(unpackLongX(packedPos), unpackLongY(packedPos), unpackLongZ(packedPos));
    }

    public static long asLong(int x, int y, int z) {
        long l = 0L;
        l |= ((long) x & BITS_X) << BIT_SHIFT_X;
        l |= ((long) y & BITS_Y);
        l |= ((long) z & BITS_Z) << BIT_SHIFT_Z;
        return l;
    }

    public static long removeChunkSectionLocalY(long y) {
        return y & -16L;
    }

    public long asLong() {
        return asLong(this.getX(), this.getY(), this.getZ());
    }

    public BlockPos add(double d, double e, double f) {
        return d == 0.0 && e == 0.0 && f == 0.0 ? this : new BlockPos((double) this.getX() + d, (double) this.getY() + e, (double) this.getZ() + f);
    }

    public BlockPos add(int i, int j, int k) {
        return i == 0 && j == 0 && k == 0 ? this : new BlockPos(this.getX() + i, this.getY() + j, this.getZ() + k);
    }

    public BlockPos add(Vec3i vec3i) {
        return this.add(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }

    public BlockPos subtract(Vec3i vec3i) {
        return this.add(-vec3i.getX(), -vec3i.getY(), -vec3i.getZ());
    }

    public BlockPos multiply(int i) {
        if (i == 1) {
            return this;
        } else {
            return i == 0 ? ORIGIN : new BlockPos(this.getX() * i, this.getY() * i, this.getZ() * i);
        }
    }

    public BlockPos up() {
        return this.offset(Direction.UP);
    }

    public BlockPos up(int distance) {
        return this.offset(Direction.UP, distance);
    }

    public BlockPos down() {
        return this.offset(Direction.DOWN);
    }

    public BlockPos down(int i) {
        return this.offset(Direction.DOWN, i);
    }

    public BlockPos north() {
        return this.offset(Direction.NORTH);
    }

    public BlockPos north(int distance) {
        return this.offset(Direction.NORTH, distance);
    }

    public BlockPos south() {
        return this.offset(Direction.SOUTH);
    }

    public BlockPos south(int distance) {
        return this.offset(Direction.SOUTH, distance);
    }

    public BlockPos west() {
        return this.offset(Direction.WEST);
    }

    public BlockPos west(int distance) {
        return this.offset(Direction.WEST, distance);
    }

    public BlockPos east() {
        return this.offset(Direction.EAST);
    }

    public BlockPos east(int distance) {
        return this.offset(Direction.EAST, distance);
    }

    public BlockPos offset(Direction direction) {
        return new BlockPos(this.getX() + direction.getOffsetX(), this.getY() + direction.getOffsetY(), this.getZ() + direction.getOffsetZ());
    }

    public BlockPos offset(Direction direction, int i) {
        return i == 0 ? this : new BlockPos(this.getX() + direction.getOffsetX() * i, this.getY() + direction.getOffsetY() * i, this.getZ() + direction.getOffsetZ() * i);
    }

    public BlockPos offset(Direction.Axis axis, int i) {
        if (i == 0) {
            return this;
        } else {
            int j = axis == Axis.X ? i : 0;
            int k = axis == Axis.Y ? i : 0;
            int l = axis == Axis.Z ? i : 0;
            return new BlockPos(this.getX() + j, this.getY() + k, this.getZ() + l);
        }
    }

    public BlockPos abs() {
        return new BlockPos(absX(), absY(), absZ());
    }

    public BlockPos crossProduct(Vec3i pos) {
        return new BlockPos(this.getY() * pos.getZ() - this.getZ() * pos.getY(), this.getZ() * pos.getX() - this.getX() * pos.getZ(), this.getX() * pos.getY() - this.getY() * pos.getX());
    }

    public BlockPos withY(int y) {
        return new BlockPos(this.getX(), y, this.getZ());
    }

    public BlockPos toImmutable() {
        return this;
    }

    public Mutable mutableCopy() {
        return new Mutable(this.getX(), this.getY(), this.getZ());
    }

    @Override
    public String toString() {
        return "(%s, %s, %s)".formatted(getX(), getY(), getZ());
    }

    public static class Mutable extends BlockPos {
        public Mutable() {
            this(0, 0, 0);
        }

        public Mutable(int i, int j, int k) {
            super(i, j, k);
        }

        public Mutable(double d, double e, double f) {
            this(MathHelper.floor(d), MathHelper.floor(e), MathHelper.floor(f));
        }

        public BlockPos add(double d, double e, double f) {
            return super.add(d, e, f).toImmutable();
        }

        public BlockPos add(int i, int j, int k) {
            return super.add(i, j, k).toImmutable();
        }

        public BlockPos multiply(int i) {
            return super.multiply(i).toImmutable();
        }

        public BlockPos offset(Direction direction, int i) {
            return super.offset(direction, i).toImmutable();
        }

        public BlockPos offset(Direction.Axis axis, int i) {
            return super.offset(axis, i).toImmutable();
        }

        public Mutable set(int x, int y, int z) {
            this.setX(x);
            this.setY(y);
            this.setZ(z);
            return this;
        }

        public Mutable set(double x, double y, double z) {
            return this.set(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
        }

        public Mutable set(Vec3i pos) {
            return this.set(pos.getX(), pos.getY(), pos.getZ());
        }

        public Mutable set(long pos) {
            return this.set(unpackLongX(pos), unpackLongY(pos), unpackLongZ(pos));
        }

        public Mutable set(Vec3i pos, Direction direction) {
            return this.set(pos.getX() + direction.getOffsetX(), pos.getY() + direction.getOffsetY(), pos.getZ() + direction.getOffsetZ());
        }

        public Mutable set(Vec3i pos, int x, int y, int z) {
            return this.set(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
        }

        public Mutable set(Vec3i vec1, Vec3i vec2) {
            return this.set(vec1.getX() + vec2.getX(), vec1.getY() + vec2.getY(), vec1.getZ() + vec2.getZ());
        }

        public Mutable move(Direction direction) {
            return this.move(direction, 1);
        }

        public Mutable move(Direction direction, int distance) {
            return this.set(this.getX() + direction.getOffsetX() * distance, this.getY() + direction.getOffsetY() * distance, this.getZ() + direction.getOffsetZ() * distance);
        }

        public Mutable move(int dx, int dy, int dz) {
            return this.set(this.getX() + dx, this.getY() + dy, this.getZ() + dz);
        }

        public Mutable move(Vec3i vec) {
            return this.set(this.getX() + vec.getX(), this.getY() + vec.getY(), this.getZ() + vec.getZ());
        }

        public Mutable clamp(Direction.Axis axis, int min, int max) {
            switch (axis) {
                case X:
                    return this.set(MathHelper.clamp(this.getX(), min, max), this.getY(), this.getZ());
                case Y:
                    return this.set(this.getX(), MathHelper.clamp(this.getY(), min, max), this.getZ());
                case Z:
                    return this.set(this.getX(), this.getY(), MathHelper.clamp(this.getZ(), min, max));
                default:
                    throw new IllegalStateException("Unable to clamp axis " + axis);
            }
        }

        public Mutable setX(int i) {
            super.setX(i);
            return this;
        }

        public Mutable setY(int i) {
            super.setY(i);
            return this;
        }

        public Mutable setZ(int i) {
            super.setZ(i);
            return this;
        }

        public BlockPos toImmutable() {
            return new BlockPos(this);
        }
    }
}
