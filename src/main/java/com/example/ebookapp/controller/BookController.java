package com.example.ebookapp.controller;

import com.example.ebookapp.model.Book;
import com.example.ebookapp.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/save")
    public String saveBook(
            @ModelAttribute Book book,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        String uploadDir = "uploads/";
        new File(uploadDir).mkdirs();

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir + fileName);
        file.transferTo(dest);

        bookService.save(book, fileName);

        return "redirect:/books";
    }
}