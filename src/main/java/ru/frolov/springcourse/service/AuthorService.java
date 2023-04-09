package ru.frolov.springcourse.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.frolov.springcourse.model.Author;
import ru.frolov.springcourse.model.Book;
import ru.frolov.springcourse.repository.AuthorRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author findOne(int id) {
        return authorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Автор с id = " + id + " не найден!"));
    }

    public List<Book> findBooksById(int id) {
        return findOne(id).getBooks();
    }

    @Transactional
    public void save(Author author) {
        authorRepository.save(author);
    }

    @Transactional
    public void update(Author updatedAuthor) {
        authorRepository.save(updatedAuthor);
    }

    @Transactional
    public void delete(int id) {
        authorRepository.deleteById(id);
    }
}
