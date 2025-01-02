package com.example.postproject.Exception;

public class PostsNotFoundException extends RuntimeException {
    public PostsNotFoundException(String message) {
        super(message);
    }
}
