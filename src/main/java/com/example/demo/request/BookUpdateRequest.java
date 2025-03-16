package com.example.demo.request;

import lombok.Data;

@Data
public class BookUpdateRequest {

    private String title;

    public BookUpdateRequest(String title, Long authorId, int amount) {
        this.title = title;
        this.authorId = authorId;
        this.amount = amount;
    }

    private Long authorId;
    private int amount;

}
