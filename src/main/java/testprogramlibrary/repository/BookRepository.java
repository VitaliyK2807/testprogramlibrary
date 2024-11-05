package testprogramlibrary.repository;

import testprogramlibrary.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Book save(Book book);

    Book update (Book book);

    void deleteBiId(Long id);

    void deleteByIdIn(List<Long> ids);

}
