package library.libraryManager.controller;

import jakarta.validation.Valid;
import library.libraryManager.dto.UserDTO;
import library.libraryManager.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LoginController {
	private UserServiceImpl userService;

	@GetMapping("/")
	public String home(){
	return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public ModelAndView register(Model model) {
		return new ModelAndView("register",
				"user",
				new UserDTO());
	}

	@PostMapping("/registrationHandler")
	public ModelAndView registrationHandler(@Valid @ModelAttribute("user") UserDTO userDTO,
											BindingResult result,
											RedirectAttributes redirectAttributes) {

		if (userService.existsUserEmail(userDTO.getEmail())) {
			result.rejectValue("email",
					"duplicate.email",
					"This email is already in use!");}
		if (userService.existsUsername(userDTO.getUsername())) {
			result.rejectValue("username",
					"duplicate.username",
					"This username is already in use!");}
		if (result.hasErrors()) {
			return new ModelAndView("register", "user", userDTO);
		}

		userService.saveUser(userDTO);
		redirectAttributes.addAttribute("success",true);
	return new ModelAndView("redirect:/register");
	}

}