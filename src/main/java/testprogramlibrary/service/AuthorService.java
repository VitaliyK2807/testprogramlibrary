package testprogramlibrary.service;

import testprogramlibrary.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> findAll();

    Author findById(Long id);

    Author save(Author author);

    Author update(Author author);

    void deleteById(Long id);

}
