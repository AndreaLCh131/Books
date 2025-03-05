package com.aleiva.books.services;

import com.aleiva.books.dto.BookDTO;
import com.aleiva.books.mapper.BookMapper;
import com.aleiva.books.models.Author;
import com.aleiva.books.models.Book;
import com.aleiva.books.repositories.AuthorRepository;
import com.aleiva.books.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookMapper bookMapper;

    public List<BookDTO> getAllBooks(){
        return bookMapper.booksToBookDTOs(bookRepository.findAll());
    }

    public BookDTO getBookById(Long id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book with ID " + id + " not found"));
        return bookMapper.bookToBookDto(book);
    }

    public BookDTO createBook(BookDTO bookDTO){
        List<Author> authors = bookDTO.getAuthors().stream()
                .map( authorDTO ->
                    authorRepository.findById(authorDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Author not found")))
                .collect(Collectors.toList());
        Book book = bookMapper.bookDtoToBook(bookDTO);
        book.setAuthors(authors);
        book = bookRepository.save(book);
        return bookMapper.bookToBookDto(book);
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book with ID " + id + " not found"));

        List<Author> authors = bookDTO.getAuthors().stream()
                .map(authorDTO -> authorRepository.findById(authorDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Author not found")))
                .collect(Collectors.toList());

        book.setTitle(bookDTO.getName());
        book.setYearOfPublication(bookDTO.getYearOfPublication());
        book.setGenre(bookDTO.getGenre());
        book.setQuantityAvailable(bookDTO.getQuantityAvailable());
        book.setAuthors(authors);

        book = bookRepository.save(book);
        return bookMapper.bookToBookDto(book);
    }

    public void deleteBookById(Long id){
        bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with ID " + id + " not found"));
        bookRepository.deleteById(id);
    }


}
