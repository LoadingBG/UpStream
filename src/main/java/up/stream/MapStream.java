package up.stream;

import java.util.Optional;
import java.util.function.Function;

final class MapStream<T, R> extends Stream<R> {
    private final Stream<T> prev;
    private final Function<? super T, ? extends R> mapper;

    MapStream(final Stream<T> prev, final Function<? super T, ? extends R> mapper) {
        this.prev = prev;
        this.mapper = mapper;
    }

    @Override
    protected Optional<R> next() {
        return prev.next().map(mapper);
    }

    @Override
    protected Stream<R> copy() {
        return new MapStream<>(prev.copy(), mapper);
    }
}
