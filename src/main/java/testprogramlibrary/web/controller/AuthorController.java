package testprogramlibrary.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testprogramlibrary.exception.EntityNotFoundException;
import testprogramlibrary.mapper.AuthorMapper;
import testprogramlibrary.model.Author;
import testprogramlibrary.service.AuthorService;
import testprogramlibrary.web.model.AuthorListResponse;
import testprogramlibrary.web.model.AuthorResponse;
import testprogramlibrary.web.model.UpsertAuthorRequest;

@RestController
@RequestMapping("api/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @GetMapping
    public ResponseEntity<AuthorListResponse> findAll() {
        return ResponseEntity.ok(authorMapper.authorListToAuthorResponseList(authorService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(authorMapper.authorToResponse(authorService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> create(@RequestBody @Valid UpsertAuthorRequest request) {
        Author newAuthor = authorService.save(authorMapper.requestToAuthor(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authorMapper.authorToResponse(newAuthor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> update(@PathVariable("id") Long id,
                                                 @RequestBody UpsertAuthorRequest request) {
        Author updateAuthor = authorService.update(authorMapper.requestToAuthor(id, request));

        return ResponseEntity.ok(authorMapper.authorToResponse(updateAuthor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> notFoundHandler(EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

}
