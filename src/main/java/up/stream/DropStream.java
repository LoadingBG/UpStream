package up.stream;

import java.util.Optional;

final class DropStream<T> extends Stream<T> {
    private final Stream<T> prev;
    private final long elemsToDrop;
    private boolean hasDropped;

    DropStream(final Stream<T> prev, final long elemsToDrop) {
        this.prev = prev;
        this.elemsToDrop = elemsToDrop;
        hasDropped = false;
    }

    @Override
    protected Optional<T> next() {
        if (!hasDropped) {
            for (long i = 0; i < elemsToDrop; ++i) {
                prev.next();
            }
            hasDropped = true;
        }
        return prev.next();
    }

    @Override
    protected Stream<T> copy() {
        return new DropStream<>(prev.copy(), elemsToDrop);
    }
}
