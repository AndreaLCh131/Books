package com.aleiva.books.controllers;

import com.aleiva.books.dto.BookDto;
import com.aleiva.books.models.Book;
import com.aleiva.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id){
        Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody Book book){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.createBook(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("id") Long id, @RequestBody Book book){
        return ResponseEntity.ok(bookService.updateBook(id,book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id){
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }


}
