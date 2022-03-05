package up.stream;

import java.util.Optional;
import java.util.function.Function;

import up.stream.util.Pair;

final class BiMapBiStream<T, R, S> extends BiStream<R, S> {
    private final Stream<T> prev;
    private final Function<? super T, ? extends Pair<R, S>> mapper;

    BiMapBiStream(final Stream<T> prev, final Function<? super T, ? extends Pair<R, S>> mapper) {
        this.prev = prev;
        this.mapper = mapper;
    }

    @Override
    protected Optional<Pair<R, S>> next() {
        return prev.next().map(mapper);
    }

    @Override
    protected BiStream<R, S> copy() {
        return new BiMapBiStream<>(prev.copy(), mapper);
    }
}
