package up.stream;

import java.util.Optional;
import java.util.function.BiConsumer;

import up.stream.util.Pair;

final class BiStreamInspect<T, U> extends BiStream<T, U> {
    private final BiStream<T, U> upstream;
    private final BiConsumer<? super T, ? super U> action;

    BiStreamInspect(final BiStream<T, U> upstream, final BiConsumer<? super T, ? super U> action) {
        this.upstream = upstream;
        this.action = action;
    }

    @Override
    protected Optional<Pair<T, U>> next() {
        final Optional<Pair<T, U>> next = upstream.next();
        next.ifPresent(pair -> action.accept(pair.first(), pair.second()));
        return next;
    }

    @Override
    protected BiStream<T, U> copy() {
        return new BiStreamInspect<>(upstream.copy(), action);
    }
}
