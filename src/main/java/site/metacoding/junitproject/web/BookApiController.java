package site.metacoding.junitproject.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.junitproject.service.BookService;
import site.metacoding.junitproject.web.dto.request.BookSaveReqDto;
import site.metacoding.junitproject.web.dto.response.BookListRespDto;
import site.metacoding.junitproject.web.dto.response.BookRespDto;
import site.metacoding.junitproject.web.dto.response.CMRespDto;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class BookApiController {

    private final BookService bookService;

    // 1. 책 등록
    @PostMapping("/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto dto, BindingResult bindingResult) {
        validationCheck(bindingResult);
        BookRespDto result = bookService.책등록하기(dto);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 저장 성공!").body(result).build(), HttpStatus.CREATED);
    }

    // 2. 책 목록보기
    @GetMapping("/book")
    public ResponseEntity<?> getBookList(){
        BookListRespDto dto = bookService.책목록보기();

        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 목록보기 성공").body(dto).build(),
                HttpStatus.OK);
    }

    // 3. 책 한건보기
    @GetMapping("/book/{id}")
    public ResponseEntity<?> getBook(@PathVariable Long id){
        BookRespDto dto = bookService.책한건보기(id);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("책 한건보기 성공").body(dto).build(),
                HttpStatus.OK);
    }

    // 4. 책 삭제하기
    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        bookService.책삭제하기(id);

        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("책 삭제하기 성공").body(null).build(),
                HttpStatus.OK);
    }

    // 5. 책 수정하기
    @PutMapping("/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, 
                                        @RequestBody @Valid BookSaveReqDto dto,
                                        BindingResult bindingResult){
        validationCheck(bindingResult);

        BookRespDto updateBook = bookService.책수정하기(id, dto);

        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("책 수정하기 성공").body(updateBook).build(),
                HttpStatus.OK);
    }

    private static void validationCheck(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        }
    }

}
