package design.patterns.iterator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Book> books = List.of(
                new Book("book1", 1000),
                new Book("book2", 2000),
                new Book("book3", 3000),
                new Book("book4", 4000)
                );
        Library library = new Library(books);
        Iterator<Book> itr = library.getIterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }
}
