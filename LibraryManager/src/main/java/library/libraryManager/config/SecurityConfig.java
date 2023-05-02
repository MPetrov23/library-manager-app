package library.libraryManager.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers(
                                        "/","index","/register/**",
                                        "login/**","listBooks","confirmOrder")
                                .permitAll()

                                .requestMatchers(
                                        "deleteBook/**","deleteUser/**","editBook/**",
                                        "saveNewBook","saveEditBook","listUsers",
                                        "addBook","editBook","listOrders",
                                        "deleteOrder/**")
                                .hasRole("ROLE_ADMIN")

                                .requestMatchers("/css/**","/images/**","/scripts/**").permitAll()

                                .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .loginProcessingUrl("/loginProcessing")
                                .defaultSuccessUrl("/",true)
                                .permitAll()
                )

                .logout(
                        logout -> logout
                                .logoutSuccessUrl("/")
                );

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    }