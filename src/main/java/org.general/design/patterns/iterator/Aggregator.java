package design.patterns.iterator;

public interface Aggregator<T> {
    Iterator<T> getIterator();
}
