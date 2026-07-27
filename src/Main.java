import java.util.Comparator;
import java.util.List;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        List<Student> students = Student.readStudents(FILE_NAME);
        if (students == null) return;
        students.stream()
                .peek(student -> System.out.println("Peek student - " + student.toString()))
                .flatMap(student -> student.getBooks().stream())
                .sorted(Comparator.comparingInt(Book::getCountPages))
                .distinct()
                .filter(book -> book.getPublicationDate() > 2000)
                .limit(3)
                .map(Book::getPublicationDate)
                .findFirst()
                .ifPresentOrElse(
                        year -> System.out.println("Найден год выпуска: " + year),
                        () -> System.out.println("Такая книга отсутствует")
                );
    }
}