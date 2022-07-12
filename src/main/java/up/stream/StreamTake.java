package up.stream;

import java.util.Optional;

final class StreamTake<T> extends Stream<T> {
    private final Stream<T> upstream;
    private final long elemsToTake;
    private long elemsTaken;

    StreamTake(final Stream<T> upstream, final long elemsToTake) {
        this.upstream = upstream;
        this.elemsToTake = elemsToTake;
        elemsTaken = 0;
    }

    @Override
    protected Optional<T> next() {
        if (elemsTaken >= elemsToTake) {
            return Optional.empty();
        }
        ++elemsTaken;
        return upstream.next();
    }

    @Override
    protected Stream<T> copy() {
        return new StreamTake<>(upstream.copy(), elemsToTake);
    }
}
