package up.stream;

import java.util.Optional;
import java.util.function.Consumer;

final class StreamInspect<T> extends Stream<T> {
    private final Stream<T> upstream;
    private final Consumer<? super T> action;

    StreamInspect(final Stream<T> upstream, final Consumer<? super T> action) {
        this.upstream = upstream;
        this.action = action;
    }

    @Override
    protected Optional<T> next() {
        final Optional<T> next = upstream.next();
        next.ifPresent(action);
        return next;
    }

    @Override
    protected Stream<T> copy() {
        return new StreamInspect<>(upstream.copy(), action);
    }
}
