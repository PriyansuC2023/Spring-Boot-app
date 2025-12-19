package com.example.ebookapp.service;

import com.example.ebookapp.model.Book;

import java.util.List;

public interface BookService {

    // Save book with PDF
    void saveBook(Book book);

    // Get all books
    List<Book> getAllBooks();

    // Get single book by ID (for PDF view)
    Book getBookById(Long id);

    // Delete book
    void deleteBook(Long id);
}