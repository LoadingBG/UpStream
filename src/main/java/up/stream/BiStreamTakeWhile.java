package up.stream;

import java.util.Optional;
import java.util.function.BiPredicate;

import up.stream.util.Pair;

final class BiStreamTakeWhile<T, U> extends BiStream<T, U> {
    private final BiStream<T, U> prev;
    private final BiPredicate<? super T, ? super U> predicate;
    private boolean isTaking;

    BiStreamTakeWhile(final BiStream<T, U> prev, final BiPredicate<? super T, ? super U> predicate) {
        this.prev = prev;
        this.predicate = predicate;
        isTaking = true;
    }

    @Override
    protected Optional<Pair<T, U>> next() {
        if (!isTaking) {
            return Optional.empty();
        }
        final Optional<Pair<T, U>> curr = prev.next();
        if (curr.isPresent()) {
            final Pair<T, U> pair = curr.get();
            if (predicate.test(pair.first(), pair.second())) {
                return curr;
            }
        }
        isTaking = false;
        return Optional.empty();
    }

    @Override
    protected BiStream<T, U> copy() {
        return new BiStreamTakeWhile<>(prev.copy(), predicate);
    }
}
