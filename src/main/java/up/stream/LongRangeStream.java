package up.stream;

import java.util.Optional;

final class LongRangeStream extends Stream<Long> {
    private final long start;
    private final long end;
    private final long step;
    private long curr;
    private boolean hasWrapped;

    LongRangeStream(final long start, final long end, final long step) {
        this.start = start;
        this.end = end;
        this.step = step;
        curr = start;
        hasWrapped = false;
    }

    @Override
    protected Optional<Long> next() {
        if (hasWrapped || curr >= end) {
            return Optional.empty();
        }

        final Optional<Long> res = Optional.of(curr);

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
    protected Stream<Long> copy() {
        return new LongRangeStream(start, end, step);
    }
}
