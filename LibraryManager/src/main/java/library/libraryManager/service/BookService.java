package library.libraryManager.service;

import library.libraryManager.dto.BookDTO;
import library.libraryManager.entity.Book;

import java.util.List;

public interface BookService {

    void saveBook(BookDTO bookDTO);

    Book findBookById(Long id);

    Book findBookByTitle(String title);

    List<BookDTO> findAllBooks();

    void deleteBook(Long id);

    boolean existsUsername(String username);

}
