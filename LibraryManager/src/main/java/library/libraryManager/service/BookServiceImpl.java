package library.libraryManager.service;

import library.libraryManager.dto.BookDTO;
import library.libraryManager.entity.Book;
import library.libraryManager.mapper.BookMapper;
import library.libraryManager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class BookServiceImpl implements BookService {

    private BookMapper mapBook;
    @Autowired
    private BookRepository bookRepository;

    public BookServiceImpl( BookRepository bookRepository) {

        this.bookRepository = bookRepository;
    }

    @Override
    public void saveBook(BookDTO bookDTO) {
        Book book = mapBook.convertDTOtoEntity(bookDTO);
            this.bookRepository.save(book);
    }
    @Override
    public void updateBook(Book book) {
        this.bookRepository.save(book);
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepository.findById(id).get();
    }
    @Override
    public Book findBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }
    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    @Override
    public List<BookDTO> findAllBooks() {
        List<Book> books=bookRepository.findAll();
        return books.stream().map((book)->mapBook.convertEntityToDTO(book)).collect(Collectors.toList());
    }


}