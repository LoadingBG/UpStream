package up.stream;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

final class StreamUnique<T> extends Stream<T> {
    private final Stream<T> prev;
    private final Set<T> uniques;

    StreamUnique(final Stream<T> prev) {
        this.prev = prev;
        uniques = new HashSet<>();
    }

    @Override
    protected Optional<T> next() {
        Optional<T> curr = prev.next();
        while (curr.isPresent() && uniques.contains(curr.get())) {
            curr = prev.next();
        }
        curr.ifPresent(uniques::add);
        return curr;
    }

    @Override
    protected Stream<T> copy() {
        return new StreamUnique<>(prev.copy());
    }
}