# TODO List

## Notes

- In code blocks:
  - `+`  &#8594; implemented
  - ` `  &#8594; will implement
  - `-`  &#8594; might implement

## Possible features

- Create `...IndexedFrom`, `...IndexedByStep` and `...IndexedFromByStep` variant for each `...Indexed` type of method
- Create `TriStream`

## Possible Optimizations

| Where           | What                                                 |
|-----------------|------------------------------------------------------|
| `OfArrayStream` | Don't copy primitive arrays                          |
| everywhere      | Make optimised versions of methods if possible       |
| everywhere      | Check for infinite streams where they aren't allowed |
| `DropStream`    | Make a protected method for dropping                 |

## Creators

### Arrays

```diff
+ of(T...)
+ ofArray(T[])
+ ofBooleanArray(boolean[])
+ ofByteArray(byte[])
+ ofShortArray(short[])
+ ofIntArray(int[])
+ ofLongArray(long[])
+ ofFloatArray(float[])
+ ofDoubleArray(double[])
+ ofCharArray(char[])
```

### Collections

```diff
  ofCollection(Collection<T>)
  ofMap(Map<T, U>)
  ofSet(Set<T>)
```

### Ranges

```diff
+ closedByteRange(byte, byte)
+ closedByteRange(byte, byte, byte)
+ byteRange(byte, byte)
+ byteRange(byte, byte, byte)
+ closedShortRange(short, short)
+ closedShortRange(short, short, short)
+ shortRange(short, short)
+ shortRange(short, short, short)
+ closedIntRange(int, int)
+ closedIntRange(int, int, int)
+ intRange(int, int)
+ intRange(int, int, int)
+ closedLongRange(long, long)
+ closedLongRange(long, long, long)
+ longRange(long, long)
+ longRange(long, long, long)
- countFrom(long)
- countBy(long, long)
```

### Miscellaneous

```diff
+ empty()
+ generate(Supplier<T>)
  iterate(T, UnaryOperator<T>)
- iterate(T, Predicate<T>, UnaryOperator<T>)
```

## Intermediate Operations

### Maps

```diff
  flatMap(Function<T, Stream<R>>) // + other collections ?
+ map(Function<T, R>)
  mapIndexed(BiFunction<Long, T, R>)
  mapMulti(BiConsumer<T, Consumer<T>>)
- bimap(Function<T, Pair<U, V>>)
- castTo(Class<R>)
- mapEveryNth(long, Function<T, R>)
- nullFilteredMap(Function<T, R>)
```

### Scans

```diff
- scanLeft(BinaryOperator<T>)
- scanLeft(R, BiFunction<R, T, R>)
- scanRight(BinaryOperator<T>)
- scanRight(R, BiFunction<R, T, R>)
```

### Filters

```diff
+ reject(Predicate<T>)
  rejectIndexed(BiPredicate<Long, T>)
+ select(Predicate<T>)
  selectIndexed(BiPredicate<Long, T>)
+ unique()
- uniqueByKey(Function<T, R>)
```

### Droppers

```diff
+ drop(long)
  dropUntil(Predicate<T>)
  dropWhile(Predicate<T>)
  take(long)
  takeUntil(Predicate<T>)
  takeWhile(Predicate<T>)
- dropClosedRange(long, long)
- dropRange(long, long)
- takeLast(long)
- stepBy(long)
- takeClosedRange(long, long)
- takeRange(long, long)
```

### Sorts

```diff
  sort()
  sortByKey(Function<T, Comparable>)
  sortByKeyUsing(Function<T, R>, Comparator<R>)
  sortUsing(Comparator<T>)
```

### Inserters

```diff
- append(Stream<T>) // + other collections
- insertAt(long, Stream<T>) // + other collections
- interpose(T)
- prepend(Stream<T>) // + other collections
```

### Order Changers

```diff
- reverse()
- rotateLeft(long)
- rotateRight(left)
- swap(long, long)
- shuffle()
```

### Groupers

```diff
- byWindowsOf(long) // [1, 2, 3, 4, 5] (2) -> [[1, 2], [2, 3], [3, 4], [4, 5]]
- frequencies()
- groupBy(Function<T, R>) // ["Alice", "Bob", "Amy"] (String::length) -> [5 -> ["Alice"], 3 -> ["Bob", "Amy"]]
- inChunksBy(Predicate<T>) // [3, 1, 4, 1, 5, 9, 2, 6] (i -> i % 2 == 0) -> [false -> [3, 1], true -> [4], false -> [1, 5, 9], true -> [2, 6]]
- splitBy(Predicate<T>) // [1, 2, 3, 4, 5] (i -> i % 2 == 0) -> [false -> [1, 3, 5], true -> [2, 4]]
- https://clojuredocs.org/clojure.core/partition
- https://clojuredocs.org/clojure.core/partition-all
- https://clojuredocs.org/clojure.core/partition-by
```

### Miscellaneous

```diff
+ cycle()
+ cycle(long)
  enumerate()
+ inspect(Consumer<T>)
  zip(Stream<U>) // + other collections
- enumerateByStep(long)
- enumerateFrom(long)
- enumerateFromByStep(long, long)
- mergeWith(Stream<U>, BiFunction<T, U, R>)
```

## Terminal Operations

### Predicates

```diff
allMatch(Predicate<T>)
  anyMatch(Predicate<T>)
  noneMatch(Predicate<T>)
  isEmpty()
  isSorted()
  isSortedByKey(Function<T, Comparable>)
  isSortedByKeyUsing(Function<T, R>, Comparator<R>)
  isSortedUsing(Comparator<T>)
- contains(T)
```

### Element Extractors

```diff
  first()
  firstOrElse(T)
  firstOrElseGet(Supplier<T>)
  firstRejected(Predicate<T>)
  firstRejectedIndexed(BiPredicate<Long, T>)
  firstSelected(Predicate<T>)
  firstSelectedIndexed(BiPredicate<Long, T>)
- indexOfFirstRejected(Predicate<T>)
- indexOfFirstSelected(Predicate<T>)
- equivalents for last element
- nth(long)
```

### Reductions

```diff
  count()
  countRejected(Predicate<T>)
  countSelected(Predicate<T>)
  toArray() // + primitive types
  toList()
  max and min functions
  fold(BinaryOperator<T>)
  reduce(R, BiFunction<R, T, R>)
- iterator()
- spliterator()
- minmax functions
```

### Miscellaneous

```diff
+ forEach(Consumer<T>)
  forEachIndexed(BiConsumer<Long, T>)
- collect(Supplier<R>, BiConsumer<R, T>, BiConsumer<R, R>)
- collect(Collector<T, A, R>)
```

## Common Functions

### Strings

```diff
- chars
- words
- lines
- joining
```

### Numbers

```diff
- inRange
- inRangeClosed
- product
- sum
```

### Predicates

```diff
- nullValues // reject
- nonNullValues // select
- notNull // takeWhile
```

### Miscellaneous

```diff
- combinations // ???
- permutations // stream of streams
- union // ???
- unzip // BiStream into two streams
```
