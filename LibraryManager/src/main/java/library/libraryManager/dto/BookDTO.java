package library.libraryManager.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;
    @NotEmpty(message = "Title should not be empty")
    private String title;
    @NotEmpty(message = "Author should not be empty")
    private String author;
    @NotEmpty(message = "Genre should not be empty")
    private String genre;
    @NotNull
    private double price;


}
