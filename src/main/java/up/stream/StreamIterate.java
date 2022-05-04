package up.stream;

import java.util.Optional;
import java.util.function.UnaryOperator;

class StreamIterate<T> extends Stream<T> {
    private final T seed;
    private final UnaryOperator<T> mapper;
    private T curr;

    StreamIterate(final T seed, final UnaryOperator<T> mapper) {
        this.seed = seed;
        this.mapper = mapper;
        curr = seed;
    }

    @Override
    protected Optional<T> next() {
        final T copy = curr;
        curr = mapper.apply(curr);
        return Optional.of(copy);
    }

    @Override
    protected Stream<T> copy() {
        return new StreamIterate<>(seed, mapper);
    }
}
