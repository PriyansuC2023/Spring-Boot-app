package com.example.ebookapp.service;

import com.example.ebookapp.model.Book;
import com.example.ebookapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // ================= GET ALL =================
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // ================= GET BY ID =================
    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElse(null);
    }

    // ================= SAVE WITH FILE =================
    @Override
    public Book save(Book book, MultipartFile file) throws IOException {

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath);

        book.setFileName(originalFileName);
        book.setFilePath(filePath.toString());

        return bookRepository.save(book);
    }

    // ================= UPDATE =================
    @Override
    public Book update(Long id, Book updatedBook,MultipartFile file) throws IOException {

        Book existing = getById(id);

        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());
        existing.setDescription(updatedBook.getDescription());

        // Only replace PDF if a new one is uploaded
        if (file != null && !file.isEmpty()) {

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File destination = new File(dir, fileName);

            file.transferTo(destination);

            existing.setFileName(fileName);
            existing.setFilePath(destination.getAbsolutePath());
        }


        return bookRepository.save(existing);
    }

    // ================= DELETE =================
    @Override
    public void delete(Long id) {

        Book book = getById(id);

        if (book.getFilePath() != null) {
            File file = new File(book.getFilePath());
            if (file.exists()) {
                file.delete();
            }
        }

        bookRepository.delete(book);
    }
}