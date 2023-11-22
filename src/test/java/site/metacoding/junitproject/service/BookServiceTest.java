package site.metacoding.junitproject.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSenderStub;
import site.metacoding.junitproject.web.dto.BookRespDto;
import site.metacoding.junitproject.web.dto.BookSaveReqDto;

@DataJpaTest
public class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void 책등록하기_test() throws Exception {
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("Football");
        dto.setAuthor("Cristiano");
        // stub
        MailSenderStub mailSenderStub = new MailSenderStub();

        // when
        BookService bookService = new BookService(bookRepository, mailSenderStub);
        BookRespDto bookRespDto = bookService.책등록하기(dto);

        // then
        assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());

    }
}
