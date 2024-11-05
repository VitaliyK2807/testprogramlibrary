package testprogramlibrary.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import testprogramlibrary.model.Author;
import testprogramlibrary.web.model.AuthorListResponse;
import testprogramlibrary.web.model.AuthorResponse;
import testprogramlibrary.web.model.UpsertAuthorRequest;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorMapper {

    private final BookMapper bookMapper;

    public Author requestToAuthor(UpsertAuthorRequest request) {
        Author author = new Author();

        author.setName(request.getName());

        return author;
    }

    public Author requestToAuthor(Long id, UpsertAuthorRequest request) {
        Author author = requestToAuthor(request);
        author.setId(id);

        return author;
    }

    public AuthorResponse authorToResponse(Author author) {
        AuthorResponse response = new AuthorResponse();

        response.setId(author.getId());
        response.setName(author.getName());
        response.setBooks(bookMapper.bookListToResponseList(author.getBooks()));

        return response;
    }

    public AuthorListResponse authorListToAuthorResponseList(List<Author> authors) {
        AuthorListResponse response = new AuthorListResponse();

        response.setAuthors(authors.stream().map(this::authorToResponse).toList());

        return response;
    }
}
