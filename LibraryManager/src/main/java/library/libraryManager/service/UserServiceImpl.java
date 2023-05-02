package library.libraryManager.service;

import library.libraryManager.dto.UserDTO;
import library.libraryManager.entity.User;
import library.libraryManager.mapper.UserMapper;
import library.libraryManager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public void saveUser(UserDTO userDto) {
        User user = UserMapper.convertDtoToEntity(userDto);

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {return userRepository.findByUsername(username);}
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public User findUserById(Long id) {return userRepository.findById(id).get();}

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users=userRepository.findAll();
        return users.stream().map((user)->UserMapper.convertEntityToDto(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
