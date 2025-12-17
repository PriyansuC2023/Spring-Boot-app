package com.example.ebookapp.controller;

import com.example.ebookapp.model.Bookmark;
import com.example.ebookapp.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @PostMapping
    public Bookmark addBookmark(@RequestBody Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    @GetMapping("/user/{userId}")
    public List<Bookmark> getUserBookmarks(@PathVariable Long userId) {
        return bookmarkRepository.findByUserId(userId);
    }
}