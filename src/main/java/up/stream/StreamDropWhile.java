package up.stream;

import java.util.Optional;
import java.util.function.Predicate;

final class StreamDropWhile<T> extends Stream<T> {
    private final Stream<T> upstream;
    private final Predicate<? super T> predicate;
    private boolean hasDropped;

    StreamDropWhile(final Stream<T> upstream, final Predicate<? super T> predicate) {
        this.upstream = upstream;
        this.predicate = predicate;
        hasDropped = false;
    }

    @Override
    protected Optional<T> next() {
        Optional<T> curr = upstream.next();
        if (!hasDropped) {
            // Prevent Objects#requireNonNull check in Optional#filter
            while (curr.isPresent() && predicate.test(curr.get())) {
                curr = upstream.next();
            }
            hasDropped = true;
        }
        return curr;
    }

    @Override
    protected Stream<T> copy() {
        return new StreamDropWhile<>(upstream.copy(), predicate);
    }
}
