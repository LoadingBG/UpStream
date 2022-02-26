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
    protected SizeBounds sizeBounds() {
        long prevLower = prev.sizeBounds().lower();
        long prevUpper = prev.sizeBounds().upper();
        if (prevLower != -1) {
            prevLower -= elemsToDrop;
            prevLower = prevLower < 0 ? 0 : prevLower;
        }
        if (prevUpper != -1) {
            prevUpper -= elemsToDrop;
            prevUpper = prevUpper < 0 ? 0 : prevUpper;
        }
        return new SizeBounds(prevLower, prevUpper);
    }

    @Override
    protected Stream<T> copy() {
        return new DropStream<>(prev.copy(), elemsToDrop);
    }
}
