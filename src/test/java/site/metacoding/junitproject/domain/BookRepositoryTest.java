package site.metacoding.junitproject.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest // DB 와 관련된 컴포넌트만 메모리에 로딩(단위 테스트)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    // 1. 책 등록
    @Test
    public void save_test() throws Exception {
        // given
        String title = "Football";
        String author = "Cristiano";

        Book book = Book.builder().title(title).author(author).build();
        // when
        Book bookPS = bookRepository.save(book);
        // then
        assertThat(bookPS.getTitle()).isEqualTo(title);
        assertThat(bookPS.getAuthor()).isEqualTo(author);
    }
    // 2. 책 목록보기

    // 3. 책 한건보기

    // 4. 책 수정

    // 5. 책 삭제
}
