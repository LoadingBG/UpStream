package up.stream;

import java.util.Optional;

final class IntRangeStream extends Stream<Integer> {
    private final int start;
    private final int end;
    private final int step;
    private int curr;

    IntRangeStream(final int start, final int end, final int step) {
        this.start = start;
        this.end = end;
        this.step = step;
        curr = start;
    }

    @Override
    protected Optional<Integer> next() {
        if (curr > end) {
            return Optional.empty();
        }
        final Optional<Integer> res = Optional.of(curr);
        curr += step;
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
