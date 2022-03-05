package up.stream.util;

import java.util.Objects;

/**
 * An immutable class for storing pairs of values.
 *
 * <p>This class is constructed as if it was
 * a <a href="https://docs.oracle.com/en/java/javase/16/language/records.html">record</a></p>
 *
 * @param <T> The type of the first value.
 * @param <U> The type of the second value.
 */
public final class Pair<T, U> {
    private final T first;
    private final U second;

    public Pair(final T first, final U second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns the first value.
     *
     * @return The first value.
     */
    public T first() {
        return first;
    }

    /**
     * Returns the second value.
     *
     * @return The second value.
     */
    public U second() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Pair(" + first + ", " + second + ")";
    }
}
