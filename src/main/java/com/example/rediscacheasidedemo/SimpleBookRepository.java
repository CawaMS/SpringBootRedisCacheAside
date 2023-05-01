package com.example.rediscacheasidedemo;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SimpleBookRepository implements BookRepository {

    @Override
    @Cacheable(cacheNames="books", key="#isbn", sync = true )
    public Book getByIsbn(String isbn) {
        simulateSlowService();
        return new Book(isbn, "some book");
    }

    @CachePut(cacheNames="books", key="#isbn")
    public Book updateBook(String isbn)
    {
        return new Book(isbn, "updated book");
    }

    @CacheEvict(cacheNames="books", allEntries=true)
    public void loadBooks()
    {

    }


    // Don't do this at home
    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}
