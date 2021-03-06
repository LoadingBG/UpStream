package up.stream;

import java.util.Optional;
import java.util.function.Predicate;

final class StreamReject<T> extends Stream<T> {
    private final Stream<T> upstream;
    private final Predicate<? super T> predicate;

    StreamReject(final Stream<T> upstream, final Predicate<? super T> predicate) {
        this.upstream = upstream;
        this.predicate = predicate;
    }

    @Override
    protected Optional<T> next() {
        Optional<T> elem = upstream.next();
        // Prevent Objects#requireNonNull check in Optional#filter
        while (elem.isPresent() && predicate.test(elem.get())) {
            elem = upstream.next();
        }
        return elem;
    }

    @Override
    protected Stream<T> copy() {
        return new StreamReject<>(upstream.copy(), predicate);
    }
}
