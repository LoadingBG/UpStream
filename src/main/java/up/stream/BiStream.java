package up.stream;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
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

    /**
     * Drops pairs of elements from the beginning of
     * this stream until a pair which passes the
     * predicate is found.
     *
     * Even if a pair which fails the predicate is
     * found later, it won't be dropped.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param predicate The predicate to test against.
     * @return A stream with the first sequence of
     * pairs of elements failing the predicate dropped.
     * @throws NullPointerException If the predicate is {@code null}.
     */
    public BiStream<T, U> dropUntil(final BiPredicate<? super T, ? super U> predicate) {
        return new BiStreamDropUntil<>(this, Objects.requireNonNull(predicate));
    }

    /**
     * Drops pairs of elements from the beginning of
     * this stream until a pair which fails the
     * predicate is found.
     *
     * Even if a pair which passes the predicate
     * is found later, it won't be dropped.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param predicate The predicate to test against.
     * @return A stream with the first sequence of
     * pairs of elements passing the predicate dropped.
     * @throws NullPointerException If the predicate is {@code null}.
     */
    public BiStream<T, U> dropWhile(final BiPredicate<? super T, ? super U> predicate) {
        return dropUntil(Objects.requireNonNull(predicate).negate());
    }

    /**
     * Takes the first {@code count} pairs of elements
     * from this stream and discards the rest.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param count The number of pairs to take.
     * @return A stream with the first {@code count}
     * pairs of elements from this stream.
     */
    public BiStream<T, U> take(final long count) {
        return map(Pair::new).take(count).biMap(Function.identity());
    }

    /**
     * Takes pairs of elements from this stream until a
     * pair which passes the predicate is found.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param predicate The predicate to test against.
     * @return A stream with the first sequence of
     * pairs of elements which fail the predicate.
     * @throws NullPointerException If the predicate is {@code null}.
     */
    public BiStream<T, U> takeUntil(final BiPredicate<? super T, ? super U> predicate) {
        return takeWhile(Objects.requireNonNull(predicate).negate());
    }

    /**
     * Takes pairs of elements from this stream
     * while they pass the predicate.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param predicate The predicate to test against.
     * @return A stream with the first sequence of
     * pairs of elements which pass the predicate.
     * @throws NullPointerException If the predicate is {@code null}.
     */
    public BiStream<T, U> takeWhile(final BiPredicate<? super T, ? super U> predicate) {
        return new BiStreamTakeWhile<>(this, Objects.requireNonNull(predicate));
    }

    /**
     * Cycles this stream infinitely many times.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @return A stream with this stream's elements cycled.
     */
    public BiStream<T, U> cycle() {
        return map(Pair::new).cycle().biMap(Function.identity());
    }

    /**
     * Cycles this stream the specified number
     * of times. If {@code times} is less than 1,
     * an {@linkplain Stream#empty() empty stream} will
     * be returned.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param times The number of times to cycle
     *              the elements.
     * @return A stream with this stream's elements cycled.
     */
    public BiStream<T, U> cycle(final long times) {
        return map(Pair::new).cycle(times).biMap(Function.identity());
    }

    /**
     * Applies an action to each pair of elements without altering it.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param action The action to apply.
     * @return An unaltered stream.
     * @throws NullPointerException If the action is {@code null}.
     */
    public BiStream<T, U> inspect(final BiConsumer<? super T, ? super U> action) {
        Objects.requireNonNull(action);
        return biMap((t, u) -> {
            action.accept(t, u);
            return new Pair<>(t, u);
        });
    }
}
