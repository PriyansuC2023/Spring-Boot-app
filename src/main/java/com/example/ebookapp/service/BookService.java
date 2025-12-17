package com.example.ebookapp.service;

import com.example.ebookapp.model.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getById(Long id);

    Book save(Book book, MultipartFile file) throws IOException;

    Book update(Long id, Book book, MultipartFile file) throws IOException;

    void delete(Long id);
}