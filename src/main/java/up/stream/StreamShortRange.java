package up.stream;

import java.util.Optional;

final class StreamShortRange extends Stream<Short> {
    private final short start;
    private final short end;
    private final short step;
    private short curr;
    private boolean hasWrapped;

    StreamShortRange(final short start, final short end, final short step) {
        this.start = start;
        this.end = end;
        this.step = step;
        curr = start;
        hasWrapped = false;
    }

    @Override
    protected Optional<Short> next() {
        if (hasWrapped || curr >= end) {
            return Optional.empty();
        }

        final Optional<Short> res = Optional.of(curr);

        final boolean wasPositive = curr > 0;
        curr += step;
        if (wasPositive && curr < 0) {
            hasWrapped = true;
        }

        return res;
    }

    @Override
    protected Stream<Short> copy() {
        return new StreamShortRange(start, end, step);
    }
}
