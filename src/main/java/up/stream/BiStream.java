package up.stream;

import java.util.Optional;

import up.stream.util.Pair;

/**
 * A stream of pairs of elements with specialized
 * methods for working with pairs.
 *
 * @param <T> The type of the first element in the pair.
 * @param <U> The type of the second element in the pair.
 *
 * @see Stream
 */
public abstract class BiStream<T, U> {
    /**
     * Computes the {@link Optional} holding the next pair
     * of elements in this stream.
     *
     * <p>An empty {@link Optional} means that this is the end
     * of this stream and there will be no more pairs.</p>
     *
     * @return An {@link Optional} holding the
     * next pair of elements in this stream.
     */
    protected abstract Optional<Pair<T, U>> next();

    /**
     * The size bounds of this stream.
     *
     * <p>The lower bound is the minimum number
     * of elements this stream can have.
     * The upper bound is the maximum number
     * of elements this stream can have.</p>
     *
     * <p>If this stream might be infinite, the
     * upper bound should be an empty {@link Optional}.</p>
     *
     * @return The size bounds of this stream.
     */
    protected abstract SizeBounds sizeBounds();

    /**
     * Creates a copy of this stream with the
     * initial values this stream had.
     *
     * @return A copy of this stream.
     */
    protected abstract BiStream<T, U> copy();
}
