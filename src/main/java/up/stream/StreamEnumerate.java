package up.stream;

import java.util.Optional;

import up.stream.util.Pair;

final class StreamEnumerate<T> extends BiStream<Long, T> {
    private final Stream<T> upstream;
    private long currIdx;

    StreamEnumerate(final Stream<T> upstream) {
        this.upstream = upstream;
        currIdx = 0;
    }

    @Override
    protected Optional<Pair<Long, T>> next() {
        // Prevent Objects#requireNonNull check in Optional#map
        final Optional<T> elem = upstream.next();
        return elem.isPresent() ? Optional.of(new Pair<>(currIdx++, elem.get())) : Optional.empty();
    }

    @Override
    protected BiStream<Long, T> copy() {
        return new StreamEnumerate<>(upstream.copy());
    }
}
