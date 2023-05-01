package com.example.rediscacheasidedemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/")
    public String Index(){
        return "Hello from Spring book store";
    }

    @GetMapping("/loadBooks")
    public String loadBooks() {

        bookRepository.loadBooks();
        return "Book loaded, book cache cleaned";
    }

    @GetMapping("/book/{isbn}")
    public String getByIsbn(@PathVariable("isbn") String isbn)
    {
        return "Fetched book: "+ bookRepository.getByIsbn(isbn).toString();
    }

    @GetMapping("/update/{isbn}")
    public String updateByIsbn(@PathVariable("isbn") String isbn)
    {
        return "Updated book: "+ bookRepository.updateBook(isbn);
    }
}
