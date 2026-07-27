import java.util.Objects;

public final class Book {
    private final int id;
    private final String name;
    private final String author;
    private final short publicationDate;
    private final short countPages;

    public Book(int id, String name, String author, short publicationDate, short countPages) {
        this.id = id;
        this.name = name;
        this.author = author;
        if (publicationDate <= 0) {
            System.out.println("Book have a strange publication date: " + publicationDate);
        }
        this.publicationDate = publicationDate;
        if (countPages <= 0) {
            System.out.println("Book have a strange countPages number: " + countPages);
        }
        this.countPages = countPages;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getAuthor() { return author; }
    public short getPublicationDate() { return publicationDate; }
    public short getCountPages() { return countPages; }

    public static Book stringToBook(String bookData) {
        if (bookData == null) {
            System.out.println("bookData is null.");
            return null;
        }
        try {
            int id = Integer.parseInt(StringsUtils.substringData(bookData, "id=", ",").trim());
            String name = StringsUtils.substringData(bookData, "name='", "',").trim();
            String author = StringsUtils.substringData(bookData, "author='", "',").trim();
            short publicationDate = Short.parseShort(StringsUtils.substringData(bookData, "publicationDate=", ",").trim());
            short countPages = Short.parseShort(StringsUtils.substringData(bookData, "countPages=", "}").trim());
            return new Book(id, name, author, publicationDate, countPages);
        } catch (Exception e) {
            System.out.println("Can't convert string '" + bookData + "' to Book class: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publicationDate=" + publicationDate +
                ", countPages=" + countPages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        if (book.hashCode() != this.hashCode()) return false;
        return id == book.id && publicationDate == book.publicationDate && Objects.equals(name, book.name)
                && Objects.equals(author, book.author) && countPages == book.countPages;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, publicationDate, countPages);
    }
}
