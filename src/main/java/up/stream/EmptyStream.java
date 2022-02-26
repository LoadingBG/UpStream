package up.stream;

import java.util.Optional;

import up.stream.Stream;
import up.stream.SizeBounds;

final class EmptyStream<T> extends Stream<T> {
    @Override
    protected Optional<T> next() {
        return Optional.empty();
    }

    @Override
    protected SizeBounds sizeBounds() {
        return SizeBounds.sized(0);
    }

    @Override
    protected Stream<T> copy() {
        return new EmptyStream<>();
    }
}
