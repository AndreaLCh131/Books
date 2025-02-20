package com.aleiva.books.services;

import com.aleiva.books.models.Author;
import com.aleiva.books.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id){
        return authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author with ID " + id + " not found"));
    }

    public Author createAuthor(Author author){
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author author){
        String name = author.getFirstName();
        return authorRepository.findById(id)
                .map(a -> {
                     a.setFirstName(author.getFirstName());
                     a.setLastName(author.getLastName());
                     a.setDateOfBirth(author.getDateOfBirth());
                     author.getBooks().forEach(book -> book.getAuthors().add(a));
                     a.setBooks(author.getBooks());
                     return authorRepository.save(a);
                })
                .orElseThrow(() -> new RuntimeException("Author with ID " + id + " not found"));
    }

    public void deleteAuthorById(Long id){
        authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author with ID " + id + " not found"));
        authorRepository.deleteById(id);
    }
}
