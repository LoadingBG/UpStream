package up.stream;

import java.util.Optional;

final class StreamDrop<T> extends Stream<T> {
    private final Stream<T> upstream;
    private final long elemsToDrop;
    private boolean hasDropped;

    StreamDrop(final Stream<T> upstream, final long elemsToDrop) {
        this.upstream = upstream;
        this.elemsToDrop = elemsToDrop;
        hasDropped = false;
    }

    @Override
    protected Optional<T> next() {
        if (!hasDropped) {
            for (long i = 0; i < elemsToDrop; ++i) {
                upstream.next();
            }
            hasDropped = true;
        }
        return upstream.next();
    }

    @Override
    protected Stream<T> copy() {
        return new StreamDrop<>(upstream.copy(), elemsToDrop);
    }
}
