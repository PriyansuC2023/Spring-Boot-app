package com.example.ebookapp.service;

import com.example.ebookapp.model.Book;
import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getById(Long id);

    Book save(Book book, String filePath);

    Book update(Long id, Book book, String filePath);

    void delete(Long id);
}