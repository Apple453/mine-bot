package net.applee.minecraft.protocol.message;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import org.jetbrains.annotations.Nullable;

import java.util.BitSet;

public class FilterMask {
    public static final FilterMask FULLY_FILTERED = new FilterMask(new BitSet(0), FilterStatus.FULLY_FILTERED);
    public static final FilterMask PASS_THROUGH = new FilterMask(new BitSet(0), FilterStatus.PASS_THROUGH);
    private static final char FILTERED = '#';
    private final BitSet mask;
    private final FilterStatus status;

    private FilterMask(BitSet mask, FilterStatus status) {
        this.mask = mask;
        this.status = status;
    }

    public FilterMask(int length) {
        this(new BitSet(length), FilterStatus.PARTIALLY_FILTERED);
    }

    public static FilterMask readMask(PacketByteBuffer buffer) {
        FilterStatus filterStatus = buffer.readEnumConstant(FilterStatus.class);
        return switch (filterStatus) {
            case PASS_THROUGH -> PASS_THROUGH;
            case FULLY_FILTERED -> FULLY_FILTERED;
            case PARTIALLY_FILTERED -> new FilterMask(buffer.readBitSet(), FilterStatus.PARTIALLY_FILTERED);
        };
    }

    public static void writeMask(PacketByteBuffer buffer, FilterMask mask) {
        buffer.writeEnumConstant(mask.status);
        if (mask.status == FilterStatus.PARTIALLY_FILTERED) {
            buffer.writeBitSet(mask.mask);
        }
    }

    public void markFiltered(int index) {
        this.mask.set(index);
    }

    @Nullable
    public String filter(String raw) {
        return switch (this.status) {
            case FULLY_FILTERED -> null;
            case PASS_THROUGH -> raw;
            case PARTIALLY_FILTERED -> {
                char[] cs = raw.toCharArray();
                for (int i = 0; i < cs.length && i < this.mask.length(); ++i) {
                    if (!this.mask.get(i)) continue;
                    cs[i] = 35;
                }
                yield new String(cs);
            }
        };
    }

//    @Nullable
//    public Text filter(DecoratedContents contents) {
//        String string = contents.plain();
//        return Util.map(this.filter(string), Text::literal);
//    }

    public boolean isPassThrough() {
        return this.status == FilterStatus.PASS_THROUGH;
    }

    public boolean isFullyFiltered() {
        return this.status == FilterStatus.FULLY_FILTERED;
    }

    public enum FilterStatus {
        PASS_THROUGH,
        FULLY_FILTERED,
        PARTIALLY_FILTERED
    }
}
