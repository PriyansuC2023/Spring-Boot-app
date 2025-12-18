package com.example.ebookapp.controller;

import com.example.ebookapp.model.Book;
import com.example.ebookapp.service.BookService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /* =========================
       LIST PAGE
       URL: /books
       ========================= */
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/list";
    }

    /* =========================
       NEW BOOK FORM
       URL: /books/new
       ========================= */
    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    /* =========================
       SAVE BOOK
       URL: /books/save
       ========================= */
    @PostMapping("/save")
    public String saveBook(
            @ModelAttribute Book book,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        String uploadDir = "uploads/";
        java.io.File dir = new java.io.File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        java.io.File dest = new java.io.File(uploadDir + fileName);
        file.transferTo(dest);

        bookService.save(book, fileName);
        return "redirect:/books";
    }

    /* =========================
       EDIT BOOK FORM
       URL: /books/edit/{id}
       ========================= */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        return "books/edit";
    }

    /* =========================
       UPDATE BOOK
       URL: /books/update/{id}
       ========================= */
    @PostMapping("/update/{id}")
    public String updateBook(
            @PathVariable Long id,
            @ModelAttribute Book book,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {

        String fileName = null;

        if (file != null && !file.isEmpty()) {
            String uploadDir = "uploads/";
            java.io.File dir = new java.io.File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            java.io.File dest = new java.io.File(uploadDir + fileName);
            file.transferTo(dest);
        }

        bookService.update(id, book, fileName);
        return "redirect:/books";
    }

    /* =========================
       DELETE BOOK
       URL: /books/delete/{id}
       ========================= */
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    /* =========================
       DOWNLOAD / VIEW PDF
       URL: /books/file/{id}
       ========================= */
    @GetMapping("/file/{id}")
    @ResponseBody
    public Resource viewFile(@PathVariable Long id) throws MalformedURLException {
        Book book = bookService.getById(id);
        Path path = Paths.get("uploads").resolve(book.getFileName());
        return new UrlResource(path.toUri());
    }
}