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

    // =========================
    // GET ALL BOOKS
    // =========================
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // =========================
    // GET BOOK BY ID
    // =========================
    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    // =========================
    // SAVE NEW BOOK
    // =========================
    @Override
    public void save(Book book, String storedFileName) {

        book.setFileName(storedFileName);
        book.setOriginalFileName(storedFileName.substring(
                storedFileName.indexOf("_") + 1
        ));

        bookRepository.save(book);
    }

    // =========================
    // UPDATE BOOK
    // =========================
    @Override
    public void update(Long id, Book updatedBook, String storedFileName) {

        Book existing = getById(id);

        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());
        existing.setDescription(updatedBook.getDescription());

        if (storedFileName != null) {
            existing.setFileName(storedFileName);
            existing.setOriginalFileName(storedFileName.substring(
                    storedFileName.indexOf("_") + 1
            ));
        }

        bookRepository.save(existing);
    }

    // =========================
    // DELETE BOOK
    // =========================
    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}