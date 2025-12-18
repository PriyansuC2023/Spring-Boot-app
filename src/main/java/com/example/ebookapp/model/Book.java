package com.example.ebookapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String author;

    private String category;

    @Column(length = 2000)
    private String description;

    // stores uploaded file name or path (NOT MultipartFile)
    @Column(name = "pdf_path")
    private String pdfPath;

    // ----- Constructors -----

    public Book() {
    }

    public Book(String title, String author, String category, String description, String pdfPath) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
        this.pdfPath = pdfPath;
    }

    // ----- Getters & Setters -----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }
}