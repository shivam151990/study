package design.patterns.flyweight.books;

import java.util.HashMap;
import java.util.Map;

public class BookFactory {
    private static final Map<String, BookType> bookTypes = new HashMap<>();

    public static BookType getBookType(String type, String distributor, String otherData) {
        if (!bookTypes.containsKey(type)) {
            bookTypes.put(type, new BookType(type, distributor, otherData));
        }
        return bookTypes.get(type);
    }
}
