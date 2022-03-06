package up.stream;

import java.util.Optional;

final class StreamDrop<T> extends Stream<T> {
    private final Stream<T> prev;
    private final long elemsToDrop;
    private boolean hasDropped;

    StreamDrop(final Stream<T> prev, final long elemsToDrop) {
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
        return new StreamDrop<>(prev.copy(), elemsToDrop);
    }
}
