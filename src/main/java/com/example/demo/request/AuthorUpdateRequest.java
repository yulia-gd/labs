package com.example.demo.request;

import com.example.demo.entities.Book;

import lombok.Data;

import java.util.Set;

@Data
public class AuthorUpdateRequest {

    private String name;

    private String lastName;

    private Set<Book> books;
}
