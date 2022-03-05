package up.stream;

import java.util.Optional;

final class StreamEmpty<T> extends Stream<T> {
    @Override
    protected Optional<T> next() {
        return Optional.empty();
    }

    @Override
    protected Stream<T> copy() {
        return new StreamEmpty<>();
    }
}
