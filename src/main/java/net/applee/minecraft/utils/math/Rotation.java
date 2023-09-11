package net.applee.minecraft.utils.math;

public class Rotation {
    public static final Rotation ZERO = new Rotation(0, 0);

    public static final int MAX_YAW = 180;
    public static final int MIN_YAW = -180;
    public static final int MAX_PITCH = 90;
    public static final int MIN_PITCH = -90;

    public static final double DIRECTION_YAW_SIZE = (double) 360 / 8;

    private double pitch;
    private double yaw;

    public Rotation(byte yaw, byte pitch) {
        this.yaw = (float) (yaw * 360) / 256.0f;
        this.pitch = (float) (pitch * 360) / 256.0f;
    }

    public Rotation(double yaw, double pitch) {
        this.pitch = (float) pitch;
        this.yaw = (float) yaw;
    }

    public Rotation(Rotation rotation) {
        this.pitch = (float) rotation.pitch;
        this.yaw = (float) rotation.yaw;
    }

    public double getPitch() {
        return pitch;
    }

    public double getYaw() {
        return yaw;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public Rotation addRotation(Rotation rotation) {
        double pitch0 = pitch + rotation.pitch;
        double yaw0 = yaw + rotation.yaw;
        return correctRotation(new Rotation(yaw0, pitch0));
    }

    public Rotation addPitch(double add) {
        double pitch0 = pitch + add;
        return correctRotation(new Rotation(yaw, pitch0));
    }

    public Rotation addYaw(double add) {
        double yaw0 = yaw + add;
        return correctRotation(new Rotation(yaw0, pitch));
    }

    public Rotation subtractRotation(Rotation rotation) {
        double pitch0 = pitch - rotation.pitch;
        double yaw0 = yaw - rotation.yaw;
        return correctRotation(new Rotation(yaw0, pitch0));
    }

    public Rotation subtractPitch(double add) {
        double pitch0 = pitch - add;
        return correctRotation(new Rotation(yaw, pitch0));
    }

    public Rotation subtractYaw(double add) {
        double yaw0 = yaw - add;
        return correctRotation(new Rotation(yaw0, pitch));
    }

    public Direction getDirection() {
        return Direction.fromRotation(yaw);
    }

    private Rotation correctRotation(Rotation rotation) {
        double yaw;
        double pitch;

        if (rotation.pitch > MAX_PITCH) pitch = MAX_PITCH;
        else if (rotation.pitch < MIN_PITCH) pitch = MIN_PITCH;
        else pitch = rotation.pitch;

        if (rotation.yaw > MAX_YAW) yaw = MIN_YAW;
        else if (rotation.yaw < MIN_YAW) yaw = MAX_YAW;
        else yaw = rotation.yaw;

        return new Rotation(yaw, pitch);
    }


    @Override
    public String toString() {
        return "Rotation{" + "yaw=" + yaw + ", pitch=" + pitch + '}';
    }


//    public void lookAt(BlockPos pos) {
//
//        dx = x-x0
//        dy = y-y0
//        dz = z-z0
//        r = sqrt( dx*dx + dy*dy + dz*dz )
//        yaw = -atan2(dx,dz)/PI*180
//        if yaw < 0 then
//                yaw = 360 + yaw
//        pitch = -arcsin(dy/r)/PI*180
//    }
}
