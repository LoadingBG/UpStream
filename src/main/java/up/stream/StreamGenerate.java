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
        return Optional.ofNullable(supplier.get());
    }

    @Override
    protected Stream<T> copy() {
        // No state, even if a proper copy is done,
        // the supplier's state cannot be reset
        return this;
    }
}
