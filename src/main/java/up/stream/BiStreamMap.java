package up.stream;

import java.util.Optional;
import java.util.function.BiFunction;

import up.stream.util.Pair;

final class BiStreamMap<T, U, R> extends Stream<R> {
    private final BiStream<T, U> prev;
    private final BiFunction<? super T, ? super U, ? extends R> mapper;

    BiStreamMap(final BiStream<T, U> prev, final BiFunction<? super T, ? super U, ? extends R> mapper) {
        this.prev = prev;
        this.mapper = mapper;
    }

    @Override
    protected Optional<R> next() {
        final Optional<Pair<T, U>> curr = prev.next();
        if (!curr.isPresent()) {
            return Optional.empty();
        }
        final Pair<T, U> pair = curr.get();
        return Optional.of(mapper.apply(pair.first(), pair.second()));
    }

    @Override
    protected Stream<R> copy() {
        return new BiStreamMap<>(prev.copy(), mapper);
    }
}
