package com.joachim.books.shelf.web;

import com.joachim.books.shelf.model.Book;
import com.joachim.books.shelf.repository.BookRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("db")
@RequiredArgsConstructor
public class TemporaryDatabaseController {

    private final BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<DBResponse> getAllEntries() {
        List<Book> allBooks = bookRepository.findAll();
        return new ResponseEntity<>(new DBResponse(allBooks), HttpStatus.OK);
    }

    @RequiredArgsConstructor
    @Data
    private static class DBResponse {
        private final List<Book> allBooks;
    }
}
