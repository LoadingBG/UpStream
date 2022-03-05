package up.stream;

import java.util.Optional;

final class StreamByteRange extends Stream<Byte> {
    private final byte start;
    private final byte end;
    private final byte step;
    private byte curr;
    private boolean hasWrapped;

    StreamByteRange(final byte start, final byte end, final byte step) {
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
    protected Stream<Byte> copy() {
        return new StreamByteRange(start, end, step);
    }
}
