package com.example.rediscacheasidedemo;

public interface BookRepository {

    Book getByIsbn(String isbn);

    Book updateBook(String isbn);

    public void loadBooks();
}
