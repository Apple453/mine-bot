package net.applee.minecraft.utils.math;

public class Vec3d {
    public static final Vec3d ZERO = new Vec3d(0.0, 0.0, 0.0);
    public double x;
    public double y;
    public double z;

    public Vec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3d(Vec3d vec) {
        this(vec.x, vec.y, vec.z);
    }

    public Vec3d(Vec3f vec) {
        this(vec.getX(), vec.getY(), vec.getZ());
    }

    public static Vec3d unpackRgb(int rgb) {
        double d = (rgb >> 16 & 255) / 255.0;
        double e = (rgb >> 8 & 255) / 255.0;
        double f = (rgb & 255) / 255.0;
        return new Vec3d(d, e, f);
    }

    public static Vec3d ofCenter(Vec3i vec) {
        return new Vec3d(vec.getX() + 0.5, vec.getY() + 0.5, vec.getZ() + 0.5);
    }

    public static Vec3d of(Vec3i vec) {
        return new Vec3d(vec.getX(), vec.getY(), vec.getZ());
    }

    public static Vec3d ofBottomCenter(Vec3i vec) {
        return new Vec3d(vec.getX() + 0.5, vec.getY(), vec.getZ() + 0.5);
    }

    public static Vec3d ofCenter(Vec3i vec, double deltaY) {
        return new Vec3d(vec.getX() + 0.5, vec.getY() + deltaY, vec.getZ() + 0.5);
    }

    public Vec3d relativize(Vec3d vec) {
        return new Vec3d(vec.x - this.x, vec.y - this.y, vec.z - this.z);
    }

    public Vec3d round() {
        return new Vec3d(Math.round(this.x), Math.round(this.y), Math.round(this.z));
    }

    public Vec3d round(int places) {
        return new Vec3d(MathHelper.round(this.x, places), MathHelper.round(this.y, places), MathHelper.round(this.z, places));
    }

    public Vec3d normalize() {
        double d = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return d < 1.0E-4 ? ZERO : new Vec3d(this.x / d, this.y / d, this.z / d);
    }

    public double dotProduct(Vec3d vec) {
        return this.x * vec.x + this.y * vec.y + this.z * vec.z;
    }

    public Vec3d crossProduct(Vec3d vec) {
        return new Vec3d(this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z, this.x * vec.y - this.y * vec.x);
    }

    public Vec3d subtract(Vec3d vec) {
        return this.subtract(vec.x, vec.y, vec.z);
    }

    public Vec3d subtract(double x, double y, double z) {
        return this.add(-x, -y, -z);
    }

    public Vec3d add(Vec3d vec) {
        return this.add(vec.x, vec.y, vec.z);
    }

    public Vec3d add(double x, double y, double z) {
        return new Vec3d(this.x + x, this.y + y, this.z + z);
    }

    public boolean isInRange(Vec3d pos, double radius) {
        return this.squaredDistanceTo(pos.x, pos.y, pos.z) < radius * radius;
    }

    public Vec3d up() {
        return this.offset(Direction.UP);
    }

    public Vec3d up(double distance) {
        return this.offset(Direction.UP, distance);
    }

    public Vec3d down() {
        return this.offset(Direction.DOWN);
    }

    public Vec3d down(double i) {
        return this.offset(Direction.DOWN, i);
    }

    public Vec3d north() {
        return this.offset(Direction.NORTH);
    }

    public Vec3d north(double distance) {
        return this.offset(Direction.NORTH, distance);
    }

    public Vec3d south() {
        return this.offset(Direction.SOUTH);
    }

    public Vec3d south(double distance) {
        return this.offset(Direction.SOUTH, distance);
    }

    public Vec3d west() {
        return this.offset(Direction.WEST);
    }

    public Vec3d west(double distance) {
        return this.offset(Direction.WEST, distance);
    }

    public Vec3d east() {
        return this.offset(Direction.EAST);
    }

    public Vec3d east(double distance) {
        return this.offset(Direction.EAST, distance);
    }

    public Vec3d offset(Direction direction) {
        return new Vec3d(this.x + direction.getOffsetX(), this.y + direction.getOffsetY(), this.z + direction.getOffsetZ());
    }

    public Vec3d offset(Direction direction, double i) {
        return i == 0 ? this : new Vec3d(this.x + direction.getOffsetX() * i, this.y + direction.getOffsetY() * i, this.z + direction.getOffsetZ() * i);
    }

    public double distanceTo(Vec3d vec) {
        double d = vec.x - this.x;
        double e = vec.y - this.y;
        double f = vec.z - this.z;
        return Math.sqrt(d * d + e * e + f * f);
    }

    public double squaredDistanceTo(Vec3d vec) {
        double d = vec.x - this.x;
        double e = vec.y - this.y;
        double f = vec.z - this.z;
        return d * d + e * e + f * f;
    }

    public double squaredDistanceTo(double x, double y, double z) {
        double d = x - this.x;
        double e = y - this.y;
        double f = z - this.z;
        return d * d + e * e + f * f;
    }

    public Vec3d multiply(double value) {
        return this.multiply(value, value, value);
    }

    public Vec3d negate() {
        return this.multiply(-1.0);
    }

    public Vec3d multiply(Vec3d vec) {
        return this.multiply(vec.x, vec.y, vec.z);
    }

    public Vec3d multiply(double x, double y, double z) {
        return new Vec3d(this.x * x, this.y * y, this.z * z);
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public double horizontalLength() {
        return Math.sqrt(this.x * this.x + this.z * this.z);
    }

    public double horizontalLengthSquared() {
        return this.x * this.x + this.z * this.z;
    }

    public boolean equals(Object o) {
        if (o instanceof Vec3d vec3d) {
            if (Double.compare(vec3d.x, this.x) != 0) {
                return false;
            } else if (Double.compare(vec3d.y, this.y) != 0) {
                return false;
            } else {
                return Double.compare(vec3d.z, this.z) == 0;
            }
        } else return false;
    }

    public int hashCode() {
        long l = Double.doubleToLongBits(this.x);
        int i = (int) (l ^ l >>> 32);
        l = Double.doubleToLongBits(this.y);
        i = 31 * i + (int) (l ^ l >>> 32);
        l = Double.doubleToLongBits(this.z);
        i = 31 * i + (int) (l ^ l >>> 32);
        return i;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public Vec3d lerp(Vec3d to, double delta) {
        return new Vec3d(MathHelper.lerp(delta, this.x, to.x), MathHelper.lerp(delta, this.y, to.y), MathHelper.lerp(delta, this.z, to.z));
    }

    public Vec3f toVec3f() {
        return new Vec3f((float) x, (float) y, (float) z);
    }

//    public Vec3d rotateX(float angle) {
//        float f = MathHelper.cos(angle);
//        float g = MathHelper.sin(angle);
//        double d = this.x;
//        double e = this.y * (double)f + this.z * (double)g;
//        double h = this.z * (double)f - this.y * (double)g;
//        return new Vec3d(d, e, h);
//    }
//
//    public Vec3d rotateY(float angle) {
//        float f = MathHelper.cos(angle);
//        float g = MathHelper.sin(angle);
//        double d = this.x * (double)f + this.z * (double)g;
//        double e = this.y;
//        double h = this.z * (double)f - this.x * (double)g;
//        return new Vec3d(d, e, h);
//    }
//
//    public Vec3d rotateZ(float angle) {
//        float f = MathHelper.cos(angle);
//        float g = MathHelper.sin(angle);
//        double d = this.x * (double)f + this.y * (double)g;
//        double e = this.y * (double)f - this.x * (double)g;
//        double h = this.z;
//        return new Vec3d(d, e, h);
//    }

//    public static Vec3d fromPolar(Vec2f polar) {
//        return fromPolar(polar.x, polar.y);
//    }

//    public static Vec3d fromPolar(float pitch, float yaw) {
//        float f = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
//        float g = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
//        float h = -MathHelper.cos(-pitch * 0.017453292F);
//        float i = MathHelper.sin(-pitch * 0.017453292F);
//        return new Vec3d((double)(g * h), (double)i, (double)(f * h));
//    }

//    public Vec3d floorAlongAxes(EnumSet<Direction.Axis> axes) {
//        double d = axes.contains(Axis.X) ? (double)MathHelper.floor(this.x) : this.x;
//        double e = axes.contains(Axis.Y) ? (double)MathHelper.floor(this.y) : this.y;
//        double f = axes.contains(Axis.Z) ? (double)MathHelper.floor(this.z) : this.z;
//        return new Vec3d(d, e, f);
//    }

//    public double getComponentAlongAxis(Direction.Axis axis) {
//        return axis.choose(this.x, this.y, this.z);
//    }

//    public Vec3d withAxis(Direction.Axis axis, double value) {
//        double d = axis == Axis.X ? value : this.x;
//        double e = axis == Axis.Y ? value : this.y;
//        double f = axis == Axis.Z ? value : this.z;
//        return new Vec3d(d, e, f);
//    }

//    public Vec3d withBias(Direction direction, double value) {
//        Vec3i vec3i = direction.getVector();
//        return new Vec3d(this.x + value * (double)vec3i.getX(), this.y + value * (double)vec3i.getY(), this.z + value * (double)vec3i.getZ());
//    }

}
