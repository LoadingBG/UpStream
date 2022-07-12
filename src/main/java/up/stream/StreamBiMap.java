package up.stream;

import java.util.Optional;
import java.util.function.Function;

import up.stream.util.Pair;

final class StreamBiMap<T, R, S> extends BiStream<R, S> {
    private final Stream<T> upstream;
    private final Function<? super T, ? extends Pair<R, S>> mapper;

    StreamBiMap(final Stream<T> upstream, final Function<? super T, ? extends Pair<R, S>> mapper) {
        this.upstream = upstream;
        this.mapper = mapper;
    }

    @Override
    protected Optional<Pair<R, S>> next() {
        final Optional<T> elem = upstream.next();
        // Prevent Objects#requireNonNull check in Optional#map
        return elem.isPresent() ? Optional.ofNullable(mapper.apply(elem.get())) : Optional.empty();
    }

    @Override
    protected BiStream<R, S> copy() {
        return new StreamBiMap<>(upstream.copy(), mapper);
    }
}
