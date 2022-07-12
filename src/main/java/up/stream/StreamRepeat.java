package up.stream;

import java.util.Optional;

final class StreamRepeat<T> extends Stream<T> {
    private final Stream<T> upstream;
    private final long times;
    private Stream<T> currStream;
    private long currCycle;

    StreamRepeat(final Stream<T> upstream, final long times) {
        this.upstream = upstream;
        this.times = times;
        currStream = upstream.copy();
        currCycle = 1;
    }

    @Override
    protected Optional<T> next() {
        Optional<T> elem = currStream.next();
        if (!elem.isPresent() && currCycle < times) {
            currStream = upstream.copy();
            elem = currStream.next();
            ++currCycle;
        }
        return elem;
    }

    @Override
    protected Stream<T> copy() {
        // upstream is not modified
        return new StreamRepeat<>(upstream, times);
    }
}
