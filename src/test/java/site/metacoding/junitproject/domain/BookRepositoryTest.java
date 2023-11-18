package site.metacoding.junitproject.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest // DB 와 관련된 컴포넌트만 메모리에 로딩(단위 테스트)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void dataInit() {
        String title = "This is Football";
        String author = "Cristiano Ronaldo";
        Book book = Book.builder().title(title).author(author).build();
        bookRepository.save(book);
    }

    // 1. 책 등록
    @Test
    public void save_test() throws Exception {
        // given
        String title = "Football King";
        String author = "Ronaldo";

        Book book = Book.builder().title(title).author(author).build();
        // when
        Book bookPS = bookRepository.save(book);
        // then
        assertThat(bookPS.getTitle()).isEqualTo(title);
        assertThat(bookPS.getAuthor()).isEqualTo(author);
    }

    // 2. 책 목록보기
    @Test
    public void 책목록보기_test() throws Exception {
        // given
        String title = "This is Football";
        String author = "Cristiano Ronaldo";
        // when
        List<Book> books = bookRepository.findAll();

        // then
        assertThat(books.size()).isEqualTo(1);
        assertThat(books.get(0).getAuthor()).isEqualTo(author);
        assertThat(books.get(0).getTitle()).isEqualTo(title);
    }

    // 3. 책 한건보기
    @Test
    public void 책한건보기_test() throws Exception {
        // given
        String title = "This is Football";
        String author = "Cristiano Ronaldo";

        // when
        Book findBook = bookRepository.findById(1L).get();

        // then
        assertThat(findBook.getAuthor()).isEqualTo(author);
        assertThat(findBook.getTitle()).isEqualTo(title);
    }
    // 4. 책 수정

    // 5. 책 삭제
}
