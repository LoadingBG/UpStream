package up.stream;

import java.util.Optional;
import java.util.function.Function;

final class StreamMap<T, R> extends Stream<R> {
    private final Stream<T> upstream;
    private final Function<? super T, ? extends R> mapper;

    StreamMap(final Stream<T> upstream, final Function<? super T, ? extends R> mapper) {
        this.upstream = upstream;
        this.mapper = mapper;
    }

    @Override
    protected Optional<R> next() {
        // Prevent Objects#requireNonNull check in Optional#map
        final Optional<T> elem = upstream.next();
        return elem.isPresent() ? Optional.ofNullable(mapper.apply(elem.get())) : Optional.empty();
    }

    @Override
    protected Stream<R> copy() {
        return new StreamMap<>(upstream.copy(), mapper);
    }
}
