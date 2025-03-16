package com.example.demo.service;

import com.example.demo.entities.Author;
import com.example.demo.request.AuthorUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {
    Author getAuthorById(Long id);
    List<Author> getAllAuthors();
    Author saveAuthor(Author author);
    void deleteAuthor(Long id);
    Author updateAuthor(Long id, AuthorUpdateRequest author);
    List<Author> getAll();
}
