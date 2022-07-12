package up.stream;

import java.util.Optional;
import java.util.function.BiFunction;

import up.stream.util.Pair;

final class BiStreamBiMap<T, U, R, S> extends BiStream<R, S> {
    private final BiStream<T, U> upstream;
    private final BiFunction<? super T, ? super U, ? extends Pair<R, S>> mapper;

    BiStreamBiMap(final BiStream<T, U> upstream, final BiFunction<? super T, ? super U, ? extends Pair<R, S>> mapper) {
        this.upstream = upstream;
        this.mapper = mapper;
    }

    @Override
    protected Optional<Pair<R, S>> next() {
        final Optional<Pair<T, U>> elem = upstream.next();
        // Prevent Objects#requireNonNull check in Optional#map
        if (elem.isPresent()) {
            final Pair<T, U> pair = elem.get();
            return Optional.ofNullable(mapper.apply(pair.first(), pair.second()));
        }
        return Optional.empty();
    }

    @Override
    protected BiStream<R, S> copy() {
        return new BiStreamBiMap<>(upstream.copy(), mapper);
    }
}
