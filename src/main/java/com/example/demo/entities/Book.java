package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@ToString
@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotBlank(message = "Title cannot be null")
    private String title;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(name = "amount")
    private int amount;

    @ManyToMany(mappedBy = "borrowedBooks")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public void incrementAmount() {
        this.amount++;
    }

    public void decrementAmount() {
        if (this.amount == 0) {
            return;
        }
        this.amount--;
    }

    public void update(Book book) {
        this.title = book.getTitle();
        this.amount = book.getAmount();
    }
}
