package com.example.demo.service;

import com.example.demo.entities.Author;
import com.example.demo.entities.Book;
import com.example.demo.entities.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.BookUpdateRequest;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Book addBook(Book book, Long authorId) {
        Author author = authorService.getAuthorById(authorId);
        book.setAuthor(author);  // Ensure the author is set properly
        author.addBook(book);  // Ensure the relationship is bidirectional if necessary
        return bookRepository.save(book);
    }


    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Book not found"));
    }

    @Transactional
    @Override
    public void deleteBookById(Long id) {
        Book book = getBookById(id);

        Author author = book.getAuthor();
        if (author != null) {
            author.getBooks().remove(book);
            authorRepository.save(author);
        }
        for(User user : book.getUsers()){
            user.getBorrowedBooks().remove(book);
            userRepository.save(user);
        }
        bookRepository.delete(book);
    }




    @Override
    @Transactional
    public Book updateBook(Long id, BookUpdateRequest bookUpdateRequest) {
        Book bookToUpdate = getBookById(id);
        updateExistingBook(bookToUpdate, bookUpdateRequest);
        return bookRepository.save(bookToUpdate);
    }

    private void updateExistingBook(Book bookToUpdate, BookUpdateRequest bookUpdateRequest) {
        if(bookUpdateRequest.getTitle() != null && !bookUpdateRequest.getTitle().isEmpty()) {
            bookToUpdate.setTitle(bookUpdateRequest.getTitle());
        }
        if(bookUpdateRequest.getAuthorId()!=null){
            Author author = authorService.getAuthorById(bookUpdateRequest.getAuthorId());
            bookToUpdate.setAuthor(author);
            author.addBook(bookToUpdate);
            authorRepository.save(author);
        }
        if(bookUpdateRequest.getAmount()!=0){
            bookToUpdate.setAmount(bookUpdateRequest.getAmount());
        }
    }
}
