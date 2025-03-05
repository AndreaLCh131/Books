package com.aleiva.books.mapper;

import com.aleiva.books.dto.AuthorDTO;
import com.aleiva.books.models.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);
    AuthorDTO authorToAuthorDTO(Author author);
    Author authorDTOToAuthor(AuthorDTO authorDTO);
    List<AuthorDTO> authorsToAuthorDTOs(List<Author> authors);
    List<Author> authorDTOsToAuthors(List<AuthorDTO> authorDTOs);
}
