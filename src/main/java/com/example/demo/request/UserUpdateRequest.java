package com.example.demo.request;

import lombok.Data;
import org.springframework.web.bind.annotation.DeleteMapping;

@Data
public class UserUpdateRequest {
    private String name;
    private String lastName;
    private String email;

}
