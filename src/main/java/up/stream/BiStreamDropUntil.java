package up.stream;

import java.util.Optional;
import java.util.function.BiPredicate;

import up.stream.util.Pair;

final class BiStreamDropUntil<T, U> extends BiStream<T, U> {
    private final BiStream<T, U> prev;
    private final BiPredicate<? super T, ? super U> predicate;
    private boolean hasDropped;

    BiStreamDropUntil(final BiStream<T, U> prev, final BiPredicate<? super T, ? super U> predicate) {
        this.prev = prev;
        this.predicate = predicate;
        hasDropped = false;
    }

    @Override
    protected Optional<Pair<T, U>> next() {
        Optional<Pair<T, U>> curr = prev.next();
        if (!hasDropped) {
            while (curr.isPresent()) {
                final Pair<T, U> pair = curr.get();
                if (predicate.test(pair.first(), pair.second())) {
                    break;
                }
                curr = prev.next();
            }
            hasDropped = true;
        }
        return curr;
    }

    @Override
    protected BiStream<T, U> copy() {
        return new BiStreamDropUntil<>(prev.copy(), predicate);
    }
}
