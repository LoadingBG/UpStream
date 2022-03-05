package up.stream;

import java.util.Optional;

final class StreamOfArray<T> extends Stream<T> {
    private final T[] source;
    private final int len;
    private int currIndex;

    StreamOfArray(final T[] source) {
        this.source = source;
        len = source.length;
        currIndex = 0;
    }

    static StreamOfArray<Boolean> of(final boolean[] source) {
        final int len = source.length;
        final Boolean[] res = new Boolean[len];
        for (int i = 0; i < len; ++i) {
            res[i] = source[i];
        }
        return new StreamOfArray<>(res);
    }

    static StreamOfArray<Byte> of(final byte[] source) {
        final int len = source.length;
        final Byte[] res = new Byte[len];
        for (int i = 0; i < len; ++i) {
            res[i] = source[i];
        }
        return new StreamOfArray<>(res);
    }

    static StreamOfArray<Short> of(final short[] source) {
        final int len = source.length;
        final Short[] res = new Short[len];
        for (int i = 0; i < len; ++i) {
            res[i] = source[i];
        }
        return new StreamOfArray<>(res);
    }

    static StreamOfArray<Integer> of(final int[] source) {
        final int len = source.length;
        final Integer[] res = new Integer[len];
        for (int i = 0; i < len; ++i) {
            res[i] = source[i];
        }
        return new StreamOfArray<>(res);
    }

    static StreamOfArray<Long> of(final long[] source) {
        final int len = source.length;
        final Long[] res = new Long[len];
        for (int i = 0; i < len; ++i) {
            res[i] = source[i];
        }
        return new StreamOfArray<>(res);
    }

    static StreamOfArray<Float> of(final float[] source) {
        final int len = source.length;
        final Float[] res = new Float[len];
        for (int i = 0; i < len; ++i) {
            res[i] = source[i];
        }
        return new StreamOfArray<>(res);
    }

    static StreamOfArray<Double> of(final double[] source) {
        final int len = source.length;
        final Double[] res = new Double[len];
        for (int i = 0; i < len; ++i) {
            res[i] = source[i];
        }
        return new StreamOfArray<>(res);
    }

    static StreamOfArray<Character> of(final char[] source) {
        final int len = source.length;
        final Character[] res = new Character[len];
        for (int i = 0; i < len; ++i) {
            res[i] = source[i];
        }
        return new StreamOfArray<>(res);
    }

    @Override
    protected Optional<T> next() {
        return currIndex < len ? Optional.of(source[currIndex++]) : Optional.empty();
    }

    @Override
    protected Stream<T> copy() {
        return new StreamOfArray<>(source);
    }
}
