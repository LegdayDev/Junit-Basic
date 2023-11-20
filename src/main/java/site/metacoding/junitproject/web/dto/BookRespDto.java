package site.metacoding.junitproject.web.dto;

import lombok.Data;
import site.metacoding.junitproject.domain.Book;

@Data
public class BookRespDto {
    private Long id;
    private String title;
    private String author;

    public BookRespDto toDto(Book bookPS) {
        this.id = bookPS.getId();
        this.title = bookPS.getTitle();
        this.author = bookPS.getAuthor();

        return this;
    }
}
