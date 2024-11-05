package testprogramlibrary.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testprogramlibrary.mapper.BookMapper;
import testprogramlibrary.model.Book;
import testprogramlibrary.service.BookService;
import testprogramlibrary.web.model.*;

@RestController
@RequestMapping("api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping
    public ResponseEntity<BookListResponse> findAll() {
        return ResponseEntity.ok(bookMapper.bookListToBookListResponse(bookService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookMapper.requestToResponse(bookService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@RequestBody @Valid UpsertBookRequest request) {
        Book newBook = bookService.save(bookMapper.requestToBook(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookMapper.requestToResponse(newBook));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable("id") Long id,
                                                 @RequestBody UpsertBookRequest request) {
        Book updateBook = bookService.update(bookMapper.requestToBook(id, request));

        return ResponseEntity.ok(bookMapper.requestToResponse(updateBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
