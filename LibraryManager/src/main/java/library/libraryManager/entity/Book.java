package library.libraryManager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="book")
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false,length=32)
    private String title;
    @Column(nullable=false,length=64)
    private String author;
    @Column(nullable=false,length=32)
    private String genre;
    @Column(nullable=false,length=32)
    private double price;


}