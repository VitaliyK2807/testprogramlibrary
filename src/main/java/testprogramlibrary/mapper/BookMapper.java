package testprogramlibrary.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import testprogramlibrary.model.Book;
import testprogramlibrary.service.AuthorService;
import testprogramlibrary.web.model.BookListResponse;
import testprogramlibrary.web.model.BookResponse;
import testprogramlibrary.web.model.UpsertBookRequest;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorService authorService;

    public Book requestToBook(UpsertBookRequest request) {
        Book book = new Book();

        book.setTitle(request.getTitle());
        book.setAuthor(authorService.findById(request.getAuthorId()));
        book.setPublishedDate(request.getPublishedDate());

        return book;
    }

    public Book requestToBook(Long bookId, UpsertBookRequest request) {
        Book book = requestToBook(request);
        book.setId(bookId);

        return book;
    }

    public BookResponse requestToResponse(Book book) {
        BookResponse response = new BookResponse();

        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setPublishedDate(book.getPublishedDate());

        return response;
    }

    public List<BookResponse> bookListToResponseList(List<Book> books) {
        return books.stream()
                .map(this::requestToResponse)
                .toList();
    }

    public BookListResponse bookListToBookListResponse(List<Book> books) {
        BookListResponse response = new BookListResponse();
        response.setBooks(bookListToResponseList(books));

        return response;
    }
}
