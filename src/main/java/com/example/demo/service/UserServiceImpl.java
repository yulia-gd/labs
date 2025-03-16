package com.example.demo.service;

import com.example.demo.entities.Book;
import com.example.demo.entities.User;
import com.example.demo.exceptions.AlreadyBorrowedBookException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.BookUpdateRequest;
import com.example.demo.request.UserUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BookService bookService;
    private final BookRepository bookRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);

        Iterator<Book> iterator = user.getBorrowedBooks().iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            book.getUsers().remove(user);
            book.incrementAmount();
            iterator.remove(); // Використовуємо iterator.remove() замість user.getBorrowedBooks().remove(book)
            bookRepository.save(book);
        }

        userRepository.delete(user);
    }


    @Override
    @Transactional
    public User updateUser(Long id, UserUpdateRequest user) {
        User userToUpdate = getUserById(id);
        updateExistingUser(userToUpdate, user);
        return userRepository.save(userToUpdate);
    }
    private void updateExistingUser(User userToUpdate, UserUpdateRequest user) {
        if(user.getName()!=null && !user.getName().isEmpty()) {
            userToUpdate.setName(user.getName());
        }
        if(user.getEmail()!=null && !user.getEmail().isEmpty()) {
            userToUpdate.setEmail(user.getEmail());
        }
        if(user.getLastName()!=null && !user.getLastName().isEmpty()) {
            userToUpdate.setLastName(user.getLastName());
        }

    }

    @Override
    @Transactional
    public User borrowBook(Long userId, Long bookId) {
        User user = getUserById(userId);
        Book book = bookService.getBookById(bookId);
        if(user.getBorrowedBooks().contains(book)) {
            throw new AlreadyBorrowedBookException("Book already borrowed");
        }
        if (book.getAmount() > 0) {
            user.borrowBook(book);
            userRepository.save(user);
            bookService.updateBook(book.getId(), new BookUpdateRequest(null,null,  book.getAmount()));

            return user;
        } else {
            throw new ResourceNotFoundException("Book is not available for borrowing");
        }
    }


    @Override
    @Transactional
    public User returnBook(Long userId, Long bookId) {
        User user = getUserById(userId);
        Book book = bookService.getBookById(bookId);

        if (user.getBorrowedBooks().contains(book)) {
            user.returnBook(book);
            userRepository.save(user);
            bookService.updateBook(book.getId(), new BookUpdateRequest(null, null, book.getAmount()));

            return user;
        } else {
            throw new ResourceNotFoundException("User hasn't borrowed this book");
        }
    }
}

