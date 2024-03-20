import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Author implements Externalizable {
    private String name;

    public Author() {}

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
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                '}';
    }
}

class Book implements Externalizable {
    private String title;
    private Author author;

    public Book() {}

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
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(title);
        out.writeObject(author);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        title = (String) in.readObject();
        author = (Author) in.readObject();
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author=" + author +
                '}';
    }
}

class Bookshelf implements Externalizable {
    private List<Book> books;

    public Bookshelf() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(books);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        books = (List<Book>) in.readObject();
    }

    @Override
    public String toString() {
        return "Bookshelf{" +
                "books=" + books +
                '}';
    }
}

public class Main3 {
    public static void main(String[] args) {
        // Creating authors
        Author author1 = new Author("J.K.Rowling");
        Author author2 = new Author("F.Scott Fitzgerald");

        // Creating books
        Book book1 = new Book("Harry Potter", author1);
        Book book2 = new Book("The great Gatsby", author2);

        // Creating bookshelf
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.addBook(book1);
        bookshelf.addBook(book2);

        // Serializing bookshelf to a file
        serialize(bookshelf, "bookshelf.ser");

        // Deserializing bookshelf from a file
        Bookshelf deserializedBookshelf = (Bookshelf) deserialize("bookshelf.ser");
        System.out.println(deserializedBookshelf);
    }

    private static void serialize(Object object, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(object);
            System.out.println("Serialized successfully to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Object deserialize(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}