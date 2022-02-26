package up.stream;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A possibly infinite sequence of lazily evaluated elements
 * supporting operations which act on the elements.
 *
 * <pre><code>
 * // for loops
 * public void disconnectOnlineUsers(List&lt;User&gt; users) {
 *     for (User user : users) {
 *         if (user.isOnline()) {
 *             user.disconnect();
 *             System.out.println("Disconnected user: " + user);
 *         }
 *     }
 * }
 * // streams
 * public void disconnectOnlineUsers(List&lt;User&gt; users) {
 *     Stream.ofCollection(users)
 *           .select(User::isOnline)
 *           .forEach(user -> {
 *               user.disconnect();
 *               System.out.println("Disconnected user: " + user);
 *           });
 * }
 * </code></pre>
 *
 * Streams are composed of creator, a chain of intermediate
 * operations and a terminal operation.
 *
 * <h2>Lazy evaluation</h2>
 * Streams are lazily evaluated. This means that each element
 * is evaluated only when needed. If not needed, no computation
 * will be performed and the whole chain of operations will
 * effectively be a no-op.
 *
 * <h2>Infinite sequences</h2>
 * Since each element is computed only when needed and doesn't
 * exist before that, streams are able to contain an infinite number
 * of elements. Infinite streams are dangerous:
 * <ul>
 *     <li>Filters can cause an infinite loop.</li>
 *     <li>Terminal operations WILL cause an infinite loop.</li>
 * </ul>
 *
 * <h2>Operations</h2>
 * Streams support two kinds of operations: <i>intermediate operations</i>
 * and <i>terminal operations</i>.
 *
 * <h3>Intermediate operations</h3>
 * Intermediate operations are operations which produce a new stream
 * out of this one. No elements are evaluated in the process. This type
 * of operation is a no-op without a terminal operation since no elements
 * will be requested, therefore none will be computed.
 *
 * <h3>Terminal operations</h3>
 * Terminal operations are operations which produce a value or
 * a side effect out of this stream. They finish the chain of operations.
 * When called, they start requesting the computation of the elements
 * of this stream and perform an action which might result in a new
 * value being created. The new value will NOT be a stream. If the
 * chain of operations doesn't finish in such an operation, no elements
 * will be computed, hence the whole chain will effectively be a no-op.
 *
 * @param <T> The type of the elements.
 */
public abstract class Stream<T> {
    /**
     * Computes the {@link Optional} holding the next element in this stream.
     *
     * <p>An empty {@link Optional} means that this is the end
     * of this stream and there will be no more elements.</p>
     *
     * @return An {@link Optional} holding the
     * next element in this stream.
     */
    protected abstract Optional<T> next();

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
    protected abstract Stream<T> copy();



    // Creators

    /**
     * Creates a stream with no elements.
     *
     * @param <T> The type of the elements if
     *           they were present.
     * @return An empty stream.
     */
    public static <T> Stream<T> empty() {
        return new EmptyStream<>();
    }

    /**
     * Creates a new stream with the given values.
     *
     * <p>If a stream created of an array is wanted,
     * {@link #ofArray(Object[]) ofArray} should be used
     * instead.</p>
     *
     * @param values The elements to create the stream
     *               out of.
     * @param <T> The type of the elements.
     * @return A stream with the given elements.
     *
     * @see #ofArray(Object[]) ofArray
     */
    @SafeVarargs
    public static <T> Stream<T> of(final T... values) {
        return new OfArrayStream<>(values);
    }

    /**
     * Creates a new stream out of the elements of the given array.
     *
     * <p>If a stream out of a primitive array is wanted, one of
     * {@link #ofBooleanArray(boolean[]) ofBooleanArray},
     * {@link #ofByteArray(byte[]) ofByteArray},
     * {@link #ofShortArray(short[]) ofShortArray},
     * {@link #ofIntArray(int[]) ofIntArray},
     * {@link #ofLongArray(long[]) ofLongArray},
     * {@link #ofFloatArray(float[]) ofFloatArray},
     * {@link #ofDoubleArray(double[]) ofDoubleArray} or
     * {@link #ofCharArray(char[]) ofCharArray} should be used.</p>
     *
     * @param objects The array to create the stream out of.
     * @param <T> The type of the elements in the array.
     * @return A stream with the values of the array.
     * @throws NullPointerException If the array is {@code null}.
     *
     * @see #ofBooleanArray(boolean[]) ofBooleanArray
     * @see #ofByteArray(byte[]) ofByteArray
     * @see #ofShortArray(short[]) ofShortArray
     * @see #ofIntArray(int[]) ofIntArray
     * @see #ofLongArray(long[]) ofLongArray
     * @see #ofFloatArray(float[]) ofFloatArray
     * @see #ofDoubleArray(double[]) ofDoubleArray
     * @see #ofCharArray(char[]) ofCharArray
     */
    public static <T> Stream<T> ofArray(final T[] objects) {
        return new OfArrayStream<>(Objects.requireNonNull(objects));
    }

    /**
     * Creates a new stream out of the booleans in the given array.
     *
     * @param booleans The array to create the stream out of.
     * @return A stream with the booleans from the array.
     * @throws NullPointerException If the array is {@code null}.
     */
    public static Stream<Boolean> ofBooleanArray(final boolean[] booleans) {
        return OfArrayStream.of(Objects.requireNonNull(booleans));
    }

    /**
     * Creates a new stream out of the bytes in the given array.
     *
     * @param bytes The array to create the stream out of.
     * @return A stream with the bytes from the array.
     * @throws NullPointerException If the array is {@code null}.
     */
    public static Stream<Byte> ofByteArray(final byte[] bytes) {
        return OfArrayStream.of(Objects.requireNonNull(bytes));
    }

    /**
     * Creates a new stream out of the shorts in the given array.
     *
     * @param shorts The array to create the stream out of.
     * @return A stream with the shorts from the array.
     * @throws NullPointerException If the array is {@code null}.
     */
    public static Stream<Short> ofShortArray(final short[] shorts) {
        return OfArrayStream.of(Objects.requireNonNull(shorts));
    }

    /**
     * Creates a new stream out of the ints in the given array.
     *
     * @param ints The array to create the stream out of.
     * @return A stream with the ints from the array.
     * @throws NullPointerException If the array is {@code null}.
     */
    public static Stream<Integer> ofIntArray(final int[] ints) {
        return OfArrayStream.of(Objects.requireNonNull(ints));
    }

    /**
     * Creates a new stream out of the longs in the given array.
     *
     * @param longs The array to create the stream out of.
     * @return A stream with the longs from the array.
     * @throws NullPointerException If the array is {@code null}.
     */
    public static Stream<Long> ofLongArray(final long[] longs) {
        return OfArrayStream.of(Objects.requireNonNull(longs));
    }

    /**
     * Creates a new stream out of the floats in the given array.
     *
     * @param floats The array to create the stream out of.
     * @return A stream with the floats from the array.
     * @throws NullPointerException If the array is {@code null}.
     */
    public static Stream<Float> ofFloatArray(final float[] floats) {
        return OfArrayStream.of(Objects.requireNonNull(floats));
    }

    /**
     * Creates a new stream out of the doubles in the given array.
     *
     * @param doubles The array to create the stream out of.
     * @return A stream with the doubles from the array.
     * @throws NullPointerException If the array is {@code null}.
     */
    public static Stream<Double> ofDoubleArray(final double[] doubles) {
        return OfArrayStream.of(Objects.requireNonNull(doubles));
    }

    /**
     * Creates a new stream out of the chars in the given array.
     *
     * @param chars The array to create the stream out of.
     * @return A stream with the chars from the array.
     * @throws NullPointerException If the array is {@code null}.
     */
    public static Stream<Character> ofCharArray(final char[] chars) {
        return OfArrayStream.of(Objects.requireNonNull(chars));
    }

    /**
     * Creates a new stream of bytes of the values
     * in the given range (inclusive) with an
     * increment of 1.
     *
     * @param startInclusive The start of the range.
     * @param endInclusive The end of the range.
     * @return A stream with the values in the range.
     *
     * @see #closedByteRange(byte, byte, byte)
     */
    public static Stream<Byte> closedByteRange(final byte startInclusive, final byte endInclusive) {
        return closedByteRange(startInclusive, endInclusive, (byte) 1);
    }

    /**
     * Creates a new stream of bytes of the values
     * in the given range (inclusive) with the given
     * increment.
     *
     * @param startInclusive The start of the range.
     * @param endInclusive The end of the range.
     * @param increment The increment for each value.
     * @return A stream with the values in the range.
     *
     * @see #byteRange(byte, byte, byte)
     */
    public static Stream<Byte> closedByteRange(final byte startInclusive, final byte endInclusive, final byte increment) {
        return byteRange(startInclusive, (byte) (endInclusive + 1), increment);
    }

    /**
     * Creates a new stream of bytes of the values
     * in the given range (exclusive) with an
     * increment of 1.
     *
     * @param startInclusive The start of the range.
     * @param endExclusive The end of the range.
     * @return A stream with the values in the range.
     *
     * @see #byteRange(byte, byte, byte)
     */
    public static Stream<Byte> byteRange(final byte startInclusive, final byte endExclusive) {
        return byteRange(startInclusive, endExclusive, (byte) 1);
    }

    /**
     * Creates a new stream of bytes of the values
     * in the given range (exclusive) with the given
     * increment.
     *
     * @param startInclusive The start of the range.
     * @param endExclusive The end of the range.
     * @param increment The increment for each value.
     * @return A stream with the values in the range.
     */
    public static Stream<Byte> byteRange(final byte startInclusive, final byte endExclusive, final byte increment) {
        return new ByteRangeStream(startInclusive, endExclusive, increment);
    }

    /**
     * Creates a new stream of shorts of the values
     * in the given range (inclusive) with an
     * increment of 1.
     *
     * @param startInclusive The start of the range.
     * @param endInclusive The end of the range.
     * @return A stream with the values in the range.
     *
     * @see #closedShortRange(short, short, short)
     */
    public static Stream<Short> closedShortRange(final short startInclusive, final short endInclusive) {
        return closedShortRange(startInclusive, endInclusive, (short) 1);
    }

    /**
     * Creates a new stream of shorts of the values
     * in the given range (inclusive) with the given
     * increment.
     *
     * @param startInclusive The start of the range.
     * @param endInclusive The end of the range.
     * @param increment The increment for each value.
     * @return A stream with the values in the range.
     *
     * @see #shortRange(short, short, short)
     */
    public static Stream<Short> closedShortRange(final short startInclusive, final short endInclusive, final short increment) {
        return shortRange(startInclusive, (short) (endInclusive + 1), increment);
    }

    /**
     * Creates a new stream of shorts of the values
     * in the given range (exclusive) with an
     * increment of 1.
     *
     * @param startInclusive The start of the range.
     * @param endExclusive The end of the range.
     * @return A stream with the values in the range.
     *
     * @see #shortRange(short, short, short)
     */
    public static Stream<Short> shortRange(final short startInclusive, final short endExclusive) {
        return shortRange(startInclusive, endExclusive, (short) 1);
    }

    /**
     * Creates a new stream of shorts of the values
     * in the given range (exclusive) with the given
     * increment.
     *
     * @param startInclusive The start of the range.
     * @param endExclusive The end of the range.
     * @param increment The increment for each value.
     * @return A stream with the values in the range.
     */
    public static Stream<Short> shortRange(final short startInclusive, final short endExclusive, final short increment) {
        return new ShortRangeStream(startInclusive, endExclusive, increment);
    }

    /**
     * Creates a new stream of integers of the
     * values in the given range (inclusive)
     * with an increment of 1.
     *
     * @param startInclusive The start of the range.
     * @param endInclusive The end of the range.
     * @return A stream with the values in the range.
     *
     * @see #closedIntRange(int, int, int)
     */
    public static Stream<Integer> closedIntRange(final int startInclusive, final int endInclusive) {
        return closedIntRange(startInclusive, endInclusive, 1);
    }

    /**
     * Creates a new stream of integers of the values
     * in the given range (inclusive) with the given
     * increment.
     *
     * @param startInclusive The start of the range.
     * @param endInclusive The end of the range.
     * @param increment The increment for each value.
     * @return A stream with the values in the range.
     *
     * @see #intRange(int, int, int)
     */
    public static Stream<Integer> closedIntRange(final int startInclusive, final int endInclusive, final int increment) {
        return intRange(startInclusive, endInclusive + 1, increment);
    }

    /**
     * Creates a new stream of integers of the
     * values in the given range (exclusive)
     * with an increment of 1.
     *
     * @param startInclusive The start of the range.
     * @param endExclusive The end of the range.
     * @return A stream with the values in the range.
     *
     * @see #intRange(int, int, int)
     */
    public static Stream<Integer> intRange(final int startInclusive, final int endExclusive) {
        return intRange(startInclusive, endExclusive, 1);
    }

    /**
     * Creates a new stream of integers of the values
     * in the given range (exclusive) with the given
     * increment.
     *
     * @param startInclusive The start of the range.
     * @param endExclusive The end of the range.
     * @param increment The increment for each value.
     * @return A stream with the values in the range.
     */
    public static Stream<Integer> intRange(final int startInclusive, final int endExclusive, final int increment) {
        return new IntRangeStream(startInclusive, endExclusive, increment);
    }

    /**
     * Creates a new stream of longs of the values
     * in the given range (inclusive) with an
     * increment of 1.
     *
     * @param startInclusive The start of the range.
     * @param endInclusive The end of the range.
     * @return A stream with the values in the range.
     *
     * @see #closedLongRange(long, long, long)
     */
    public static Stream<Long> closedLongRange(final long startInclusive, final long endInclusive) {
        return closedLongRange(startInclusive, endInclusive, 1);
    }

    /**
     * Creates a new stream of longs of the values
     * in the given range (inclusive) with the given
     * increment.
     *
     * @param startInclusive The start of the range.
     * @param endInclusive The end of the range.
     * @param increment The increment for each value.
     * @return A stream with the values in the range.
     *
     * @see #longRange(long, long, long)
     */
    public static Stream<Long> closedLongRange(final long startInclusive, final long endInclusive, final long increment) {
        return longRange(startInclusive, endInclusive + 1, increment);
    }

    /**
     * Creates a new stream of longs of the values
     * in the given range (exclusive) with an
     * increment of 1.
     *
     * @param startInclusive The start of the range.
     * @param endExclusive The end of the range.
     * @return A stream with the values in the range.
     *
     * @see #longRange(long, long, long)
     */
    public static Stream<Long> longRange(final long startInclusive, final long endExclusive) {
        return longRange(startInclusive, endExclusive, 1);
    }

    /**
     * Creates a new stream of longs of the values
     * in the given range (exclusive) with the given
     * increment.
     *
     * @param startInclusive The start of the range.
     * @param endExclusive The end of the range.
     * @param increment The increment for each value.
     * @return A stream with the values in the range.
     */
    public static Stream<Long> longRange(final long startInclusive, final long endExclusive, final long increment) {
        return new LongRangeStream(startInclusive, endExclusive, increment);
    }

    /**
     * Creates a new stream by repeatedly getting
     * values from the given generator.
     *
     * @param generator The generator of values.
     * @param <T> The type of the elements.
     * @return A stream with the generated values.
     */
    public static <T> Stream<T> generate(final Supplier<? extends T> generator) {
        return new GenerateStream<>(Objects.requireNonNull(generator));
    }



    // Intermediate Operations

    /**
     * Applies the given mapper function to each element and
     * returns a new stream out of the results of the function.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param mapper The function to apply.
     * @param <R> The type of the new elements.
     * @return A stream with each element from this one mapped
     * according to the mapper function.
     * @throws NullPointerException if the mapper is {@code null}.
     */
    public <R> Stream<R> map(final Function<? super T, ? extends R> mapper) {
        return new MapStream<>(this, Objects.requireNonNull(mapper));
    }

    /**
     * Filters this stream keeping all elements which fail
     * the given predicate.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @implNote If this stream is infinite, this operation
     * will result in an infinite loop.
     *
     * @param filter The predicate to test against.
     * @return A stream containing all elements which failed
     * the test.
     * @throws NullPointerException If the filter is {@code null}.
     */
    public Stream<T> reject(final Predicate<? super T> filter) {
        return new RejectStream<>(this, Objects.requireNonNull(filter));
    }

    /**
     * Filters this stream keeping all elements which pass
     * the given predicate.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @implNote If this stream is infinite, this operation
     * will result in an infinite loop.
     *
     * @param filter The predicate to test against.
     * @return A stream containing all elements which
     * passed the predicate.
     */
    public Stream<T> select(final Predicate<? super T> filter) {
        return new RejectStream<>(this, Objects.requireNonNull(filter).negate());
    }

    /**
     * Applies an action to each element without altering it.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param action The action to apply.
     * @return An unaltered stream.
     */
    public Stream<T> inspect(final Consumer<? super T> action) {
        Objects.requireNonNull(action);
        return map(value -> {
            action.accept(value);
            return value;
        });
    }

    /**
     * Filters this stream keeping only the unique values.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @return A stream containing only unique values.
     */
    public Stream<T> unique() {
        return new UniqueStream<>(this);
    }

    /**
     * Drops the specified number of elements
     * from the beginning of this stream.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param elemsToDrop The number of elements
     *                    to drop.
     * @return A stream without the first number
     * of elements specified.
     */
    public Stream<T> drop(final long elemsToDrop) {
        return new DropStream<>(this, elemsToDrop);
    }

    /**
     * Drops elements from the beginning of this stream
     * until an element which passes the predicate is
     * found.
     *
     * Even if an element which fails the predicate is
     * found later, it won't be dropped.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param predicate The predicate to test against.
     * @return A stream with the first sequence of
     * elements failing the predicate dropped.
     */
    public Stream<T> dropUntil(final Predicate<? super T> predicate) {
        return new DropUntilStream<>(this, Objects.requireNonNull(predicate));
    }

    /**
     * Drops elements from the beginning of this stream
     * until an element which fails the predicate is
     * found.
     *
     * Even if an element which passes the predicate
     * is found later, it won't be dropped.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param predicate The predicate to test against.
     * @return A stream with the first sequence of
     * elements passing the predicate dropped.
     */
    public Stream<T> dropWhile(final Predicate<? super T> predicate) {
        return new DropUntilStream<>(this, Objects.requireNonNull(predicate).negate());
    }

    /**
     * Takes the first {@code count} elements from
     * this stream and discards the rest.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param count The number of elements to take.
     * @return A stream with the first {@code count}
     * elements from this stream.
     */
    public Stream<T> take(final long count) {
        return new TakeStream<>(this, count);
    }

    /**
     * Cycles this stream infinitely many times.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @return A stream with this stream's elements cycled.
     */
    public Stream<T> cycle() {
        return new CycleStream<>(this, -1);
    }

    /**
     * Cycles this stream the specified number
     * of times. If {@code times} is less than 1,
     * an {@linkplain #empty() empty stream} will
     * be returned.
     *
     * <p>This is an intermediate operation.</p>
     *
     * @param times The number of times to cycle
     *              the elements.
     * @return A stream with this stream's elements cycled.
     */
    public Stream<T> cycle(final long times) {
        if (times < 1) {
            return empty();
        }
        return new CycleStream<>(this, times);
    }



    // Terminal Operations

    /**
     * Performs an action on each element of this stream.
     *
     * <p>This is a terminal operation.</p>
     *
     * @param action The action to perform.
     * @throws NullPointerException If the action is {@code null}.
     */
    public void forEach(final Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (Optional<T> curr = next(); curr.isPresent(); curr = next()) {
            action.accept(curr.get());
        }
    }
}
