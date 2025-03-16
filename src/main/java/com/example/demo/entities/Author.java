package com.example.demo.entities;

import com.example.demo.request.BookUpdateRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@ToString(exclude = "books")
@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be null")
    private String name;

    @Column(name = "last_name")
    @NotBlank(message = "Lastname cannot be null")
    private String lastName;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private Set<Book> books = new HashSet<>();


    public void addBook(Book book) {
       books.add(book);
    }
}
