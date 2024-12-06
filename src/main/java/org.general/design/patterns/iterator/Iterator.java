package design.patterns.iterator;

public interface Iterator<T> {
    boolean hasNext();
    T next();
}
