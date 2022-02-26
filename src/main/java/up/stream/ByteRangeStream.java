package up.stream;

import java.util.Optional;

final class ByteRangeStream extends Stream<Byte> {
    private final byte start;
    private final byte end;
    private final byte step;
    private byte curr;

    ByteRangeStream(final byte start, final byte end, final byte step) {
        this.start = start;
        this.end = end;
        this.step = step;
        curr = start;
    }

    @Override
    protected Optional<Byte> next() {
        if (curr > end) {
            return Optional.empty();
        }
        final Optional<Byte> res = Optional.of(curr);
        curr += step;
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
