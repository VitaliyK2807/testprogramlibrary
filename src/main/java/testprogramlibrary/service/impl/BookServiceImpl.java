package testprogramlibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import testprogramlibrary.exception.EntityNotFoundException;
import testprogramlibrary.model.Book;
import testprogramlibrary.repository.BookRepository;
import testprogramlibrary.service.BookService;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Book with id {0} not found", id)));
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book update(Book book) {
        return bookRepository.update(book);
    }

    @Override
    public void deleteById(Long id) {
        Book currentBook = findById(id);
        currentBook.getAuthor().removeBook(id);

        bookRepository.deleteBiId(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        bookRepository.deleteByIdIn(ids);
    }
}
