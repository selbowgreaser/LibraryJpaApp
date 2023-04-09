package ru.frolov.springcourse.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.frolov.springcourse.model.Book;
import ru.frolov.springcourse.repository.BookRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(Integer page, Integer itemsPerPage, Boolean isSortByYear) {

        if (isPagination(page, itemsPerPage)) {
            return getBooksWithoutPagination(isSortByYear);
        }

        return getBooksWithPagination(
                getPageOrDefault(page),
                getItemsPerPageOrDefault(itemsPerPage),
                isSortByYear);
    }

    public Book findOne(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> findAllBySearchTitle(String searchTitle) {
        return bookRepository.findBooksByTitleStartsWithIgnoreCase(searchTitle);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(Book updatedBook) {
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    private List<Book> getBooksWithPagination(Integer page, Integer itemsPerPage, Boolean isSortByYear) {
        if (isSortByYear) {
            return bookRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("year"))).getContent();
        } else {
            return bookRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
        }
    }

    private List<Book> getBooksWithoutPagination(Boolean isSortByYear) {
        if (isSortByYear) {
            return bookRepository.findAll(Sort.by("year"));
        } else {
            return bookRepository.findAll();
        }
    }

    private static int getItemsPerPageOrDefault(Integer itemsPerPage) {
        return itemsPerPage == null ? 5 : itemsPerPage;
    }

    private static int getPageOrDefault(Integer page) {
        return page == null ? 0 : page;
    }

    private static boolean isPagination(Integer page, Integer itemsPerPage) {
        return page == null && itemsPerPage == null;
    }
}
