package com.example.ebookapp.controller;

import com.example.ebookapp.model.Book;
import com.example.ebookapp.service.BookService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequestMapping("/ui/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // ===== LIST =====
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/list";
    }

    // ===== NEW FORM =====
    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    // ===== SAVE =====
    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book,
                           @RequestParam("file") MultipartFile file) throws IOException {
        bookService.save(book, file);
        return "redirect:/ui/books";
    }

//EDIT

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {

        Book book = bookService.getById(id);

        if (book == null) {
            return "redirect:/ui/books"; // fail-safe
        }

        model.addAttribute("book", book);
        return "books/edit";
    }
    //UPDATE


    @PostMapping("/update/{id}")
    public String updateBook(
            @PathVariable Long id,
            @ModelAttribute Book book,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {

        bookService.update(id, book, file);
        return "redirect:/ui/books";
    }

    // ===== DELETE =====
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/ui/books";
    }

    // ===== VIEW PDF =====
    @GetMapping("/file/{id}")
    public void viewPdf(@PathVariable Long id,
                        HttpServletResponse response) throws IOException {

        Book book = bookService.getById(id);
        File file = new File(book.getFilePath());

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "inline; filename=\"" + book.getFileName() + "\"");

        Files.copy(file.toPath(), response.getOutputStream());
        response.getOutputStream().flush();
    }
}