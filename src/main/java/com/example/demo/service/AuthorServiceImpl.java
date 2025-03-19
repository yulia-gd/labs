package com.example.demo.service;

import com.example.demo.entities.Author;
import com.example.demo.entities.Book;
import com.example.demo.entities.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.AuthorUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
    }

    @Override
    public List<Author> getAllAuthors() {
        return  authorRepository.findAll();
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id) {
        Author author = getAuthorById(id);
        for (Book book : author.getBooks()) {
            book.setAuthor(null);
            for(User user : book.getUsers()) {
                user.getBorrowedBooks().remove(book);
                userRepository.save(user);
            }
            bookRepository.delete(book);
        }
        authorRepository.delete(author);
    }


    @Override
    public Author updateAuthor(Long id, AuthorUpdateRequest authorUpdateRequest) {
        Author authorToUpdate = getAuthorById(id);
        updateExistingAuthor(authorToUpdate, authorUpdateRequest);
        return authorRepository.save(authorToUpdate);
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    private void updateExistingAuthor(Author authorToUpdate, AuthorUpdateRequest authorUpdateRequest) {
        if (authorUpdateRequest.getName() != null && !authorUpdateRequest.getName().isEmpty()) {
            authorToUpdate.setName(authorUpdateRequest.getName());
        }
        if (authorUpdateRequest.getLastName() != null && !authorUpdateRequest.getLastName().isEmpty()) {
            authorToUpdate.setLastName(authorUpdateRequest.getLastName());
        }
    }
}
