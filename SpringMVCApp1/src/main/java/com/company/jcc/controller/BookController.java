package com.company.jcc.controller;

import com.company.jcc.model.Book;
//import com.company.jcc.model.User;
import com.company.jcc.service.BookService;
import com.company.jcc.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;


    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("book", new Book());
        return "create_book";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("book") Book book) {
        bookService.create(book);
        return "redirect:/books/all";
    }

    @GetMapping("/{id}/read")
    public String read(@PathVariable int id, Model model) {
        model.addAttribute("book", bookService.readById(id));
        return "book_info";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("books", bookService.getAll());
        return "book_list";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books/all";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable("id") int id, Model model) {
        Book book = bookService.readById(id);
        model.addAttribute("book", book);
        System.out.println(book + "get method");
        return "update_book";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") int id, @RequestParam String bookTitle,
                         @RequestParam int amountLeft,
                         @RequestParam int amountGave,
                         @RequestParam int rating) {
        Book book = bookService.readById(id);
        System.out.println(book);
        book.setBookTitle(bookTitle);
        book.setAmountLeft(amountLeft);
        book.setAmountGave(amountGave);
        book.setRating(rating);
        if (book != null)
            bookService.update(book);
        return "redirect:/books/" + id + "/read";
    }


    public BookController() {
    }

    public BookService getBookService() {
        return bookService;
    }

    public void setBookService(BookServiceImpl bookService) {
        this.bookService = bookService;
    }
}