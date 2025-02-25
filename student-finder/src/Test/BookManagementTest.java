package Test;

import java.util.*;
import java.util.function.Predicate;

// 1. 제너릭 Repository 인터페이스
interface Repository<T, ID> {
    void save(T entity);
    T findById(ID id);
    List<T> findAll();
    void update(T entity);
    void delete(T entity);
}

// 2. 도메인 엔티티: Book 클래스
class Book {
    private String isbn;
    private String title;
    private String author;
    private double price;

    // 생성자
    public Book(String isbn, String title, String author, double price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    // Getter & Setter
    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{isbn='" + isbn + "', title='" + title + "', author='" + author + "', price=" + price + "}";
    }
}

// 3. MemoryBookRepository: Repository<Book, String> 구현체
class MemoryBookRepository implements Repository<Book, String> {
    private Map<String, Book> storage = new HashMap<>();

    @Override
    public void save(Book book) {
        storage.put(book.getIsbn(), book);
        System.out.println("Saved: " + book);
    }

    @Override
    public Book findById(String isbn) {
        return storage.get(isbn);
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(Book book) {
        if(book.getIsbn()==null || !storage.containsKey(book.getIsbn())){
            System.out.println("Book not found");
        }else{
            storage.replace(book.getIsbn(),book);
        }
        // TODO: storage에 해당 isbn의 도서가 존재하면 업데이트하고,
        // 존재하지 않으면 "Book not found" 메시지를 출력하세요.
    }

    @Override
    public void delete(Book book) {
        if(book.getIsbn()==null || !storage.containsKey(book.getIsbn())){
            System.out.println("삭제 실패");
        }else{
            storage.remove(book.getIsbn());
            System.out.println("삭제 성공");
        }
        // TODO: storage에서 해당 isbn의 도서를 삭제하고,
        // 삭제 성공 여부에 따라 메시지를 출력하세요.
    }
}

// 4. 함수형 인터페이스: 도서 필터링을 위한 BookFilter
@FunctionalInterface
interface BookFilter {
    boolean filter(Book book);
}

// BookService: BookFilter를 활용하여 도서 목록을 필터링하는 메서드 제공
class BookService {
    public static List<Book> filterBooks(List<Book> books, BookFilter filter) {
        List<Book> filtered = new ArrayList<>();
        for (Book book : books) {
            if (filter.filter(book)) {
                filtered.add(book);
            }
        }
        return filtered;
    }
}

// 5. 테스트: main 메서드에서 CRUD 및 필터링 기능 확인
public class BookManagementTest {
    public static void main(String[] args) {
        // MemoryBookRepository 객체 생성
        Repository<Book, String> repo = new MemoryBookRepository();

        // 도서 객체 생성 및 저장
        Book b1 = new Book("ISBN001", "Java Programming", "Author A", 25.0);
        Book b2 = new Book("ISBN002", "Python Programming", "Author B", 18.5);
        Book b3 = new Book("ISBN003", "Effective Java", "Author C", 30.0);

        repo.save(b1);
        repo.save(b2);
        repo.save(b3);

        // 전체 도서 목록 출력
        System.out.println("All Books: " + repo.findAll());

        // TODO: update와 delete 메서드를 테스트해 보세요.
        // 예: b2의 가격을 변경하고 update한 후, 삭제 기능도 확인

        // 4. 람다 표현식을 사용하여 가격이 20 이상인 도서 필터링
        List<Book> expensiveBooks = BookService.filterBooks(repo.findAll(), book -> book.getPrice() >= 20.0);
        System.out.println("Expensive Books: " + expensiveBooks);
    }
}
