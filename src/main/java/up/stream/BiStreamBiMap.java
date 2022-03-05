package up.stream;

import java.util.Optional;
import java.util.function.BiFunction;

import up.stream.util.Pair;

final class BiStreamBiMap<T, U, R, S> extends BiStream<R, S> {
    private final BiStream<T, U> prev;
    private final BiFunction<? super T, ? super U, ? extends Pair<R, S>> mapper;

    BiStreamBiMap(final BiStream<T, U> prev, final BiFunction<? super T, ? super U, ? extends Pair<R, S>> mapper) {
        this.prev = prev;
        this.mapper = mapper;
    }

    @Override
    protected Optional<Pair<R, S>> next() {
        final Optional<Pair<T, U>> curr = prev.next();
        if (!curr.isPresent()) {
            return Optional.empty();
        }
        final Pair<T, U> pair = curr.get();
        return Optional.of(mapper.apply(pair.first(), pair.second()));
    }

    @Override
    protected BiStream<R, S> copy() {
        return new BiStreamBiMap<>(prev.copy(), mapper);
    }
}
