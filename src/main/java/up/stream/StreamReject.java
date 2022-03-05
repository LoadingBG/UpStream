package up.stream;

import java.util.Optional;
import java.util.function.Predicate;

final class StreamReject<T> extends Stream<T> {
    private final Stream<T> prev;
    private final Predicate<? super T> filter;

    StreamReject(final Stream<T> prev, final Predicate<? super T> filter) {
        this.prev = prev;
        this.filter = filter;
    }

    @Override
    protected Optional<T> next() {
        Optional<T> curr = prev.next();
        while (curr.isPresent() && filter.test(curr.get())) {
            curr = prev.next();
        }
        return curr;
    }

    @Override
    protected Stream<T> copy() {
        return new StreamReject<>(prev.copy(), filter);
    }
}
