package library.libraryManager.repository;

import library.libraryManager.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
    Book findByTitle(String title);

}
