package net.applee.minecraft.utils.math;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public enum Direction {
    DOWN(0, 1, -1, "down", Direction.AxisDirection.NEGATIVE, Direction.Axis.Y, new Vec3i(0, -1, 0)),
    UP(1, 0, -1, "up", Direction.AxisDirection.POSITIVE, Direction.Axis.Y, new Vec3i(0, 1, 0)),
    NORTH(2, 3, 2, "north", Direction.AxisDirection.NEGATIVE, Direction.Axis.Z, new Vec3i(0, 0, -1)),
    SOUTH(3, 2, 0, "south", Direction.AxisDirection.POSITIVE, Direction.Axis.Z, new Vec3i(0, 0, 1)),
    WEST(4, 5, 1, "west", Direction.AxisDirection.NEGATIVE, Direction.Axis.X, new Vec3i(-1, 0, 0)),
    EAST(5, 4, 3, "east", Direction.AxisDirection.POSITIVE, Direction.Axis.X, new Vec3i(1, 0, 0));

    private static final Direction[] ALL = values();
    private static final Direction[] VALUES = Arrays.stream(ALL).sorted(Comparator.comparingInt((direction) -> direction.id)).toArray(Direction[]::new);
    private static final Direction[] HORIZONTAL = Arrays.stream(ALL).filter((direction) -> direction.getAxis().isHorizontal()).sorted(Comparator.comparingInt((direction) -> direction.idHorizontal)).toArray(Direction[]::new);
    private final int id;
    private final int idOpposite;
    private final int idHorizontal;
    private final String name;
    private final Axis axis;
    private final AxisDirection direction;
    private final Vec3i vector;
//    private static final Long2ObjectMap<Direction> VECTOR_TO_DIRECTION = (Long2ObjectMap)Arrays.stream(ALL).collect(Collectors.toMap((direction) -> (new BlockPos(direction.getVector())).asLong(), (direction) -> direction, (direction1, direction2) -> {
//        throw new IllegalArgumentException("Duplicate keys");
//    }, Long2ObjectOpenHashMap::new));

    Direction(int id, int idOpposite, int idHorizontal, String name, AxisDirection direction, Axis axis, Vec3i vector) {
        this.id = id;
        this.idHorizontal = idHorizontal;
        this.idOpposite = idOpposite;
        this.name = name;
        this.axis = axis;
        this.direction = direction;
        this.vector = vector;
    }

//    public static Direction[] getEntityFacingOrder(Entity entity) {
//        float f = entity.getPitch(1.0F) * 0.017453292F;
//        float g = -entity.getYaw(1.0F) * 0.017453292F;
//        float h = MathHelper.sin(f);
//        float i = MathHelper.cos(f);
//        float j = MathHelper.sin(g);
//        float k = MathHelper.cos(g);
//        boolean bl = j > 0.0F;
//        boolean bl2 = h < 0.0F;
//        boolean bl3 = k > 0.0F;
//        float l = bl ? j : -j;
//        float m = bl2 ? -h : h;
//        float n = bl3 ? k : -k;
//        float o = l * i;
//        float p = n * i;
//        Direction direction = bl ? EAST : WEST;
//        Direction direction2 = bl2 ? UP : DOWN;
//        Direction direction3 = bl3 ? SOUTH : NORTH;
//        if (l > n) {
//            if (m > o) return listClosest(direction2, direction, direction3);
//            else return p > m ? listClosest(direction, direction3, direction2) : listClosest(direction, direction2, direction3);
//        } else if (m > p) return listClosest(direction2, direction3, direction);
//        else return o > m ? listClosest(direction3, direction, direction2) : listClosest(direction3, direction2, direction);
//    }

    private static Direction[] listClosest(Direction first, Direction second, Direction third) {
        return new Direction[]{first, second, third, third.getOpposite(), second.getOpposite(), first.getOpposite()};
    }

    public static Stream<Direction> stream() {
        return Stream.of(ALL);
    }

//    public Quaternion getRotationQuaternion() {
//        Quaternion quaternion = Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F);
//        Quaternion var10000;
//        switch (this) {
//            case DOWN -> var10000 = Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F);
//            case UP -> var10000 = Quaternion.IDENTITY.copy();
//            case NORTH -> {
//                quaternion.hamiltonProduct(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
//                var10000 = quaternion;
//            }
//            case SOUTH -> var10000 = quaternion;
//            case WEST -> {
//                quaternion.hamiltonProduct(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
//                var10000 = quaternion;
//            }
//            case EAST -> {
//                quaternion.hamiltonProduct(Vec3f.POSITIVE_Z.getDegreesQuaternion(-90.0F));
//                var10000 = quaternion;
//            }
//            default -> throw new IncompatibleClassChangeError();
//        }
//
//        return var10000;
//    }

    public static Direction byId(int id) {
        return VALUES[MathHelper.abs(id % VALUES.length)];
    }

    public static Direction fromHorizontal(int value) {
        return HORIZONTAL[MathHelper.abs(value % HORIZONTAL.length)];
    }

    public static Direction fromRotation(double rotation) {
        return fromHorizontal(MathHelper.floor(rotation / 90.0 + 0.5) & 3);
    }

//    public static Direction getLookDirectionForAxis(Entity entity, Axis axis) {
//        return switch (axis) {
//            case X -> EAST.pointsTo(entity.getYaw(1.0F)) ? EAST : WEST;
//            case Z -> SOUTH.pointsTo(entity.getYaw(1.0F)) ? SOUTH : NORTH;
//            case Y -> entity.getPitch(1.0F) < 0.0F ? UP : DOWN;
//        };
//    }

    public static Direction from(Axis axis, AxisDirection direction) {
        return switch (axis) {
            case X -> direction == AxisDirection.POSITIVE ? EAST : WEST;
            case Z -> direction == AxisDirection.POSITIVE ? SOUTH : NORTH;
            case Y -> direction == AxisDirection.POSITIVE ? UP : DOWN;
        };
    }

    public static Direction getFacing(double x, double y, double z) {
        return getFacing((float) x, (float) y, (float) z);
    }

    public static Direction getFacing(float x, float y, float z) {
        Direction direction = NORTH;
        float f = Float.MIN_VALUE;

        for (Direction direction2 : ALL) {
            float g = x * (float) direction2.vector.getX() + y * (float) direction2.vector.getY() + z * (float) direction2.vector.getZ();
            if (g > f) {
                f = g;
                direction = direction2;
            }
        }

        return direction;
    }

    public static Direction get(AxisDirection direction, Axis axis) {
        for (Direction direction2 : ALL) {
            if (direction2.getDirection() == direction && direction2.getAxis() == axis) {
                return direction2;
            }
        }

        throw new IllegalArgumentException("No such direction: " + direction + " " + axis);
    }

    public int getId() {
        return this.id;
    }

    public int getHorizontal() {
        return this.idHorizontal;
    }

    public AxisDirection getDirection() {
        return this.direction;
    }

    public Direction getOpposite() {
        return byId(this.idOpposite);
    }

    public Direction rotateClockwise(Axis axis) {
        return switch (axis) {
            case X -> this != WEST && this != EAST ? this.rotateXClockwise() : this;
            case Z -> this != NORTH && this != SOUTH ? this.rotateZClockwise() : this;
            case Y -> this != UP && this != DOWN ? this.rotateYClockwise() : this;
        };
    }

    public Direction rotateCounterclockwise(Axis axis) {
        return switch (axis) {
            case X -> this != WEST && this != EAST ? this.rotateXCounterclockwise() : this;
            case Z -> this != NORTH && this != SOUTH ? this.rotateZCounterclockwise() : this;
            case Y -> this != UP && this != DOWN ? this.rotateYCounterclockwise() : this;
        };
    }

    public Direction rotateYClockwise() {
        return switch (this) {
            case NORTH -> EAST;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            case EAST -> SOUTH;
            default -> throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        };
    }

    private Direction rotateXClockwise() {
        return switch (this) {
            case DOWN -> SOUTH;
            case UP -> NORTH;
            case NORTH -> DOWN;
            case SOUTH -> UP;
            default -> throw new IllegalStateException("Unable to get X-rotated facing of " + this);
        };
    }

    private Direction rotateXCounterclockwise() {
        return switch (this) {
            case DOWN -> NORTH;
            case UP -> SOUTH;
            case NORTH -> UP;
            case SOUTH -> DOWN;
            default -> throw new IllegalStateException("Unable to get X-rotated facing of " + this);
        };
    }

    private Direction rotateZClockwise() {
        return switch (this) {
            case DOWN -> WEST;
            case UP -> EAST;
            case WEST -> UP;
            case EAST -> DOWN;
            default -> throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
        };
    }

    private Direction rotateZCounterclockwise() {
        return switch (this) {
            case DOWN -> EAST;
            case UP -> WEST;
            case WEST -> DOWN;
            case EAST -> UP;
            default -> throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
        };
    }

    public Direction rotateYCounterclockwise() {
        return switch (this) {
            case NORTH -> WEST;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
            case EAST -> NORTH;
            default -> throw new IllegalStateException("Unable to get CCW facing of " + this);
        };
    }

    public int getOffsetX() {
        return this.vector.getX();
    }

//    public static @NotNull Direction fromVector(BlockPos pos) {
//        return VECTOR_TO_DIRECTION.get(pos.asLong());
//    }

//    public static @NotNull Direction fromVector(int x, int y, int z) {
//        return VECTOR_TO_DIRECTION.get(BlockPos.asLong(x, y, z));
//    }

    public int getOffsetY() {
        return this.vector.getY();
    }

    public int getOffsetZ() {
        return this.vector.getZ();
    }

    public Vec3f getUnitVector() {
        return new Vec3f(this.getOffsetX(), this.getOffsetY(), this.getOffsetZ());
    }

    public String getName() {
        return this.name;
    }

    public Axis getAxis() {
        return this.axis;
    }

    public float asRotation() {
        return (float) ((this.idHorizontal & 3) * 90);
    }

    public String toString() {
        return this.name;
    }

//    private static DataResult<Direction> validateVertical(Direction direction) {
//        return direction.getAxis().isVertical() ? DataResult.success(direction) : DataResult.error("Expected a vertical direction");
//    }

    public String asString() {
        return this.name;
    }

    public Vec3i getVector() {
        return this.vector;
    }

//    public boolean pointsTo(float yaw) {
//        float f = yaw * 0.017453292F;
//        float g = -MathHelper.sin(f);
//        float h = MathHelper.cos(f);
//        return (float)this.vector.getX() * g + (float)this.vector.getZ() * h > 0.0F;
//    }

    public enum Axis implements Predicate<Direction> {
        X("x") {
            public int choose(int x, int y, int z) {
                return x;
            }

            public double choose(double x, double y, double z) {
                return x;
            }
        },
        Y("y") {
            public int choose(int x, int y, int z) {
                return y;
            }

            public double choose(double x, double y, double z) {
                return y;
            }
        },
        Z("z") {
            public int choose(int x, int y, int z) {
                return z;
            }

            public double choose(double x, double y, double z) {
                return z;
            }
        };

        public static final Axis[] VALUES = values();
        private final String name;

        Axis(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public boolean isVertical() {
            return this == Y;
        }

        public boolean isHorizontal() {
            return this == X || this == Z;
        }

        public String toString() {
            return this.name;
        }

        public boolean test(@Nullable Direction direction) {
            return direction != null && direction.getAxis() == this;
        }

        public Type getType() {
            Type var10000;
            switch (this) {
                case X:
                case Z:
                    var10000 = Direction.Type.HORIZONTAL;
                    break;
                case Y:
                    var10000 = Direction.Type.VERTICAL;
                    break;
                default:
                    throw new IncompatibleClassChangeError();
            }

            return var10000;
        }

        public String asString() {
            return this.name;
        }

        public abstract int choose(int x, int y, int z);

        public abstract double choose(double x, double y, double z);
    }

    public enum AxisDirection {
        POSITIVE(1, "Towards positive"),
        NEGATIVE(-1, "Towards negative");

        private final int offset;
        private final String description;

        AxisDirection(int offset, String description) {
            this.offset = offset;
            this.description = description;
        }

        public int offset() {
            return this.offset;
        }

        public String getDescription() {
            return this.description;
        }

        public String toString() {
            return this.description;
        }

        public AxisDirection getOpposite() {
            return this == POSITIVE ? NEGATIVE : POSITIVE;
        }
    }

    public enum Type implements Iterable<Direction>, Predicate<Direction> {
        HORIZONTAL(new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}, new Axis[]{Direction.Axis.X, Direction.Axis.Z}),
        VERTICAL(new Direction[]{Direction.UP, Direction.DOWN}, new Axis[]{Direction.Axis.Y});

        private final Direction[] facingArray;
        private final Axis[] axisArray;

        Type(Direction[] facingArray, Axis[] axisArray) {
            this.facingArray = facingArray;
            this.axisArray = axisArray;
        }

        public boolean test(@Nullable Direction direction) {
            return direction != null && direction.getAxis().getType() == this;
        }

        public Stream<Direction> stream() {
            return Arrays.stream(this.facingArray);
        }

        @Override
        public Iterator<Direction> iterator() {
            return this.stream().iterator();
        }

        @Override
        public void forEach(Consumer<? super Direction> action) {
            Iterable.super.forEach(action);
        }

        @Override
        public Spliterator<Direction> spliterator() {
            return Iterable.super.spliterator();
        }
    }
}

