package site.metacoding.junitproject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.web.dto.BookRespDto;
import site.metacoding.junitproject.web.dto.BookSaveReqDto;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    // BookService 객체가 heap 메모리에 뜨고 BookService 가 의존하는 BookRepository , MailSender를
    // BookService 에 주입 시켜준다.
    @InjectMocks
    private BookService bookService;

    @Mock // 가짜 환경에 BookRepository , MailSender 를 로딩함.
    private BookRepository bookRepository;
    @Mock
    private MailSender mailSender;

    @Test
    public void 책등록하기_test() throws Exception {
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("Football");
        dto.setAuthor("Cristiano");

        // stub(가설)
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);

        // when
        BookRespDto bookRespDto = bookService.책등록하기(dto);

        // then
        assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());

    }
}
