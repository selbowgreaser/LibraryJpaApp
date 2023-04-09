package ru.frolov.springcourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.frolov.springcourse.model.Borrowing;

import java.util.List;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Integer> {

    List<Borrowing> findAllByPersonId(int personId);

    void deleteByBookId(int bookId);
}
