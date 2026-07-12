import java.util.Comparator;
import java.util.List;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String FILE_NAME = "students.txt";
    /*private static final List<Book> books = List.of(
            new Book(0, "JAVA EE паттерны проектирования для профессионалов", "Йенер М. Фидом А.", (short)1998, (short)216),
            new Book(1, "Паттерны проектирования", "Эрик Фримен, Элизабет Фримен", (short)2018, (short)114),
            new Book(2, "Чистый код. Создание, анализ и рефакторинг", "Р. Мартин", (short)2025, (short)512),
            new Book(3, "Совершенный код", "Стив МакКоннелл", (short)1994, (short)622),
            new Book(4, "Грокаем алгоритмы. Иллюстрированное пособие для программистов и любопытствующих", "Бхаргава А.", (short)2017, (short)766),
            new Book(5, "Структура данных и алгоритмы Java", "Роберт Лафоре", (short)2013, (short)555),
            new Book(6, "Oracle Certified Associate Java SE 8 Programmer I Study Guide", "Jeanne Boyarsky", (short)2015, (short)333),
            new Book(7, "OCP: Oracle Certified Professional Java SE 8 Programmer II Study Guide", "Jeanne Boyarsky Scott Selikoff", (short)2015, (short)766),
            new Book(8, "Лямбда-выражения в Java 8", "Уобэртон Р.", (short)2014, (short)334),
            new Book(9, "Java 8. Руководство для начинающих»(6-е издание)", "Герберт Шилдт", (short)2015, (short)888),
            new Book(10, "Java 8. Полное руководство 9-е издание", "Герберт Шилдт", (short)2015, (short)594));
    private static final List<Student> students = List.of(
            new Student("Егор", "Кучумов", "Александрович", List.of(books.get(0), books.get(1), books.get(2), books.get(3), books.get(4))),
            new Student("Максим", "Парфенов", "Артёмович", List.of(books.get(3), books.get(4), books.get(5), books.get(6), books.get(7))),
            new Student("Ярослав", "Кравчук", "Александрович", List.of(books.get(5), books.get(6), books.get(7), books.get(8), books.get(9))),
            new Student("Никита", "Мухин", "Иванович", List.of(books.get(6), books.get(7), books.get(8), books.get(9), books.get(10))),
            new Student("Варвара", "Горелова", "Глебовна", List.of(books.get(10), books.get(1), books.get(4), books.get(7), books.get(2))));
*/

    public static void main(String[] args) {
        //Student.writeStudents(FILE_NAME, students, true);
        List<Student> students = Student.readStudents(FILE_NAME);
        if (students == null)
            return;

        students.stream()
                .peek(student -> System.out.println("Peek student - " + student.toString()))
                .flatMap(student -> student.getBooks().stream())
                .peek(book -> System.out.println("Peek book - " + book.toString()))
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