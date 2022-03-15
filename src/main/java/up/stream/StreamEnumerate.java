package up.stream;

import java.util.Optional;

import up.stream.util.Pair;

final class StreamEnumerate<T> extends BiStream<Long, T> {
    private final Stream<T> prev;
    private long currIdx;

    StreamEnumerate(final Stream<T> prev) {
        this.prev = prev;
        currIdx = 0;
    }

    @Override
    protected Optional<Pair<Long, T>> next() {
        return prev.next().map(v -> new Pair<>(currIdx++, v));
    }

    @Override
    protected BiStream<Long, T> copy() {
        return new StreamEnumerate<>(prev.copy());
    }
}
