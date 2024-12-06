package design.patterns.flyweight.books;

public class Book {
    private final String name;
    private final double price;

    //Flyweight object
    private final BookType bookType;

    public Book(String name, double price, BookType bookType) {
        this.name = name;
        this.price = price;
        this.bookType = bookType;
    }
}
