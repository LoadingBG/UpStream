package up.stream;

import java.util.Optional;
import java.util.function.UnaryOperator;

final class StreamIterate<T> extends Stream<T> {
    private final T seed;
    private final UnaryOperator<T> mapper;
    private T curr;

    StreamIterate(final T seed, final UnaryOperator<T> mapper) {
        this.seed = seed;
        this.mapper = mapper;
        curr = null;
    }

    @Override
    protected Optional<T> next() {
        curr = curr == null ? seed : mapper.apply(curr);
        return Optional.ofNullable(curr);
    }

    @Override
    protected Stream<T> copy() {
        return new StreamIterate<>(seed, mapper);
    }
}
