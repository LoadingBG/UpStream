package up.stream;

import java.util.Optional;

final class IntRangeStream extends Stream<Integer> {
    private final int start;
    private final int end;
    private final int step;
    private int curr;
    private boolean hasWrapped;

    IntRangeStream(final int start, final int end, final int step) {
        this.start = start;
        this.end = end;
        this.step = step;
        curr = start;
        hasWrapped = false;
    }

    @Override
    protected Optional<Integer> next() {
        if (hasWrapped || curr >= end) {
            return Optional.empty();
        }

        final Optional<Integer> res = Optional.of(curr);

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
    protected Stream<Integer> copy() {
        return new IntRangeStream(start, end, step);
    }
}
