package up.stream;

import java.util.Optional;
import java.util.function.BiPredicate;

import up.stream.util.Pair;

final class BiStreamTakeWhile<T, U> extends BiStream<T, U> {
    private final BiStream<T, U> upstream;
    private final BiPredicate<? super T, ? super U> predicate;
    private boolean isTaking;

    BiStreamTakeWhile(final BiStream<T, U> upstream, final BiPredicate<? super T, ? super U> predicate) {
        this.upstream = upstream;
        this.predicate = predicate;
        isTaking = true;
    }

    @Override
    protected Optional<Pair<T, U>> next() {
        if (!isTaking) {
            return Optional.empty();
        }

        final Optional<Pair<T, U>> elem = upstream.next();
        // Prevent Objects#requireNonNull check in Optional#filter
        if (elem.isPresent()) {
            final Pair<T, U> pair = elem.get();
            if (predicate.test(pair.first(), pair.second())) {
                return elem;
            }
        }
        isTaking = false;
        return Optional.empty();
    }

    @Override
    protected BiStream<T, U> copy() {
        return new BiStreamTakeWhile<>(upstream.copy(), predicate);
    }
}
