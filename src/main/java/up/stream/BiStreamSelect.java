package up.stream;

import java.util.Optional;
import java.util.function.BiPredicate;

import up.stream.util.Pair;

final class BiStreamSelect<T, U> extends BiStream<T, U> {
    private final BiStream<T, U> prev;
    private final BiPredicate<? super T, ? super U> filter;

    BiStreamSelect(final BiStream<T, U> prev, final BiPredicate<? super T, ? super U> filter) {
        this.prev = prev;
        this.filter = filter;
    }

    @Override
    protected Optional<Pair<T, U>> next() {
        Optional<Pair<T, U>> curr = prev.next();
        while (curr.isPresent()) {
            final Pair<T, U> pair = curr.get();
            if (filter.test(pair.first(), pair.second())) {
                break;
            }
            curr = prev.next();
        }
        return curr;
    }

    @Override
    protected BiStream<T, U> copy() {
        return new BiStreamSelect<>(prev.copy(), filter);
    }
}
