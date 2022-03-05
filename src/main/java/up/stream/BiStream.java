package up.stream;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

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
     * Creates a copy of this stream with the
     * initial values this stream had.
     *
     * @return A copy of this stream.
     */
    protected abstract BiStream<T, U> copy();



    // Intermediate Operations

    /**
     * Applies the given mapper function to each pair of elements
     * and returns a new {@linkplain BiStream bistream} out
     * of the results of the function.
     *
     * <p>The result will be a stream with specialized
     * methods for working with pairs of values.</p>
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param mapper The function to apply.
     * @param <R> The type of the first element in the new pair.
     * @param <S> The type of the second element in the new pair.
     * @return A stream with each pair of elements from this one
     * mapped according to the mapper function.
     * @throws NullPointerException If the mapper is {@code null}.
     */
    public <R, S> BiStream<R, S> biMap(final BiFunction<? super T, ? super U, ? extends Pair<R, S>> mapper) {
        return new BiStreamBiMap<>(this, Objects.requireNonNull(mapper));
    }

    /**
     * Applies the given mapper function to each pair of elements and
     * returns a new stream out of the results of the function.
     *
     * <p>The result will be a stream with specialized methods
     * for working with single values.</p>
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param mapper The function to apply.
     * @param <R> The type of the new elements.
     * @return A stream with each pair of elements from this one
     * mapped according to the mapper function.
     * @throws NullPointerException if the mapper is {@code null}.
     */
    public <R> Stream<R> map(final BiFunction<? super T, ? super U, ? extends R> mapper) {
        return new BiStreamMap<>(this, Objects.requireNonNull(mapper));
    }

    /**
     * Filters this stream keeping all pairs of
     * elements which fail the given predicate.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param filter The predicate to test against.
     * @return A stream containing all pairs of elements
     * which failed the test.
     * @throws NullPointerException If the filter is {@code null}.
     */
    public BiStream<T, U> reject(final BiPredicate<? super T, ? super U> filter) {
        return select(Objects.requireNonNull(filter).negate());
    }

    /**
     * Filters this stream keeping all pairs of elements
     * which pass the given predicate.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param filter The predicate to test against.
     * @return A stream containing all pairs of elements
     * which passed the predicate.
     */
    public BiStream<T, U> select(final BiPredicate<? super T, ? super U> filter) {
        return new BiStreamSelect<>(this, Objects.requireNonNull(filter));
    }

    /**
     * Filters this stream keeping only the unique pairs.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @return A stream containing only unique pairs.
     */
    public BiStream<T, U> unique() {
        return map(Pair::new).unique().biMap(Function.identity());
    }

    /**
     * Drops the specified number of pairs of elements
     * from the beginning of this stream.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param count The number of pairs of elements to drop.
     * @return A stream without the first number
     * of pairs of elements specified.
     */
    public BiStream<T, U> drop(final long count) {
        return map(Pair::new).drop(count).biMap(Function.identity());
    }
}
