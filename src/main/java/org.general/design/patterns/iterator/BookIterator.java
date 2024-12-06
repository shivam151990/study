package design.patterns.iterator;

import java.util.List;

public class BookIterator implements Iterator<Book> {

    private List<Book> books;
    private int idx;
    public BookIterator(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean hasNext() {
        return idx <= books.size() - 1;
    }

    @Override
    public Book next() {
        return books.get(idx++);
    }
}
