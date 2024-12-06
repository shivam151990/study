package design.patterns.iterator;

import java.util.List;

public class Library implements Aggregator<Book> {

    private final List<Book> books;

    public Library(List<Book> books) {
        this.books = books;
    }

    @Override
    public Iterator<Book> getIterator() {
        return new BookIterator(books);
    }
}
