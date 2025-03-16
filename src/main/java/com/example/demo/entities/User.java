package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "User must have name")
    private String name;

    @Column(name = "last_name")
    @NotBlank(message = "User must have name")
    private String lastName;

    @Column(name = "email", unique = true)
    @NotBlank(message = "User must have email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "borrowed_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> borrowedBooks = new HashSet<>();


    public void borrowBook(Book book){
        borrowedBooks.add(book);
        book.decrementAmount();
        book.getUsers().add(this);
    }
    public void returnBook(Book book) {
        borrowedBooks.remove(book);
        book.incrementAmount();
        book.getUsers().remove(this);
    }


}
