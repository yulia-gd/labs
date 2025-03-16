package com.example.demo.controllers;

import com.example.demo.entities.Author;
import com.example.demo.request.AuthorUpdateRequest;
import com.example.demo.responce.ApiResponse;
import com.example.demo.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.apache.tomcat.websocket.Constants.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    // Отримати автора за ID
    @GetMapping("/author/{id}")
    public ResponseEntity<ApiResponse> getAuthorById(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id);
        return ResponseEntity.status(FOUND).body(new ApiResponse("Author found", author));
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllAuthors() {
        return ResponseEntity.status(FOUND).body(new ApiResponse("All authors found", authorService.getAll()));
    }

    // Додати автора
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addAuthor(@Valid @RequestBody Author author) {
        Author addedAuthor = authorService.saveAuthor(author);
        return ResponseEntity.status(CREATED).body(new ApiResponse("Author added. Location: "+ "http://localhost:8080/api/v1/authors/author/"+addedAuthor.getId(), addedAuthor));
    }

    // Оновити автора
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateAuthor(@PathVariable Long id, @RequestBody AuthorUpdateRequest authorUpdateRequest) {
        Author updatedAuthor = authorService.updateAuthor(id, authorUpdateRequest);
        return ResponseEntity.ok(new ApiResponse("Author updated. Location: "+ "http://localhost:8080/api/v1/authors/author/"+updatedAuthor.getId(), updatedAuthor));
    }

    // Видалити автора
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok(new ApiResponse("Author deleted", null));
    }
}
