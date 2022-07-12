package up.stream;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

final class StreamUnique<T> extends Stream<T> {
    private final Stream<T> upstream;
    private final Set<T> uniques;

    StreamUnique(final Stream<T> upstream) {
        this.upstream = upstream;
        uniques = new HashSet<>();
    }

    @Override
    protected Optional<T> next() {
        Optional<T> curr = upstream.next();
        // Prevent Objects#requireNonNull check in Optional#filter
        while (curr.isPresent() && uniques.contains(curr.get())) {
            curr = upstream.next();
        }
        curr.ifPresent(uniques::add);
        return curr;
    }

    @Override
    protected Stream<T> copy() {
        return new StreamUnique<>(upstream.copy());
    }
}
