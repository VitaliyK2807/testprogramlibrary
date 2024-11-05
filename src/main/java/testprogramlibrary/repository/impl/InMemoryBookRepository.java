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
public class InMemoryBookRepository implements BookRepository {

    private AuthorRepository authorRepository;
    private final Map<Long, Book> repository = new ConcurrentHashMap<>();
    private final AtomicLong currentId = new AtomicLong(1);


    @Override
    public List<Book> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Book save(Book book) {
        Long bookId = currentId.getAndIncrement();
        Long authorId = book.getAuthor().getId();
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Author with id {0} not found", authorId)));

        book.setAuthor(author);
        book.setId(bookId);

        author.addBook(book);
        authorRepository.update(author);
        repository.put(bookId, book);

        return book;
    }

    @Override
    public Book update(Book book) {
        Long bookId = book.getId();
        Book currentBook = repository.get(bookId);

        if(currentBook == null) {
            throw new EntityNotFoundException(MessageFormat.format("Book with id {0} not found", bookId));
        }

        BeanUtils.copyNonNullProperties(book, currentBook);
        currentBook.setId(bookId);
        repository.put(bookId, currentBook);

        return currentBook;
    }

    @Override
    public void deleteBiId(Long id) {
        repository.remove(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        ids.forEach(repository::remove);
    }

    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
}
