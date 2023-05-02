package library.libraryManager.controller;

import jakarta.validation.Valid;
import library.libraryManager.dto.BookDTO;
import library.libraryManager.dto.OrderDTO;
import library.libraryManager.dto.UserDTO;
import library.libraryManager.entity.Book;
import library.libraryManager.entity.Order;
import library.libraryManager.entity.User;
import library.libraryManager.mapper.BookMapper;
import library.libraryManager.mapper.UserMapper;
import library.libraryManager.service.BookServiceImpl;
import library.libraryManager.email.EmailSenderService;
import library.libraryManager.service.OrderService;
import library.libraryManager.service.UserServiceImpl;
import library.libraryManager.validation.DataValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

@Controller
public class AppController {
	@Autowired
	private UserServiceImpl userService;
	private UserMapper mapUser;
	@Autowired
	private BookServiceImpl bookService;
	private BookMapper mapBook;
	@Autowired
	private OrderService orderService;
	@Autowired
	private EmailSenderService emailSender;

	private DataValidation validation;



	@GetMapping("/")
	public String home(Model model) {

	return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String registrationForm(Model model) {
		UserDTO user = new UserDTO();
		model.addAttribute("user", user);
	return "register";
	}

	@PostMapping("/register/save")
	public String registrationHandler(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model) {

		User existingEmail = userService.findUserByEmail(userDTO.getEmail());
		User existingUsername=userService.findUserByUsername(userDTO.getUsername());

		if (existingEmail != null && existingEmail.getEmail() != null && !existingEmail.getEmail().isEmpty()) {
			result.rejectValue("email", null, "This email is already in use!");}
		if (existingUsername != null && existingUsername.getUsername() != null && !existingUsername.getUsername().isEmpty()) {
			result.rejectValue("username", null, "This username is already in use!");}
		if (result.hasErrors()) {
			model.addAttribute("user", userDTO);
		return "register";}

		userService.saveUser(userDTO);
	return "redirect:/register?success";
	}

	@GetMapping("/listUsers")
	public String listUsers(Model model) {
		List<UserDTO> users = userService.findAllUsers();
		model.addAttribute("users", users);
	return "listUsers";
	}

	@GetMapping(value="/deleteUser/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return "redirect:/listUsers?success";
	}


	@GetMapping("/listBooks")
	public String listBooks(Model model) {
		List<BookDTO> books = bookService.findAllBooks();
		model.addAttribute("books", books);
		return "listBooks";
	}

	@GetMapping("/addNewBook")
	public String showAddNewBookForm(Model model) {
		BookDTO bookDTO=new BookDTO();
		model.addAttribute("book",bookDTO);
	return "addBook";
	}

	@PostMapping("/saveNewBook")
	public String addBook(@Valid @ModelAttribute("book") BookDTO bookDTO, BindingResult result, Model model) {

		Book existingBook = bookService.findBookByTitle(bookDTO.getTitle());

		if (existingBook != null && existingBook.getTitle() != null && !existingBook.getTitle().isEmpty()) {
			result.rejectValue("title", null, "There is a book with the same title");}
		if(bookDTO.getPrice()<=0){
			result.rejectValue("price",null,"Price can't be 0 or less");}
		if (result.hasErrors()) {
			model.addAttribute("book", bookDTO);
			return "addBook";}

		bookService.saveBook(bookDTO);
	return "redirect:/listBooks";
	}

	@GetMapping("/editBook/{id}")
	public String showEditBookForm(@PathVariable(value="id")Long id,Model model) {
		BookDTO book=mapBook.convertEntityToDTO(bookService.findBookById(id));
		model.addAttribute("book",book);
	return "editBook";
}

	@PostMapping("/saveEditBook")
	public String saveBook(@Valid  @ModelAttribute("book") BookDTO bookDTO, BindingResult result, Model model) {
		Book existingBook = bookService.findBookByTitle(bookDTO.getTitle());

		if(bookDTO.getPrice()<=0){
			result.rejectValue("price",null,"Price can't be 0 or less");}
		if (result.hasErrors()) {
			model.addAttribute("book", bookDTO);
			return "/editBook";}

		bookService.saveBook(bookDTO);
	return "redirect:/listBooks";
	}

	@GetMapping(value="/deleteBook/{id}")
	public String deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return "redirect:/listBooks";
	}

	@GetMapping(value = "/orderBook/{id}")
	public String order(@PathVariable Long id, Model model){
		Book book=bookService.findBookById(id);
		model.addAttribute("book", book);

		OrderDTO orderDTO=new OrderDTO();
		model.addAttribute("order",orderDTO);
		return "confirmOrder";
	}

	@PostMapping(value="/confirmOrder/{id}")
	public String confirmOrder(@PathVariable Long id, @Valid @ModelAttribute("order")OrderDTO orderDTO, BindingResult result,Model model) {
		Book book=bookService.findBookById(id);
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User receiver=userService.findUserByUsername(user.getUsername());


		if (!validation.isValidPhoneNumber(orderDTO.getPhoneNumber())) {
			result.rejectValue("phoneNumber", null, "Invalid phone number!");}
		if (result.hasErrors()) {
			model.addAttribute("book", book);
			return "confirmOrder";}

		Order order=new Order(
				user.getUsername(),
				book.getTitle(),
				LocalDate.now(),
				orderDTO.getAddress(),
				orderDTO.getPhoneNumber()
				);

		orderService.saveOrder(order);

		emailSender.sendEmail(
				receiver.getEmail(),
				receiver.getName(),
				book.getTitle(),
				String.valueOf(book.getPrice()),
				order.getAddress(),
				order.getPhoneNumber(),
				String.valueOf(order.getDate()),
				String.valueOf(order.getId())
		);

		return "redirect:/listBooks?success";

	}
	@GetMapping("/listOrders")
	public String listOrders(Model model) {
		List<Order> orders = orderService.findAllOrders();
		model.addAttribute("orders", orders);
		return "listOrders";
	}

	@GetMapping("/deleteOrder/{id}")
	public String deleteOrder(@PathVariable Long id){
		orderService.deleteOrderById(id);
		return "redirect:/listOrders?success";

	}


}