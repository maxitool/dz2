import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {
    private static final int COUNT_BOOKS = 5;
    private final String name;
    private final String surname;
    private final String patronymic;
    private List<Book> books;

    public Student(String name, String surname, String patronymic) {
        if (name == null) {
            name = "";
        }
        this.name = name;
        if (surname == null) {
            surname = "";
        }
        this.surname = surname;
        if (patronymic == null) {
            patronymic = "";
        }
        this.patronymic = patronymic;
        this.books = List.of();
    }
    public Student(String name, String surname, String patronymic, List<Book> books) {
        this(name, surname, patronymic);
        setBooks(books);
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getPatronymic() { return patronymic; }
    public List<Book> getBooks() { return List.copyOf(books); }

    public void setBooks(List<Book> books) {
        if (books == null || books.isEmpty()) {
            System.out.println("The number of books is 0.");
            this.books = List.of();
            return;
        }
        if (books.size() < COUNT_BOOKS) {
            System.out.println("The number of books is less than " + COUNT_BOOKS + '.');
        }
        this.books = List.copyOf(books);
    }

    public static Student stringToStudent(String studentData) {
        if (studentData == null) {
            System.out.println("studentData is null.");
            return null;
        }
        try {
            String name = StringsUtils.substringData(studentData, "name='", "',").trim();
            String surname = StringsUtils.substringData(studentData, "surname='", "',").trim();
            String patronymic = StringsUtils.substringData(studentData, "patronymic='", "',").trim();
            return new Student(name, surname, patronymic);
        } catch (Exception e) {
            System.out.println("Can't convert string '" + studentData + "' to Student class: " + e.getMessage());
        }
        return null;
    }

    public static List<Student> readStudents(String filePath) {
        if (filePath == null) {
            System.out.println("The path of file is null.");
            return null;
        }
        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath), StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            String[] data; Student student; Book book;
            List<Student> students = new ArrayList<>();
            List<Book> books;
            while (line != null) {
                data = line.split("\\|\\|\\|");
                student = stringToStudent(data[0]);
                if (student == null) {
                    line = reader.readLine();
                    continue;
                }
                books = new ArrayList<>();
                for (int i = 1; i < data.length; i++) {
                    book = Book.stringToBook(data[i]);
                    if (book != null)
                        books.add(book);
                }
                student.setBooks(books);
                students.add(student);
                line = reader.readLine();
            }
            return students;
        } catch (NoSuchFileException e) {
            System.out.println("No such file.");
        } catch (IOException e) {
            System.out.println("Can't read the file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Can't get data from file: " + e.getMessage());
        }
        return null;
    }

    public static boolean writeStudents(String filePath, List<Student> students, boolean doClearFile) {
        if (filePath == null) {
            System.out.println("The path of file is null.");
            return false;
        }
        StandardOpenOption standardOpenOption = StandardOpenOption.APPEND;
        if (doClearFile) {
            standardOpenOption = StandardOpenOption.TRUNCATE_EXISTING;
        }
        StringBuilder stringBuilder = new StringBuilder();
        students.stream().peek(student -> stringBuilder.append("\n").append(student.toString()))
                .flatMap(student -> student.getBooks().stream())
                .forEach(book -> stringBuilder.append(" ||| ").append(book.toString()));
        stringBuilder.deleteCharAt(0); // delete first '\n' char
        try {
            Files.writeString(Path.of(filePath), stringBuilder.toString(), standardOpenOption);
            System.out.println("Students are recorded in the file " + filePath);
            return true;
        } catch (NoSuchFileException e) {
            System.out.println("File is not exist, creating one...");
            File file = new File(filePath);
            try {
                if (file.createNewFile()) {
                    System.out.println("File created.");
                    return writeStudents(filePath, students, false);
                }
                System.out.println("Can't create file.");
            } catch (IOException e2) {
                System.out.println("Can't create file: " + e2.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Can't write students to file: " + e.getMessage());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", countOfBooks=" + books.size() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        if (student.hashCode() != hashCode()) return false;
        return Objects.equals(name, student.name) && Objects.equals(surname, student.surname) && Objects.equals(patronymic, student.patronymic) && Objects.equals(books, student.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, patronymic, books);
    }
}
