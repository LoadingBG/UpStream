package up.stream;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

final class StreamOfCollection<T> extends Stream<T> {
    private final Collection<T> collection;
    private final Iterator<T> iter;

    StreamOfCollection(final Collection<T> collection) {
        this.collection = collection;
        iter = collection.iterator();
    }

    @Override
    protected Optional<T> next() {
        return iter.hasNext() ? Optional.of(iter.next()) : Optional.empty();
    }

    @Override
    protected Stream<T> copy() {
        return new StreamOfCollection<>(collection);
    }
}
