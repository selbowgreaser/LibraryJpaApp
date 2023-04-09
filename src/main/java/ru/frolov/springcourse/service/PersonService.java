package ru.frolov.springcourse.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.frolov.springcourse.model.Borrowing;
import ru.frolov.springcourse.model.Person;
import ru.frolov.springcourse.repository.PersonRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;
    private final BorrowingService borrowingService;

    @Autowired
    public PersonService(PersonRepository personRepository, BorrowingService borrowingService) {
        this.personRepository = personRepository;
        this.borrowingService = borrowingService;
    }

    public List<Person> findAll() {
        return personRepository.findAll(Sort.by("surname"));
    }

    public Person findOne(int id) {
        return personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователь с id = " + id + " не найден!"));
    }

    public List<Borrowing> findBorrowingsById(int id) {
        List<Borrowing> borrowings = borrowingService.findAllByPersonId(id);

        fillOverdue(borrowings);

        return borrowings;
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void update(Person updatedPerson) {
        personRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    private static void fillOverdue(List<Borrowing> borrowings) {
        for (Borrowing borrowing : borrowings) {
            boolean isOverdue = borrowing.getEndDate().isBefore(LocalDateTime.now());
            borrowing.setOverdue(isOverdue);
        }
    }
}
