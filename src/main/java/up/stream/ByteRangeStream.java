package up.stream;

import java.util.Optional;

final class ByteRangeStream extends Stream<Byte> {
    private final byte start;
    private final byte end;
    private final byte step;
    private byte curr;
    private boolean hasWrapped;

    ByteRangeStream(final byte start, final byte end, final byte step) {
        this.start = start;
        this.end = end;
        this.step = step;
        curr = start;
        hasWrapped = false;
    }

    @Override
    protected Optional<Byte> next() {
        if (hasWrapped || curr >= end) {
            return Optional.empty();
        }

        final Optional<Byte> res = Optional.of(curr);

        final boolean wasPositive = curr > 0;
        curr += step;
        if (wasPositive && curr < 0) {
            hasWrapped = true;
        }

        return res;
    }

    @Override
    protected SizeBounds sizeBounds() {
        return SizeBounds.sized((end - start) / step);
    }

    @Override
    protected Stream<Byte> copy() {
        return new ByteRangeStream(start, end, step);
    }
}
