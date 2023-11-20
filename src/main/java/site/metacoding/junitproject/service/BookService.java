package site.metacoding.junitproject.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.web.dto.BookSaveReqDto;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // 1. 책 등록
    public Book 책등록하기(BookSaveReqDto dto) {
        // DTO -> Entity -> DTO ?
        Book bookPS = bookRepository.save(dto.toEntity());

        return bookPS;
    }
    // 2. 책 목록보기

    // 3. 책 한건보기

    // 4. 책 삭제하기

    // 5. 책 수정하기

}
