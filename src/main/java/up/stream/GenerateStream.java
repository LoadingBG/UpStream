package up.stream;

import java.util.Optional;
import java.util.function.Supplier;

final class GenerateStream<T> extends Stream<T> {
    private final Supplier<? extends T> supplier;

    GenerateStream(final Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    @Override
    protected Optional<T> next() {
        return Optional.of(supplier.get());
    }

    @Override
    protected SizeBounds sizeBounds() {
        return SizeBounds.infinite();
    }

    @Override
    protected Stream<T> copy() {
        return new GenerateStream<>(supplier);
    }
}
