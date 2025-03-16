package com.example.demo.controllers;

import com.example.demo.entities.Book;
import com.example.demo.request.BookUpdateRequest;
import com.example.demo.responce.ApiResponse;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.apache.tomcat.websocket.Constants.*;
import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/book/{id}")
    public ResponseEntity<ApiResponse> getBookById( @PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.status(FOUND).body(new ApiResponse("Book found", book));
    }
    @PostMapping("/add/{authorId}")
    public ResponseEntity<ApiResponse> addBook(@Valid @RequestBody Book book, @PathVariable Long authorId) {
        Book addedBook =  bookService.addBook(book, authorId);
        return ResponseEntity.status(CREATED).body(new ApiResponse("Book added. Location: "+ "http://localhost:8080/api/v1/books/book/"+addedBook.getId(), addedBook));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllBooks() {
        return ResponseEntity.status(FOUND).body(new ApiResponse("All books found", bookService.getBooks()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateBook(@PathVariable Long id, @RequestBody BookUpdateRequest book) {
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(new ApiResponse("Book updated. Location: "+ "http://localhost:8080/api/v1/books/book/"+updatedBook.getId(), updatedBook));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok(new ApiResponse("Book deleted", null));
    }

}
