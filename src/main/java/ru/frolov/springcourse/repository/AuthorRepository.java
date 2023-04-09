package ru.frolov.springcourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.frolov.springcourse.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
