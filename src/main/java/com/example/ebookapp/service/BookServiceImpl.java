package com.example.ebookapp.service;

import com.example.ebookapp.model.Book;
import com.example.ebookapp.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book save(Book book, String filePath) {
        book.setPdfPath(filePath);
        return bookRepository.save(book);
    }

    @Override
    public Book update(Long id, Book book, String filePath) {
        book.setId(id);
        book.setPdfPath(filePath);
        return bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}