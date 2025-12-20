package com.example.ebookapp.controller;

import com.example.ebookapp.model.Book;
import com.example.ebookapp.service.BookService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // =========================
    // LIST BOOKS
    // =========================
    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books/list";
    }

    // =========================
    // SHOW ADD BOOK FORM
    // =========================
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    // =========================
    // SAVE BOOK WITH PDF
    // =========================
    @PostMapping("/save")
    public String saveBook(
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        book.setOriginalFileName(file.getOriginalFilename());
        book.setPdfData(file.getBytes());

        bookService.saveBook(book);

        return "redirect:/books";
    }

    // =========================
    // VIEW / DOWNLOAD PDF
    // =========================
    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> viewPdf(@PathVariable Long id) {

        Book book = bookService.getBookById(id);

        if (book == null || book.getPdfData() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + book.getOriginalFileName() + "\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(book.getPdfData());
    }

    // =========================
    // DELETE BOOK (OPTIONAL)
    // =========================
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}