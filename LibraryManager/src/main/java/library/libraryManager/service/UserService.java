package library.libraryManager.service;


import library.libraryManager.dto.UserDTO;
import library.libraryManager.entity.User;
import java.util.List;

public interface UserService {

  void saveUser(UserDTO userDto);

  User findUserByUsername(String username);

  User findUserByEmail(String email);

  User findUserById(Long id);

  List<UserDTO> findAllUsers();

  void deleteUser(Long id);

  boolean existsUsername(String username);

  boolean existsUserEmail(String email);

}
