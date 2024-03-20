import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

class Author implements Serializable {
    private String name;

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                '}';
    }
}

class Book implements Serializable {
    private String title;
    private Author author;

    public Book(String title, Author author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author=" + author +
                '}';
    }
}

class Bookshelf implements Serializable {
    private HashMap<Book, Integer> books;

    public Bookshelf() {
        books = new HashMap<>();
    }

    public void addBook(Book book, int quantity) {
        books.put(book, quantity);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "Bookshelf{" +
                "books=" + books +
                '}';
    }
}

class Reader {
    private String name;

    public Reader(String name) {
        this.name = name;
    }

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

class Rental implements Serializable {
    private Reader reader;
    private Book book;
    private int days;

    public Rental(Reader reader, Book book, int days) {
        this.reader = reader;
        this.book = book;
        this.days = days;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "reader=" + reader +
                ", book=" + book +
                ", days=" + days +
                '}';
    }
}

public class LibrarySystem implements Serializable {
    private ArrayList<Reader> readers;
    private Bookshelf bookshelf;
    private ArrayList<Rental> rentals;

    public LibrarySystem() {
        readers = new ArrayList<>();
        bookshelf = new Bookshelf();
        rentals = new ArrayList<>();
    }

    public void addReader(Reader reader) {
        readers.add(reader);
    }

    public void removeReader(Reader reader) {
        readers.remove(reader);
    }

    public void rentBook(Reader reader, Book book, int days) {
        rentals.add(new Rental(reader, book, days));
    }

    public void returnBook(Reader reader, Book book) {
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getReader().equals(reader) && rental.getBook().equals(book)) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
        }
    }

    public ArrayList<Reader> getReaders() {
        return readers;
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    public ArrayList<Rental> getRentals() {
        return rentals;
    }

    public static void serializeLibrarySystem(LibrarySystem librarySystem, String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(librarySystem);
            System.out.println("Library system serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LibrarySystem deserializeLibrarySystem(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return (LibrarySystem) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // Creating sample data
        Author author1 = new Author("John Doe");
        Author author2 = new Author("Jane Smith");

        Book book1 = new Book("Java Programming", author1);
        Book book2 = new Book("Python Programming", author2);

        Reader reader1 = new Reader("Alice");
        Reader reader2 = new Reader("Bob");

        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.addReader(reader1);
        librarySystem.addReader(reader2);

        librarySystem.getBookshelf().addBook(book1, 5);
        librarySystem.getBookshelf().addBook(book2, 3);

        librarySystem.rentBook(reader1, book1, 7);
        librarySystem.rentBook(reader2, book2, 5);

        // Serializing and deserializing
        serializeLibrarySystem(librarySystem, "library_system.ser");
        LibrarySystem deserializedLibrarySystem = deserializeLibrarySystem("library_system.ser");

        // Checking deserialized data
        System.out.println("Deserialized Library System:");
        System.out.println(deserializedLibrarySystem.getReaders());
        System.out.println(deserializedLibrarySystem.getBookshelf());
        System.out.println(deserializedLibrarySystem.getRentals());
    }
}
