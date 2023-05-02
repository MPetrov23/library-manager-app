package library.libraryManager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="orders")
public class Order {
    @Id
    @SequenceGenerator(name="mySequence", initialValue=100000,allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="mySequence")
    private Long id;
    @Column(nullable=false,length=32)
    private String username;
    @Column(nullable=false,length=32)
    private String title;
    @Column(nullable=false,length=64)
    private LocalDate date;
    @Column(nullable=false,length=128)
    private String address;
    @Column(nullable=false,length=10)
    private String phoneNumber;

    public Order(String username, String title, LocalDate date, String address, String phoneNumber) {
        this.username = username;
        this.title = title;
        this.date = date;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
