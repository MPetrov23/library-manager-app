package library.libraryManager.mapper;

import library.libraryManager.dto.UserDTO;
import library.libraryManager.entity.Role;
import library.libraryManager.entity.User;
import library.libraryManager.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserMapper {
    @Autowired
    private static RoleRepository roleRepository;
    public UserMapper(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }
    public static UserDTO convertEntityToDto(User user){
        String[] name=user.getName().split(" ");

        UserDTO userDto=new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                name[0],
                name[1],
                user.getPassword(),
                user.isActive()
        );
        return userDto;
    }
    public static User convertDtoToEntity(UserDTO userDTO){

        Role role=roleRepository.findByName("ROLE_USER");
        if(role==null){
            role=checkRoleExist();
        }

        User user=new User(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getFirstName()+" "+userDTO.getLastName(),
                userDTO.getPassword(),
                true,
                List.of(role)
        );

        return user;
    }
    public static Role checkRoleExist(){
        Role role=new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }
}
