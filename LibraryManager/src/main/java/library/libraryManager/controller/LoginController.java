package library.libraryManager.controller;

import jakarta.validation.Valid;
import library.libraryManager.dto.UserDTO;
import library.libraryManager.service.UserServiceImpl;
import library.libraryManager.validation.DataValidation;
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
	public ModelAndView registrationHandler(@Valid @ModelAttribute("user") UserDTO user,
											BindingResult result,
											RedirectAttributes redirectAttributes) {

		validateData(user, result)
		;
		if (result.hasErrors()) {
			return new ModelAndView("register", "user", user);
		}

		userService.saveUser(user);
		redirectAttributes.addAttribute("success",true);
	return new ModelAndView("redirect:/register");
	}
	
	private void validateData(UserDTO user, BindingResult result){
		if (DataValidation.isValidUsername(user.getUsername())) {
			result.rejectValue("username",
					"invalid.username",
					"Invalid username format!");
		}

		if (DataValidation.isValidFirstName(user.getFirstName())) {
			result.rejectValue("firstName",
					"invalid.firstName",
					"Invalid first name!");
		}

		if (DataValidation.isValidLastName(user.getLastName())) {
			result.rejectValue("lastName",
					"invalid.lastName",
					"Invalid last name!");
		}

		if (DataValidation.isValidEmail(user.getEmail())) {
			result.rejectValue("email",
					"invalid.email",
					"Invalid email!");
		}

		if (DataValidation.isValidPassword(user.getPassword())) {
			result.rejectValue("password",
					"invalid.password",
					"Invalid password!");
		}
		if (userService.existsUserEmail(user.getEmail())) {
			result.rejectValue("email",
					"duplicate.email",
					"Email already in use!");
		}
		if (userService.existsUsername(user.getUsername())) {
			result.rejectValue("username",
					"duplicate.username",
					"Username already in use!");
		}
	}

}