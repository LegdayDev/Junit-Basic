package site.metacoding.junitproject.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.service.BookService;
import site.metacoding.junitproject.web.dto.request.BookSaveReqDto;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.*;

/**
 * <h2> 통합테스트 </h2>
 * <li> Controller+Service+Repository 를 통합하여 테스트</li>
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private TestRestTemplate rt;
    @Autowired
    private BookRepository bookRepository;

    private static ObjectMapper om;
    private static HttpHeaders headers;
    @BeforeAll
    public static void init(){
        om = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
    @BeforeEach
    public void dataInit() {
        String title = "This is Football";
        String author = "Cristiano Ronaldo";
        Book book = Book.builder().title(title).author(author).build();
        bookRepository.save(book);
    }

    @Test
    public void saveBookTest() throws Exception {
        //given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("This is Football");
        dto.setAuthor("Cristiano");

        String body = om.writeValueAsString(dto);

        //when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.POST, request, String.class);

        //then
        DocumentContext documentContext = JsonPath.parse(response.getBody()); //JSON 은 검증하기 힘들기 때문에 DocumentContext 이용
        String title = documentContext.read("$.body.title");
        String author = documentContext.read("$.body.author");

        assertThat(title).isEqualTo(dto.getTitle());
        assertThat(author).isEqualTo(dto.getAuthor());
    }

    @Test
    public void getBookListTest() throws Exception {
        //given

        //when
        HttpEntity<String> request = new HttpEntity<>(null, headers); //GET 은 HTTP Body가 없다.
        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.GET, request, String.class);

        //then
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Integer code = documentContext.read("$.code");
        String title = documentContext.read("$.body.data[0].title");
        String author = documentContext.read("$.body.data[0].author");

        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("This is Football");
        assertThat(author).isEqualTo("Cristiano Ronaldo");

    }
}
