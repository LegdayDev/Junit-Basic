package site.metacoding.junitproject.web.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class BookListRespDto {
    List<BookRespDto> data;

    @Builder
    public BookListRespDto(List<BookRespDto> bookList) {
        this.data = bookList;
    }
}
