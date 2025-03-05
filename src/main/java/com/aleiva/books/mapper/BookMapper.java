package com.aleiva.books.mapper;

import com.aleiva.books.dto.BookDTO;
import com.aleiva.books.models.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = { AuthorMapper.class })
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    @Mapping(source = "title", target = "name")
    BookDTO bookToBookDto(Book book);
    @Mapping(source = "name", target = "title")
    Book bookDtoToBook(BookDTO bookDTO);
    List<BookDTO> booksToBookDTOs(List<Book> books);

}
