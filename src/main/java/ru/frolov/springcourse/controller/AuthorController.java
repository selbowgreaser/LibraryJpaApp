package ru.frolov.springcourse.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.frolov.springcourse.model.Author;
import ru.frolov.springcourse.service.AuthorService;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String getAllAuthors(Model model) {
        model.addAttribute("authors", authorService.findAll());

        return "authors/index";
    }

    @GetMapping("/new")
    public String getFormForAddAuthor(@ModelAttribute("author") Author author) {
        return "authors/new";
    }

    @PostMapping
    public String addNewAuthor(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "authors/new";
        }

        authorService.save(author);

        return "redirect:/authors";
    }

    @GetMapping("/{id}")
    public String getAuthor(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorService.findOne(id));
        model.addAttribute("authorBooks", authorService.findBooksById(id));
        return "authors/author";
    }

    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable("id") int id) {
        authorService.delete(id);

        return "redirect:/authors";
    }

    @GetMapping("/{id}/edit")
    public String getFormForEditAuthor(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorService.findOne(id));
        return "authors/edit";
    }

    @PatchMapping("/{id}")
    public String updateAuthor(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "authors/edit";
        }

        authorService.update(author);
        return "redirect:/authors";
    }
}
