package com.aleiva.books.services;

import com.aleiva.books.dto.BookDto;
import com.aleiva.books.models.Author;
import com.aleiva.books.models.Book;
import com.aleiva.books.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBookById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with ID " + id + " not found"));
    }

    public Book createBook(Book book){
        return bookRepository.save(book);
    }

    public BookDto updateBook(Long id, Book book){
        return bookRepository.findById(id)
                .map(b -> {
                    b.setTitle(book.getTitle());
                    b.setGenre(book.getGenre());
                    b.setQuantityAvailable(book.getQuantityAvailable());
                    b.setYearOfPublication(book.getYearOfPublication());
                    book.getAuthors().forEach(author -> author.getBooks().add(b));
                    b.setAuthors(book.getAuthors());
                    Book book2 = bookRepository.save(b);
                    BookDto bookDto = new BookDto();
                    bookDto.setName(book2.getTitle());
                    return bookDto;
                })
                .orElseThrow(() -> new RuntimeException("Book with ID " + id + " not found"));
    }

    public void deleteBookById(Long id){
        bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with ID " + id + " not found"));
        bookRepository.deleteById(id);
    }


}
