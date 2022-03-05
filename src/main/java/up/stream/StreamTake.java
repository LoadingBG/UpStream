package up.stream;

import java.util.Optional;

final class StreamTake<T> extends Stream<T> {
    private final Stream<T> prev;
    private final long elemsToTake;
    private long elemsTaken;

    StreamTake(final Stream<T> prev, final long elemsToTake) {
        this.prev = prev;
        this.elemsToTake = elemsToTake;
        elemsTaken = 0;
    }

    @Override
    protected Optional<T> next() {
        if (elemsTaken >= elemsToTake) {
            return Optional.empty();
        }
        ++elemsTaken;
        return prev.next();
    }

    @Override
    protected Stream<T> copy() {
        return new StreamTake<>(prev.copy(), elemsToTake);
    }
}