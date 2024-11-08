package testprogramlibrary.repository;

import testprogramlibrary.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    List<Author> findAll();

    Optional<Author> findById(Long id);

    Author save(Author author);

    Author update(Author author);

    void deleteById(Long id);

}
