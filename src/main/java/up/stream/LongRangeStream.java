package up.stream;

import java.util.Optional;

final class LongRangeStream extends Stream<Long> {
    private final long start;
    private final long end;
    private final long step;
    private long curr;

    LongRangeStream(final long start, final long end, final long step) {
        this.start = start;
        this.end = end;
        this.step = step;
        curr = start;
    }

    @Override
    protected Optional<Long> next() {
        if (curr > end) {
            return Optional.empty();
        }
        final Optional<Long> res = Optional.of(curr);
        curr += step;
        return res;
    }

    @Override
    protected SizeBounds sizeBounds() {
        return SizeBounds.sized((end - start) / step);
    }

    @Override
    protected Stream<Long> copy() {
        return new LongRangeStream(start, end, step);
    }
}
