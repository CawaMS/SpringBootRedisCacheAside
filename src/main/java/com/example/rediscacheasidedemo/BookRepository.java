package com.example.rediscacheasidedemo;

public interface BookRepository {

    Book getByIsbn(String isbn);

}
