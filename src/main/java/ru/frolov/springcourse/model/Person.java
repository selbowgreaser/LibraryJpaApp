package ru.frolov.springcourse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "person")
@SuppressWarnings("unused")
public class Person {

    private static final String NAME_PATTERN = "[А-Я][а-я]+";
    private static final String PATRONYMIC_PATTERN = "^(|[А-Я][а-я]+)$";
    private static final String NAME_ERROR_MESSAGE = "Введите с большой буквы";
    private static final String BIRTHDAY_ERROR_MESSAGE = "Дата рождения не должна быть в будущем";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "surname")
    @Pattern(regexp = NAME_PATTERN, message = NAME_ERROR_MESSAGE)
    @NotNull
    @Length(max = 50)
    private String surname;

    @Column(name = "name")
    @Pattern(regexp = NAME_PATTERN, message = NAME_ERROR_MESSAGE)
    @NotNull
    @Length(max = 50)
    private String name;

    @Column(name = "patronymic")
    @Pattern(regexp = PATRONYMIC_PATTERN, message = NAME_ERROR_MESSAGE)
    @Length(max = 50)
    private String patronymic;

    @Column(name = "birthday")
    @Past(message = BIRTHDAY_ERROR_MESSAGE)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @NotNull
    private LocalDate birthday;

    @OneToMany(mappedBy = "person")
    private List<Borrowing> borrowings;

    public Person() {
    }

    public String getFullName() {
        if (patronymic == null) {
            return surname + " " + name;
        }
        return surname + " " + name + " " + patronymic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<Borrowing> getBorrowings() {
        return borrowings;
    }

    public void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }
}
