package up.stream;

import java.util.Optional;
import java.util.function.Predicate;

final class DropUntilStream<T> extends Stream<T> {
    private final Stream<T> prev;
    private final Predicate<? super T> filter;
    private boolean hasDropped;

    DropUntilStream(final Stream<T> prev, final Predicate<? super T> filter) {
        this.prev = prev;
        this.filter = filter;
        hasDropped = false;
    }

    @Override
    protected Optional<T> next() {
        Optional<T> curr = prev.next();
        if (!hasDropped) {
            while (curr.isPresent() && filter.test(curr.get())) {
                curr = prev.next();
            }
            hasDropped = true;
        }
        return curr;
    }

    @Override
    protected SizeBounds sizeBounds() {
        return new SizeBounds(0, prev.sizeBounds().upper());
    }

    @Override
    protected Stream<T> copy() {
        return new DropUntilStream<>(prev.copy(), filter);
    }
}
