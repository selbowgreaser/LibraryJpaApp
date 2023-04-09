package ru.frolov.springcourse.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.frolov.springcourse.model.Book;
import ru.frolov.springcourse.model.Borrowing;
import ru.frolov.springcourse.model.Person;
import ru.frolov.springcourse.repository.BorrowingRepository;
import ru.frolov.springcourse.repository.PersonRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final BookService bookService;
    private final PersonRepository personRepository;

    public BorrowingService(BorrowingRepository borrowingRepository,
                            BookService bookService,
                            PersonRepository personRepository) {
        this.borrowingRepository = borrowingRepository;
        this.bookService = bookService;
        this.personRepository = personRepository;
    }

    public List<Borrowing> findAllByPersonId(int personId) {
        return borrowingRepository.findAllByPersonId(personId);
    }

    @Transactional
    public void deleteByBookId(int bookId) {
        borrowingRepository.deleteByBookId(bookId);
    }

    @Transactional
    public void save(int bookId, int personId) {

        Borrowing borrowing = createNewBorrowing(bookService.findOne(bookId),
                personRepository.findById(personId).orElse(null));

        borrowingRepository.save(borrowing);
    }

    private static Borrowing createNewBorrowing(Book book, Person person) {
        Borrowing borrowing = new Borrowing();
        borrowing.setBook(book);
        borrowing.setPerson(person);
        borrowing.setStartDate(LocalDateTime.now());
        borrowing.setEndDate(borrowing.getStartDate().plusMinutes(3));
        return borrowing;
    }
}
