package testprogramlibrary.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import testprogramlibrary.exception.EntityNotFoundException;
import testprogramlibrary.model.Author;
import testprogramlibrary.model.Book;
import testprogramlibrary.repository.AuthorRepository;
import testprogramlibrary.repository.BookRepository;
import testprogramlibrary.utils.BeanUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryAuthorRepository implements AuthorRepository {

    private BookRepository bookRepository;

    private final Map<Long, Author> repository = new ConcurrentHashMap<>();

    private final AtomicLong currentId = new AtomicLong(1);

    @Override
    public List<Author> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Author save(Author author) {
        Long authorId = currentId.getAndIncrement();
        author.setId(authorId);

        repository.put(authorId, author);

        return author;
    }

    @Override
    public Author update(Author author) {
        Long authorId = author.getId();
        Author currentAuthor = repository.get(authorId);

        if(currentAuthor == null) {
            throw new EntityNotFoundException(MessageFormat.format("Author with id {0} not found", authorId));
        }

        BeanUtils.copyNonNullProperties(author, currentAuthor);
        currentAuthor.setId(authorId);
        repository.put(authorId, currentAuthor);

        return currentAuthor;
    }

    @Override
    public void deleteById(Long id) {
        Author author = repository.get(id);

        if(author == null) {
            throw new EntityNotFoundException(MessageFormat.format("Author with id {0} not found", id));
        }

        bookRepository.deleteByIdIn(author.getBooks().stream().map(Book::getId).toList());

        repository.remove(id);
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
