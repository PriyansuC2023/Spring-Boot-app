package com.example.ebookapp.service;

import com.example.ebookapp.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getById(Long id);

    void save(Book book, String storedFileName);

    void update(Long id, Book book, String storedFileName);

    void delete(Long id);
}