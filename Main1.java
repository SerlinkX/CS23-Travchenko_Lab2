import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Клас, що представляє книгу
class Book implements Serializable {
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // Геттери та сеттери
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}

// Клас, що представляє читача
class Reader implements Serializable {
    private String name;

    public Reader(String name) {
        this.name = name;
    }

    // Геттер та сеттер
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Reader{" +
                "name='" + name + '\'' +
                '}';
    }
}

// Клас, що представляє бібліотеку
class Library implements Serializable {
    private List<Book> books;
    private List<Reader> readers;

    public Library() {
        this.books = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    // Метод для додавання книги
    public void addBook(Book book) {
        books.add(book);
    }

    // Метод для додавання читача
    public void addReader(Reader reader) {
        readers.add(reader);
    }

    @Override
    public String toString() {
        return "Library{" +
                "books=" + books +
                ", readers=" + readers +
                '}';
    }

    // Метод для серіалізації
    public void serialize(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        }
    }

    // Метод для десеріалізації
    public static Library deserialize(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (Library) in.readObject();
        }
    }
}

public class Main1 {
    public static void main(String[] args) {
        // Створення бібліотеки
        Library library = new Library();

        // Додавання книг та читачів
        library.addBook(new Book("Java Programming", "John Doe"));
        library.addBook(new Book("Python Basics", "Jane Smith"));
        library.addReader(new Reader("Alice"));
        library.addReader(new Reader("Bob"));

        // Виведення поточного стану бібліотеки
        System.out.println("Current state of the library:");
        System.out.println(library);

        // Серіалізація
        try {
            library.serialize("library.ser");
            System.out.println("Library serialized successfully.");
        } catch (IOException e) {
            System.err.println("Error occurred during serialization: " + e.getMessage());
        }

        // Десеріалізація
        try {
            Library deserializedLibrary = Library.deserialize("library.ser");
            System.out.println("Deserialized library:");
            System.out.println(deserializedLibrary);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error occurred during deserialization: " + e.getMessage());
        }
    }
}
