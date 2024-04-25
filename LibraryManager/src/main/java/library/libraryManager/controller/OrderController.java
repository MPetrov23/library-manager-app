package library.libraryManager.controller;

import library.libraryManager.dto.OrderDTO;
import library.libraryManager.email.EmailSenderService;
import library.libraryManager.entity.Book;
import library.libraryManager.entity.Order;
import library.libraryManager.entity.User;
import library.libraryManager.service.BookService;
import library.libraryManager.service.OrderService;
import library.libraryManager.service.UserService;
import library.libraryManager.validation.DataValidation;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private OrderService orderService;
    private BookService bookService;
    private UserService userService;
    private EmailSenderService mailSenderService;

    @PostMapping(value="/confirmOrder/{id}")
    public ModelAndView submitOrder(@PathVariable Long id,
                              @ModelAttribute("order") OrderDTO orderDTO,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {

        Book book=bookService.findBookById(id);
        User receiver=userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (!DataValidation.isValidPhoneNumber(orderDTO.getPhoneNumber())) {
            result.rejectValue("phoneNumber", null, "Invalid phone number!");}
        if (result.hasErrors()) {

            return new ModelAndView("confirmOrder",
                    "book",
                    book);
        }

        Order order = new Order(
                receiver.getUsername(),
                book.getTitle(),
                LocalDate.now(),
                orderDTO.getAddress(),
                orderDTO.getPhoneNumber()
        );

        orderService.saveOrder(order);

        mailSenderService.sendEmail(
                receiver.getEmail(),
                receiver.getName(),
                book.getTitle(),
                String.valueOf(book.getPrice()),
                order.getAddress(),
                order.getPhoneNumber(),
                String.valueOf(order.getDate()),
                String.valueOf(order.getId())
        );
        redirectAttributes.addAttribute("success",true);
        return new ModelAndView("redirect:/listBooks");
    }

    @GetMapping("/listOrders")
    public ModelAndView listOrders(Model model) {
        return new ModelAndView("listOrders",
                "orders",
                orderService.findAllOrders());
    }

    @GetMapping("/deleteOrder/{id}")
    public ModelAndView deleteOrder(@PathVariable Long id,
                              RedirectAttributes redirectAttributes){

        orderService.deleteOrderById(id);

        redirectAttributes.addAttribute("success",true);
        return new ModelAndView("listOrders");
    }

}