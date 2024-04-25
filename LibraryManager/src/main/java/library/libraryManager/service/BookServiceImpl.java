package library.libraryManager.service;

import library.libraryManager.dto.BookDTO;
import library.libraryManager.entity.Book;
import library.libraryManager.mapper.BookMapper;
import library.libraryManager.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookMapper mapBook;
    private final BookRepository bookRepository;

    public BookServiceImpl( BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.mapBook = new BookMapper();
    }

    @Override
    public void saveBook(BookDTO bookDTO) {
        Book book = mapBook.convertDTOtoEntity(bookDTO);
            this.bookRepository.save(book);
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepository.findBookById(id);
    }

    @Override
    public BookDTO findBookDTOById(Long id) {
        return mapBook.convertEntityToDTO(bookRepository.findBookById(id));
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
    public boolean existsTitle(String title) {
        return bookRepository.existsByTitle(title);
    }

    @Override
    public List<BookDTO> findAllBooks() {
        List<Book> books=bookRepository.findAll();
        return books.stream().map((book)-> mapBook.convertEntityToDTO(book)).collect(Collectors.toList());
    }

}