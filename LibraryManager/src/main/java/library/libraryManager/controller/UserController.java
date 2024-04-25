package library.libraryManager.controller;

import library.libraryManager.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/listUsers")
    public ModelAndView listUsers(Model model) {
        return new ModelAndView("listUsers",
                "users",
                userService.findAllUsers());
    }

    @GetMapping(value="/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/listUsers?success";
    }

}