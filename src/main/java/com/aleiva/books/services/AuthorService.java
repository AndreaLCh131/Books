package com.aleiva.books.services;

import com.aleiva.books.dto.AuthorDTO;
import com.aleiva.books.mapper.AuthorMapper;
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
    @Autowired
    private AuthorMapper authorMapper;

    public List<AuthorDTO> getAllAuthors(){
        return authorMapper.authorsToAuthorDTOs(authorRepository.findAll());
    }

    public AuthorDTO getAuthorById(Long id){
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author with ID " + id + " not found"));
        return authorMapper.authorToAuthorDTO(author);
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
