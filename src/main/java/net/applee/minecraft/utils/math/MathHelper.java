package net.applee.minecraft.utils.math;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class MathHelper {
    public static final float PI = 3.1415927F;
    public static final float HALF_PI = 1.5707964F;
    public static final float TAU = 6.2831855F;
    public static final float RADIANS_PER_DEGREE = 0.017453292F;
    public static final float DEGREES_PER_RADIAN = 57.295776F;
    public static final float EPSILON = 1.0E-5F;
    public static final float SQUARE_ROOT_OF_TWO = sqrt(2.0F);
    private static final int field_29850 = 1024;
    private static final float field_29851 = 1024.0F;
    private static final long field_29852 = 61440L;
    private static final long HALF_PI_RADIANS_SINE_TABLE_INDEX = 16384L;
    private static final long field_29854 = -4611686018427387904L;
    private static final long field_29855 = Long.MIN_VALUE;
    private static final float DEGREES_TO_SINE_TABLE_INDEX = 10430.378F;

    private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
    private static final double field_29857 = 0.16666666666666666;
    private static final int field_29858 = 8;
    private static final int field_29859 = 257;
    private static final double SMALLEST_FRACTION_FREE_DOUBLE = Double.longBitsToDouble(4805340802404319232L);
    private static final double[] ARCSINE_TABLE = new double[257];
    private static final double[] COSINE_TABLE = new double[257];

    static {
        for (int i = 0; i < 257; ++i) {
            double d = (double) i / 256.0;
            double e = Math.asin(d);
            COSINE_TABLE[i] = Math.cos(e);
            ARCSINE_TABLE[i] = e;
        }

    }

    public MathHelper() {
    }

    public static float sqrt(float value) {
        return (float) Math.sqrt(value);
    }

    public static int floor(float value) {
        int i = (int) value;
        return value < (float) i ? i - 1 : i;
    }

    public static int fastFloor(double value) {
        return (int) (value + 1024.0) - 1024;
    }

    public static int floor(double value) {
        int i = (int) value;
        return value < (double) i ? i - 1 : i;
    }

    public static long lfloor(double value) {
        long l = (long) value;
        return value < (double) l ? l - 1L : l;
    }

    public static int absFloor(double value) {
        return (int) (value >= 0.0 ? value : -value + 1.0);
    }

    public static float abs(float value) {
        return Math.abs(value);
    }

    public static int abs(int value) {
        return Math.abs(value);
    }

    public static int ceil(float value) {
        int i = (int) value;
        return value > (float) i ? i + 1 : i;
    }

    public static int ceil(double value) {
        int i = (int) value;
        return value > (double) i ? i + 1 : i;
    }

    public static byte clamp(byte value, byte min, byte max) {
        if (value < min) {
            return min;
        } else {
            return value > max ? max : value;
        }
    }

    public static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        } else {
            return Math.min(value, max);
        }
    }

    public static long clamp(long value, long min, long max) {
        if (value < min) {
            return min;
        } else {
            return Math.min(value, max);
        }
    }

    public static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        } else {
            return Math.min(value, max);
        }
    }

    public static double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        } else {
            return Math.min(value, max);
        }
    }

    public static double clampedLerp(double start, double end, double delta) {
        if (delta < 0.0) {
            return start;
        } else {
            return delta > 1.0 ? end : lerp(delta, start, end);
        }
    }

    public static float clampedLerp(float start, float end, float delta) {
        if (delta < 0.0F) {
            return start;
        } else {
            return delta > 1.0F ? end : lerp(delta, start, end);
        }
    }

    public static double absMax(double a, double b) {
        if (a < 0.0) {
            a = -a;
        }

        if (b < 0.0) {
            b = -b;
        }

        return Math.max(a, b);
    }

    public static int floorDiv(int dividend, int divisor) {
        return Math.floorDiv(dividend, divisor);
    }

    public static int nextInt(Random random, int min, int max) {
        return min >= max ? min : random.nextInt(max - min + 1) + min;
    }

    public static float nextFloat(Random random, float min, float max) {
        return min >= max ? min : random.nextFloat() * (max - min) + min;
    }

    public static double nextDouble(Random random, double min, double max) {
        return min >= max ? min : random.nextDouble() * (max - min) + min;
    }

    public static double average(long[] array) {
        long l = 0L;

        for (long m : array) {
            l += m;
        }

        return (double) l / (double) array.length;
    }

    public static boolean approximatelyEquals(float a, float b) {
        return Math.abs(b - a) < 1.0E-5F;
    }

    public static boolean approximatelyEquals(double a, double b) {
        return Math.abs(b - a) < 9.999999747378752E-6;
    }

    public static int floorMod(int dividend, int divisor) {
        return Math.floorMod(dividend, divisor);
    }

    public static float floorMod(float dividend, float divisor) {
        return (dividend % divisor + divisor) % divisor;
    }

    public static double floorMod(double dividend, double divisor) {
        return (dividend % divisor + divisor) % divisor;
    }

    public static int wrapDegrees(int degrees) {
        int i = degrees % 360;
        if (i >= 180) {
            i -= 360;
        }

        if (i < -180) {
            i += 360;
        }

        return i;
    }

    public static float wrapDegrees(float degrees) {
        float f = degrees % 360.0F;
        if (f >= 180.0F) {
            f -= 360.0F;
        }

        if (f < -180.0F) {
            f += 360.0F;
        }

        return f;
    }

    public static double wrapDegrees(double degrees) {
        double d = degrees % 360.0;
        if (d >= 180.0) d -= 360.0;
        if (d < -180.0) d += 360.0;

        return d;
    }

    public static float subtractAngles(float start, float end) {
        return wrapDegrees(end - start);
    }

    public static float angleBetween(float first, float second) {
        return abs(subtractAngles(first, second));
    }

    public static float clampAngle(float value, float mean, float delta) {
        float f = subtractAngles(value, mean);
        float g = clamp(f, -delta, delta);
        return mean - g;
    }

    public static float stepTowards(float from, float to, float step) {
        step = abs(step);
        return from < to ? clamp(from + step, from, to) : clamp(from - step, to, from);
    }

    public static float stepUnwrappedAngleTowards(float from, float to, float step) {
        float f = subtractAngles(from, to);
        return stepTowards(from, from + f, step);
    }

    public static double parseDouble(String string, double fallback) {
        try {
            return Double.parseDouble(string);
        } catch (Throwable var4) {
            return fallback;
        }
    }

    public static double parseDouble(String string, double fallback, double min) {
        return Math.max(min, parseDouble(string, fallback));
    }

    public static int smallestEncompassingPowerOfTwo(int value) {
        int i = value - 1;
        i |= i >> 1;
        i |= i >> 2;
        i |= i >> 4;
        i |= i >> 8;
        i |= i >> 16;
        return i + 1;
    }

    public static boolean isPowerOfTwo(int value) {
        return value != 0 && (value & value - 1) == 0;
    }

    public static int ceilLog2(int value) {
        value = isPowerOfTwo(value) ? value : smallestEncompassingPowerOfTwo(value);
        return MULTIPLY_DE_BRUIJN_BIT_POSITION[(int) ((long) value * 125613361L >> 27) & 31];
    }

    public static int floorLog2(int value) {
        return ceilLog2(value) - (isPowerOfTwo(value) ? 0 : 1);
    }

    public static int packRgb(float r, float g, float b) {
        return packRgb(floor(r * 255.0F), floor(g * 255.0F), floor(b * 255.0F));
    }

    public static int packRgb(int r, int g, int b) {
        int i = (r << 8) + g;
        i = (i << 8) + b;
        return i;
    }

    public static int multiplyColors(int a, int b) {
        int i = (a & 16711680) >> 16;
        int j = (b & 16711680) >> 16;
        int k = (a & '\uff00') >> 8;
        int l = (b & '\uff00') >> 8;
        int m = (a & 255);
        int n = (b & 255);
        int o = (int) ((float) i * (float) j / 255.0F);
        int p = (int) ((float) k * (float) l / 255.0F);
        int q = (int) ((float) m * (float) n / 255.0F);
        return a & -16777216 | o << 16 | p << 8 | q;
    }

    public static int multiplyColors(int color, float r, float g, float b) {
        int i = (color & 16711680) >> 16;
        int j = (color & '\uff00') >> 8;
        int k = (color & 255);
        int l = (int) ((float) i * r);
        int m = (int) ((float) j * g);
        int n = (int) ((float) k * b);
        return color & -16777216 | l << 16 | m << 8 | n;
    }

    public static float fractionalPart(float value) {
        return value - (float) floor(value);
    }

    public static double fractionalPart(double value) {
        return value - (double) lfloor(value);
    }

    public static Vec3d method_34946(Vec3d vec3d, Vec3d vec3d2, Vec3d vec3d3, Vec3d vec3d4, double d) {
        double e = ((-d + 2.0) * d - 1.0) * d * 0.5;
        double f = ((3.0 * d - 5.0) * d * d + 2.0) * 0.5;
        double g = ((-3.0 * d + 4.0) * d + 1.0) * d * 0.5;
        double h = (d - 1.0) * d * d * 0.5;
        return new Vec3d(vec3d.x * e + vec3d2.x * f + vec3d3.x * g + vec3d4.x * h, vec3d.y * e + vec3d2.y * f + vec3d3.y * g + vec3d4.y * h, vec3d.z * e + vec3d2.z * f + vec3d3.z * g + vec3d4.z * h);
    }

    public static long hashCode(Vec3i vec) {
        return hashCode(vec.getX(), vec.getY(), vec.getZ());
    }

    public static long hashCode(int x, int y, int z) {
        long l = (x * 3129871L) ^ (long) z * 116129781L ^ (long) y;
        l = l * l * 42317861L + l * 11L;
        return l >> 16;
    }

    public static UUID randomUuid(Random random) {
        long l = random.nextLong() & -61441L | 16384L;
        long m = random.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
        return new UUID(l, m);
    }

    public static double getLerpProgress(double value, double start, double end) {
        return (value - start) / (end - start);
    }

    public static float getLerpProgress(float value, float start, float end) {
        return (value - start) / (end - start);
    }

    public static double atan2(double y, double x) {
        double d = x * x + y * y;
        if (Double.isNaN(d)) {
            return Double.NaN;
        } else {
            boolean bl = y < 0.0;
            if (bl) {
                y = -y;
            }

            boolean bl2 = x < 0.0;
            if (bl2) {
                x = -x;
            }

            boolean bl3 = y > x;
            double e;
            if (bl3) {
                e = x;
                x = y;
                y = e;
            }

            e = fastInverseSqrt(d);
            x *= e;
            y *= e;
            double f = SMALLEST_FRACTION_FREE_DOUBLE + y;
            int i = (int) Double.doubleToRawLongBits(f);
            double g = ARCSINE_TABLE[i];
            double h = COSINE_TABLE[i];
            double j = f - SMALLEST_FRACTION_FREE_DOUBLE;
            double k = y * h - x * j;
            double l = (6.0 + k * k) * k * 0.16666666666666666;
            double m = g + l;
            if (bl3) {
                m = 1.5707963267948966 - m;
            }

            if (bl2) {
                m = Math.PI - m;
            }

            if (bl) {
                m = -m;
            }

            return m;
        }
    }

    public static float fastInverseSqrt(float x) {
        float f = 0.5F * x;
        int i = Float.floatToIntBits(x);
        i = 1597463007 - (i >> 1);
        x = Float.intBitsToFloat(i);
        x *= 1.5F - f * x * x;
        return x;
    }

    public static double fastInverseSqrt(double x) {
        double d = 0.5 * x;
        long l = Double.doubleToRawLongBits(x);
        l = 6910469410427058090L - (l >> 1);
        x = Double.longBitsToDouble(l);
        x *= 1.5 - d * x * x;
        return x;
    }

    public static float fastInverseCbrt(float x) {
        int i = Float.floatToIntBits(x);
        i = 1419967116 - i / 3;
        float f = Float.intBitsToFloat(i);
        f = 0.6666667F * f + 1.0F / (3.0F * f * f * x);
        f = 0.6666667F * f + 1.0F / (3.0F * f * f * x);
        return f;
    }

    public static int hsvToRgb(float hue, float saturation, float value) {
        int i = (int) (hue * 6.0F) % 6;
        float f = hue * 6.0F - (float) i;
        float g = value * (1.0F - saturation);
        float h = value * (1.0F - f * saturation);
        float j = value * (1.0F - (1.0F - f) * saturation);
        float k;
        float l;
        float m;
        switch (i) {
            case 0:
                k = value;
                l = j;
                m = g;
                break;
            case 1:
                k = h;
                l = value;
                m = g;
                break;
            case 2:
                k = g;
                l = value;
                m = j;
                break;
            case 3:
                k = g;
                l = h;
                m = value;
                break;
            case 4:
                k = j;
                l = g;
                m = value;
                break;
            case 5:
                k = value;
                l = g;
                m = h;
                break;
            default:
                throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
        }

        int n = clamp((int) (k * 255.0F), 0, 255);
        int o = clamp((int) (l * 255.0F), 0, 255);
        int p = clamp((int) (m * 255.0F), 0, 255);
        return n << 16 | o << 8 | p;
    }

    public static int idealHash(int value) {
        value ^= value >>> 16;
        value *= -2048144789;
        value ^= value >>> 13;
        value *= -1028477387;
        value ^= value >>> 16;
        return value;
    }

    public static long murmurHash(long value) {
        value ^= value >>> 33;
        value *= -49064778989728563L;
        value ^= value >>> 33;
        value *= -4265267296055464877L;
        value ^= value >>> 33;
        return value;
    }

    public static double[] getCumulativeDistribution(double... values) {
        double d = 0.0;
        double[] var3 = values;
        int var4 = values.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            double e = var3[var5];
            d += e;
        }

        int i;
        for (i = 0; i < values.length; ++i) {
            values[i] /= d;
        }

        for (i = 0; i < values.length; ++i) {
            values[i] += i == 0 ? 0.0 : values[i - 1];
        }

        return values;
    }

    public static int method_34950(Random random, double[] ds) {
        double d = random.nextDouble();

        for (int i = 0; i < ds.length; ++i) {
            if (d < ds[i]) {
                return i;
            }
        }

        return ds.length;
    }

    public static double[] method_34941(double d, double e, double f, int i, int j) {
        double[] ds = new double[j - i + 1];
        int k = 0;

        for (int l = i; l <= j; ++l) {
            ds[k] = Math.max(0.0, d * StrictMath.exp(-((double) l - f) * ((double) l - f) / (2.0 * e * e)));
            ++k;
        }

        return ds;
    }

    public static double[] method_34940(double d, double e, double f, double g, double h, double i, int j, int k) {
        double[] ds = new double[k - j + 1];
        int l = 0;

        for (int m = j; m <= k; ++m) {
            ds[l] = Math.max(0.0, d * StrictMath.exp(-((double) m - f) * ((double) m - f) / (2.0 * e * e)) + g * StrictMath.exp(-((double) m - i) * ((double) m - i) / (2.0 * h * h)));
            ++l;
        }

        return ds;
    }

    public static double[] method_34942(double d, double e, int i, int j) {
        double[] ds = new double[j - i + 1];
        int k = 0;

        for (int l = i; l <= j; ++l) {
            ds[k] = Math.max(d * StrictMath.log(l) + e, 0.0);
            ++k;
        }

        return ds;
    }

    public static int binarySearch(int min, int max, IntPredicate predicate) {
        int i = max - min;

        while (i > 0) {
            int j = i / 2;
            int k = min + j;
            if (predicate.test(k)) {
                i = j;
            } else {
                min = k + 1;
                i -= j + 1;
            }
        }

        return min;
    }

    public static float lerp(float delta, float start, float end) {
        return start + delta * (end - start);
    }

    public static double lerp(double delta, double start, double end) {
        return start + delta * (end - start);
    }

    public static double lerp2(double deltaX, double deltaY, double x0y0, double x1y0, double x0y1, double x1y1) {
        return lerp(deltaY, lerp(deltaX, x0y0, x1y0), lerp(deltaX, x0y1, x1y1));
    }

    public static double lerp3(double deltaX, double deltaY, double deltaZ, double x0y0z0, double x1y0z0, double x0y1z0, double x1y1z0, double x0y0z1, double x1y0z1, double x0y1z1, double x1y1z1) {
        return lerp(deltaZ, lerp2(deltaX, deltaY, x0y0z0, x1y0z0, x0y1z0, x1y1z0), lerp2(deltaX, deltaY, x0y0z1, x1y0z1, x0y1z1, x1y1z1));
    }

    public static float method_41303(float f, float g, float h, float i, float j) {
        return 0.5F * (2.0F * h + (i - g) * f + (2.0F * g - 5.0F * h + 4.0F * i - j) * f * f + (3.0F * h - g - 3.0F * i + j) * f * f * f);
    }

    public static double perlinFade(double value) {
        return value * value * value * (value * (value * 6.0 - 15.0) + 10.0);
    }

    public static double perlinFadeDerivative(double value) {
        return 30.0 * value * value * (value - 1.0) * (value - 1.0);
    }

    public static int sign(double value) {
        if (value == 0.0) {
            return 0;
        } else {
            return value > 0.0 ? 1 : -1;
        }
    }

    public static float lerpAngleDegrees(float delta, float start, float end) {
        return start + delta * wrapDegrees(end - start);
    }

    public static float method_34955(float f, float g, float h) {
        return Math.min(f * f * 0.6F + g * g * ((3.0F + g) / 4.0F) + h * h * 0.8F, 1.0F);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static float lerpAngle(float start, float end, float delta) {
        float f;
        for (f = end - start; f < -180.0F; f += 360.0F) {
        }

        while (f >= 180.0F) {
            f -= 360.0F;
        }

        return start + delta * f;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static float fwrapDegrees(double degrees) {
        while (degrees >= 180.0) {
            degrees -= 360.0;
        }

        while (degrees < -180.0) {
            degrees += 360.0;
        }

        return (float) degrees;
    }

    public static float wrap(float value, float maxDeviation) {
        return (Math.abs(value % maxDeviation - maxDeviation * 0.5F) - maxDeviation * 0.25F) / (maxDeviation * 0.25F);
    }

    public static float square(float n) {
        return n * n;
    }

    public static double square(double n) {
        return n * n;
    }

    public static int square(int n) {
        return n * n;
    }

    public static long square(long n) {
        return n * n;
    }

    public static float magnitude(float n) {
        return n * n * n;
    }

    public static double clampedMap(double value, double oldStart, double oldEnd, double newStart, double newEnd) {
        return clampedLerp(newStart, newEnd, getLerpProgress(value, oldStart, oldEnd));
    }

    public static float clampedMap(float value, float oldStart, float oldEnd, float newStart, float newEnd) {
        return clampedLerp(newStart, newEnd, getLerpProgress(value, oldStart, oldEnd));
    }

    public static double map(double value, double oldStart, double oldEnd, double newStart, double newEnd) {
        return lerp(getLerpProgress(value, oldStart, oldEnd), newStart, newEnd);
    }

    public static float map(float value, float oldStart, float oldEnd, float newStart, float newEnd) {
        return lerp(getLerpProgress(value, oldStart, oldEnd), newStart, newEnd);
    }

    public static int roundUpToMultiple(int value, int divisor) {
        return ceilDiv(value, divisor) * divisor;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static int ceilDiv(int a, int b) {
        return -Math.floorDiv(-a, b);
    }

    public static int nextBetween(Random random, int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static float nextBetween(Random random, float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    public static float nextGaussian(Random random, float mean, float deviation) {
        return mean + (float) random.nextGaussian() * deviation;
    }

    public static double squaredHypot(double a, double b) {
        return a * a + b * b;
    }

    public static double hypot(double a, double b) {
        return Math.sqrt(squaredHypot(a, b));
    }

    public static double squaredMagnitude(Vec3d val) {
        double a = val.x;
        double b = val.y;
        double c = val.z;
        return a * a + b * b + c * c;
    }

    public static double squaredMagnitude(double a, double b, double c) {
        return a * a + b * b + c * c;
    }

    public static double magnitude(double a, double b, double c) {
        return Math.sqrt(squaredMagnitude(a, b, c));
    }

    public static int roundDownToMultiple(double a, int b) {
        return floor(a / (double) b) * b;
    }

    public static IntStream stream(int seed, int lowerBound, int upperBound) {
        return stream(seed, lowerBound, upperBound, 1);
    }

    public static IntStream stream(int seed, int lowerBound, int upperBound, int steps) {
        if (lowerBound > upperBound) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "upperbound %d expected to be > lowerBound %d", upperBound, lowerBound));
        } else if (steps < 1) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "steps expected to be >= 1, was %d", steps));
        } else {
            return seed >= lowerBound && seed <= upperBound ? IntStream.iterate(seed, (i) -> {
                int m = Math.abs(seed - i);
                return seed - m >= lowerBound || seed + m <= upperBound;
            }, (i) -> {
                boolean bl = i <= seed;
                int n = Math.abs(seed - i);
                boolean bl2 = seed + n + steps <= upperBound;
                if (!bl || !bl2) {
                    int o = seed - n - (bl ? steps : 0);
                    if (o >= lowerBound) {
                        return o;
                    }
                }

                return seed + n + steps;
            }) : IntStream.empty();
        }
    }
}
