package up.stream;

import java.util.Optional;

final class CycleStream<T> extends Stream<T> {
    private final Stream<T> prev;
    private final long times;
    private Stream<T> curr;
    private long currCycle;

    CycleStream(final Stream<T> prev, final long times) {
        this.prev = prev;
        this.times = times;
        curr = prev.copy();
        currCycle = 1;
    }

    @Override
    protected Optional<T> next() {
        Optional<T> elem = curr.next();
        if (!elem.isPresent() && (times == -1 || currCycle != times)) {
            curr = prev.copy();
            elem =  curr.next();
            ++currCycle;
        }
        return elem;
    }

    @Override
    protected SizeBounds sizeBounds() {
        return SizeBounds.infinite();
    }

    @Override
    protected Stream<T> copy() {
        return new CycleStream<>(prev, times);
    }
}
