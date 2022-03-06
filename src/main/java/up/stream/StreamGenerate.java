package up.stream;

import java.util.Optional;
import java.util.function.Supplier;

final class StreamGenerate<T> extends Stream<T> {
    private final Supplier<? extends T> supplier;

    StreamGenerate(final Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    @Override
    protected Optional<T> next() {
        return Optional.of(supplier.get());
    }

    @Override
    protected Stream<T> copy() {
        return new StreamGenerate<>(supplier);
    }
}
