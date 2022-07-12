package up.stream;

import java.util.Optional;
import java.util.function.BiPredicate;

import up.stream.util.Pair;

final class BiStreamDropUntil<T, U> extends BiStream<T, U> {
    private final BiStream<T, U> upstream;
    private final BiPredicate<? super T, ? super U> predicate;
    private boolean hasDropped;

    BiStreamDropUntil(final BiStream<T, U> upstream, final BiPredicate<? super T, ? super U> predicate) {
        this.upstream = upstream;
        this.predicate = predicate;
        hasDropped = false;
    }

    @Override
    protected Optional<Pair<T, U>> next() {
        Optional<Pair<T, U>> curr = upstream.next();
        if (!hasDropped) {
            while (curr.isPresent()) {
                // Prevent Objects#requireNonNull check in Optional#filter
                final Pair<T, U> pair = curr.get();
                if (predicate.test(pair.first(), pair.second())) {
                    break;
                }
                curr = upstream.next();
            }
            hasDropped = true;
        }
        return curr;
    }

    @Override
    protected BiStream<T, U> copy() {
        return new BiStreamDropUntil<>(upstream.copy(), predicate);
    }
}
