package up.stream;

import java.util.Optional;

final class StreamCycle<T> extends Stream<T> {
    private final Stream<T> upstream;
    private final long times;
    private Stream<T> currStream;

    StreamCycle(final Stream<T> upstream, final long times) {
        this.upstream = upstream;
        this.times = times;
        currStream = upstream.copy();
    }

    @Override
    protected Optional<T> next() {
        final Optional<T> elem = currStream.next();
        if (elem.isPresent()) {
            return elem;
        }
        currStream = upstream.copy();
        return currStream.next();
    }

    @Override
    protected Stream<T> copy() {
        // Upstream is not modified
        return new StreamCycle<>(upstream, times);
    }
}
