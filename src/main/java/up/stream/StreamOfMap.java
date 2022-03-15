package up.stream;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import up.stream.util.Pair;

final class StreamOfMap<T, U> extends BiStream<T, U> {
    private final Map<T, U> map;
    private final Iterator<Map.Entry<T, U>> iter;

    StreamOfMap(final Map<T, U> map) {
        this.map = map;
        iter = map.entrySet().iterator();
    }

    @Override
    protected Optional<Pair<T, U>> next() {
        if (iter.hasNext()) {
            final Map.Entry<T, U> entry = iter.next();
            return Optional.of(new Pair<>(entry.getKey(), entry.getValue()));
        }
        return Optional.empty();
    }

    @Override
    protected BiStream<T, U> copy() {
        return new StreamOfMap<>(map);
    }
}
