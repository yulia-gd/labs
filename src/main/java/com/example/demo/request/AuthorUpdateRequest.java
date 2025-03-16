package com.example.demo.request;

import com.example.demo.entities.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Set;

@Data
public class AuthorUpdateRequest {

    private String name;

    private String lastName;

    private Set<Book> books;
}
