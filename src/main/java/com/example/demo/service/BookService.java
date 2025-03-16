package com.example.demo.service;

import com.example.demo.entities.Book;
import com.example.demo.request.BookUpdateRequest;

import java.util.List;

public interface BookService {
    Book addBook(Book book, Long authorId);
    List<Book> getBooks();
    Book getBookById(Long id);
    void deleteBookById(Long id);
    Book updateBook(Long id, BookUpdateRequest bookUpdateRequest);
}
