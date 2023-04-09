package ru.frolov.springcourse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;


@Entity
@Table(name = "book")
@SuppressWarnings("unused")
public class Book {

    private static final int MAX_YEAR = 2023;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotNull
    @Length(max = 200)
    private String title;

    @ManyToOne()
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @Column(name = "year")
    @Range(max = MAX_YEAR)
    private int year;

    @OneToOne(mappedBy = "book")
    private Borrowing borrowing;

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Borrowing getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(Borrowing borrowing) {
        this.borrowing = borrowing;
    }
}
