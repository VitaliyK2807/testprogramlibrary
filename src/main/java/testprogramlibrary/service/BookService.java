package testprogramlibrary.service;

import testprogramlibrary.model.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book findById(Long id);

    Book save(Book book);

    Book update(Book book);

    void deleteById(Long id);

    void deleteByIdIn(List<Long> ids);
}
