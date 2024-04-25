package library.libraryManager.controller;

import jakarta.validation.Valid;
import library.libraryManager.dto.OrderDTO;
import library.libraryManager.entity.Book;
import library.libraryManager.entity.Order;
import library.libraryManager.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

public class OrderController {



    @PostMapping(value="/confirmOrder/{id}")
    public String confirmOrder(@PathVariable Long id, @Valid @ModelAttribute("order") OrderDTO orderDTO, BindingResult result, Model model) {
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
