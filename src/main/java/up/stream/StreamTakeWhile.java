package up.stream;

import java.util.Optional;
import java.util.function.Predicate;

final class StreamTakeWhile<T> extends Stream<T> {
    private final Stream<T> upstream;
    private final Predicate<? super T> predicate;
    private boolean isTaking;

    StreamTakeWhile(final Stream<T> upstream, final Predicate<? super T> predicate) {
        this.upstream = upstream;
        this.predicate = predicate;
        isTaking = true;
    }

    @Override
    protected Optional<T> next() {
        if (!isTaking) {
            return Optional.empty();
        }

        Optional<T> elem = upstream.next();
        // Prevent Objects#requireNonNull check in Optional#filter
        if (elem.isPresent() && predicate.test(elem.get())) {
            return elem;
        }
        isTaking = false;
        return Optional.empty();
    }

    @Override
    protected Stream<T> copy() {
        return new StreamTakeWhile<>(upstream.copy(), predicate);
    }
}
