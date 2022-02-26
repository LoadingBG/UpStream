package up.stream;

import java.util.Optional;
import java.util.function.Predicate;

final class TakeUntilStream<T> extends Stream<T> {
    private final Stream<T> prev;
    private final Predicate<? super T> filter;
    private boolean isTaking;

    TakeUntilStream(final Stream<T> prev, final Predicate<? super T> filter) {
        this.prev = prev;
        this.filter = filter;
        isTaking = false;
    }

    @Override
    protected Optional<T> next() {
        if (!isTaking) {
            return Optional.empty();
        }
        Optional<T> curr = prev.next();
        if (curr.isPresent() && !filter.test(curr.get())) {
            return curr;
        }
        isTaking = false;
        return Optional.empty();
    }

    @Override
    protected SizeBounds sizeBounds() {
        return new SizeBounds(0, prev.sizeBounds().upper());
    }

    @Override
    protected Stream<T> copy() {
        return new TakeUntilStream<>(prev.copy(), filter);
    }
}
