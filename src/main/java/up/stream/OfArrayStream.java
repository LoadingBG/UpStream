package up.stream;

import java.util.Optional;

final class OfArrayStream<T> extends Stream<T> {
    private final T[] source;
    private final int len;
    private int currIndex;

    OfArrayStream(final T[] source) {
        this.source = source;
        len = source.length;
        currIndex = 0;
    }

    static OfArrayStream<Boolean> of(final boolean[] source) {
        final int len = source.length;
        final Boolean[] res = new Boolean[len];
        for (int i = 0; i < len; ++i) {
            res[i] = source[i];
        }
        return new OfArrayStream<>(res);
    }

    static OfArrayStream<Byte> of(final byte[] source) {
        final int len = source.length;
        final Byte[] res = new Byte[len];
        for (int i = 0; i < len; ++i) {
            res[i] = source[i];
        }
        return new OfArrayStream<>(res);
    }

    static OfArrayStream<Short> of(final short[] source) {
        final int len = source.length;
        final Short[] res = new Short[len];
        for (int i = 0; i < len; ++i) {
            res[i] = source[i];
        }
        return new OfArrayStream<>(res);
    }

    static OfArrayStream<Integer> of(final int[] source) {
        final int len = source.length;
        final Integer[] res = new Integer[len];
        for (int i = 0; i < len; ++i) {
            res[i] = source[i];
        }
        return new OfArrayStream<>(res);
    }

    static OfArrayStream<Long> of(final long[] source) {
        final int len = source.length;
        final Long[] res = new Long[len];
        for (int i = 0; i < len; ++i) {
            res[i] = source[i];
        }
        return new OfArrayStream<>(res);
    }

    static OfArrayStream<Float> of(final float[] source) {
        final int len = source.length;
        final Float[] res = new Float[len];
        for (int i = 0; i < len; ++i) {
            res[i] = source[i];
        }
        return new OfArrayStream<>(res);
    }

    static OfArrayStream<Double> of(final double[] source) {
        final int len = source.length;
        final Double[] res = new Double[len];
        for (int i = 0; i < len; ++i) {
            res[i] = source[i];
        }
        return new OfArrayStream<>(res);
    }

    static OfArrayStream<Character> of(final char[] source) {
        final int len = source.length;
        final Character[] res = new Character[len];
        for (int i = 0; i < len; ++i) {
            res[i] = source[i];
        }
        return new OfArrayStream<>(res);
    }

    @Override
    protected Optional<T> next() {
        return currIndex < len ? Optional.of(source[currIndex++]) : Optional.empty();
    }

    @Override
    protected Stream<T> copy() {
        return new OfArrayStream<>(source);
    }
}
