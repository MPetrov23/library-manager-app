package library.libraryManager.mapper;

import library.libraryManager.dto.BookDTO;
import library.libraryManager.entity.Book;

public class BookMapper {
    public BookDTO convertEntityToDTO(Book book){
        BookDTO bookDTO =new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getPrice()
        );
        return bookDTO;
    }

    public Book convertDTOtoEntity(BookDTO bookDTO){
        Book book=new Book(
                bookDTO.getId(),
                bookDTO.getTitle(),
                bookDTO.getAuthor(),
                bookDTO.getGenre(),
                bookDTO.getPrice()
        );
        return book;
    }
}
