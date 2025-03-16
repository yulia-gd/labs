package com.example.demo.repository;

import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"borrowedBooks.author"})
    List<User> findAll();

    @EntityGraph(attributePaths = {"borrowedBooks.author"})
    Optional<User> findById(Long id);
}
