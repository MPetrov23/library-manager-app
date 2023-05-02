package library.libraryManager.config;

import library.libraryManager.entity.Role;
import library.libraryManager.entity.User;
import library.libraryManager.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.stream.Collectors;


@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);


        if (user != null) {
            if(!user.isActive()){
                try {
                    throw new Exception("Inactive account!");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    mapRolesToAuthorities(user.getRoles()));

        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }
    }