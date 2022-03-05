package up.stream;

import java.util.Optional;
import java.util.function.Predicate;

final class StreamDropUntil<T> extends Stream<T> {
    private final Stream<T> prev;
    private final Predicate<? super T> filter;
    private boolean hasDropped;

    StreamDropUntil(final Stream<T> prev, final Predicate<? super T> filter) {
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
    protected Stream<T> copy() {
        return new StreamDropUntil<>(prev.copy(), filter);
    }
}
