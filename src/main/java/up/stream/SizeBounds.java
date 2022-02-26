package up.stream;

import java.util.OptionalLong;

final class SizeBounds {
    private final long lower;
    private final long upper;

    SizeBounds(final long lower, final long upper) {
        this.lower = lower;
        this.upper = upper;
    }

    long lower() {
        return lower;
    }

    long upper() {
        return upper;
    }

    static SizeBounds sized(final long size) {
        return new SizeBounds(size, size);
    }

    static SizeBounds infinite() {
        return sized(-1);
    }
}
