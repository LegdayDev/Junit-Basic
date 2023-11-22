package site.metacoding.junitproject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import site.metacoding.junitproject.domain.Book;
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

    @Test
    public void 책목록보기_test() throws Exception {
        // given

        // stub
        List<Book> books = Arrays.asList(
                Book.builder().id(1L).title("Junit").author("메타코딩").build(),
                Book.builder().id(2L).title("Spring").author("데어프로그래밍").build());

        when(bookRepository.findAll()).thenReturn(books);

        // when
        List<BookRespDto> bookRespDtoList = bookService.책목록보기();

        // then
        assertThat(bookRespDtoList.size()).isEqualTo(2);
        assertThat(bookRespDtoList.get(0).getTitle()).isEqualTo(books.get(0).getTitle());
        assertThat(bookRespDtoList.get(0).getAuthor()).isEqualTo(books.get(0).getAuthor());
        assertThat(bookRespDtoList.get(1).getTitle()).isEqualTo(books.get(1).getTitle());
        assertThat(bookRespDtoList.get(1).getAuthor()).isEqualTo(books.get(1).getAuthor());
    }

    @Test
    public void 책한건보기_test() throws Exception {
        // given
        Long id = 1L;

        // stub
        Book book = new Book(1L, "Junit", "MetaCoding");
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        // when
        BookRespDto dto = bookService.책한건보기(id);

        // then
        assertThat(dto.getTitle()).isEqualTo(book.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    public void 책수정하기_test() throws Exception {
        // given
        Long id = 1L;
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("Spring");
        dto.setAuthor("DearProgramming");

        // stub
        Book book = new Book(1L, "Junit", "MetaCoding");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // when
        BookRespDto resultDto = bookService.책수정하기(id, dto);

        // then
        assertThat(resultDto.getId()).isEqualTo(id);
        assertThat(resultDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(resultDto.getAuthor()).isEqualTo(dto.getAuthor());
    }
}
