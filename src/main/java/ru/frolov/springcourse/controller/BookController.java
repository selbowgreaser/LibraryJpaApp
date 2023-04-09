package ru.frolov.springcourse.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.frolov.springcourse.model.Author;
import ru.frolov.springcourse.model.Book;
import ru.frolov.springcourse.model.Person;
import ru.frolov.springcourse.service.AuthorService;
import ru.frolov.springcourse.service.BookService;
import ru.frolov.springcourse.service.BorrowingService;
import ru.frolov.springcourse.service.PersonService;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;
    private final AuthorService authorService;
    private final BorrowingService borrowingService;


    @Autowired
    public BookController(BookService bookService, PersonService personService, AuthorService authorService, BorrowingService borrowingService) {
        this.bookService = bookService;
        this.personService = personService;
        this.authorService = authorService;
        this.borrowingService = borrowingService;
    }

    @GetMapping
    public String getAllBooks(@RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "itemsPerPage", required = false) Integer itemsPerPage,
                              @RequestParam(value = "isSortByYear", required = false, defaultValue = "false") Boolean isSortByYear,
                              Model model) {
        model.addAttribute("books", bookService.findAll(page, itemsPerPage, isSortByYear));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") int id, @ModelAttribute("person") Person person, Model model) {
        model.addAttribute("book", bookService.findOne(id));
        model.addAttribute("people", personService.findAll());
        return "books/book";
    }

    @GetMapping("/new")
    public String getFormForCreateBook(@ModelAttribute("book") Book book, @ModelAttribute("author") Author author, Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "books/new";
    }

    @PostMapping
    public String addNewBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            return "books/new";
        }

        bookService.save(book);

        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.delete(id);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String getFormForUpdateBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findOne(id));
        model.addAttribute("authors", authorService.findAll());

        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        bookService.update(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/borrow")
    public String borrowBook(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        borrowingService.save(id, person.getId());

        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        borrowingService.deleteByBookId(id);

        return "redirect:/books/{id}";
    }

    @GetMapping("/search")
    public String getSearchForm() {
        return "/books/search";
    }

    @GetMapping("/search/r")
    public String getSearch(@RequestParam(value = "searchTitle") String searchTitle, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("searchTitle", searchTitle);
        return "redirect:/books/search/{searchTitle}";
    }

    @GetMapping("/search/{searchTitle}")
    public String getSearchResult(@PathVariable(value = "searchTitle") String searchTitle, Model model) {
        model.addAttribute("foundBooks", bookService.findAllBySearchTitle(searchTitle));
        return "/books/search";
    }
}

