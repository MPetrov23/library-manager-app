package library.libraryManager.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    @Email
    @NotEmpty(message = "Email should not be empty")
    private String email;
    @NotEmpty(message = "Username should not be empty")
    private String username;
    @NotEmpty(message = "First name should not be empty")
    private String firstName;
    @NotEmpty(message = "Last name should not be empty")
    private String lastName;
    @NotEmpty(message = "Password should not be empty")
    private String password;

    private boolean active;
}