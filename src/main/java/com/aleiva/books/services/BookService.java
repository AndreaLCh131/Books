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

    public BookDTO updateBook(Long id, Book book){
        return bookRepository.findById(id)
                .map(b -> {
                    b.setTitle(book.getTitle());
                    b.setGenre(book.getGenre());
                    b.setQuantityAvailable(book.getQuantityAvailable());
                    b.setYearOfPublication(book.getYearOfPublication());
                    book.getAuthors().forEach(author -> author.getBooks().add(b));
                    b.setAuthors(book.getAuthors());
                    Book book2 = bookRepository.save(b);
                    BookDTO bookDto = new BookDTO();
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
