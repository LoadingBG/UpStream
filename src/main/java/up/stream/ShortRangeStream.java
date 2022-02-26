package up.stream;

import java.util.Optional;

final class ShortRangeStream extends Stream<Short> {
    private final short start;
    private final short end;
    private final short step;
    private short curr;

    ShortRangeStream(final short start, final short end, final short step) {
        this.start = start;
        this.end = end;
        this.step = step;
        curr = start;
    }

    @Override
    protected Optional<Short> next() {
        if (curr > end) {
            return Optional.empty();
        }
        final Optional<Short> res = Optional.of(curr);
        curr += step;
        return res;
    }

    @Override
    protected SizeBounds sizeBounds() {
        return SizeBounds.sized((end - start) / step);
    }

    @Override
    protected Stream<Short> copy() {
        return new ShortRangeStream(start, end, step);
    }
}
