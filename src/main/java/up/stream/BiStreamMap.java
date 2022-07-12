package up.stream;

import java.util.Optional;
import java.util.function.BiFunction;

import up.stream.util.Pair;

final class BiStreamMap<T, U, R> extends Stream<R> {
    private final BiStream<T, U> upstream;
    private final BiFunction<? super T, ? super U, ? extends R> mapper;

    BiStreamMap(final BiStream<T, U> upstream, final BiFunction<? super T, ? super U, ? extends R> mapper) {
        this.upstream = upstream;
        this.mapper = mapper;
    }

    @Override
    protected Optional<R> next() {
        final Optional<Pair<T, U>> elem = upstream.next();
        // Prevent Objects#requireNonNull check in Optional#map
        if (elem.isPresent()) {
            final Pair<T, U> pair = elem.get();
            return Optional.ofNullable(mapper.apply(pair.first(), pair.second()));
        }
        return Optional.empty();
    }

    @Override
    protected Stream<R> copy() {
        return new BiStreamMap<>(upstream.copy(), mapper);
    }
}
