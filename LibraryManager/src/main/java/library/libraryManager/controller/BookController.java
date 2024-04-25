package library.libraryManager.controller;

import jakarta.validation.Valid;
import library.libraryManager.dto.BookDTO;
import library.libraryManager.dto.OrderDTO;
import library.libraryManager.entity.Book;
import library.libraryManager.service.BookService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


public class BookController {
    private BookService bookService;

    @GetMapping("/listBooks")
    public ModelAndView listBooks() {
        return new ModelAndView("listBooks",
                "books",
                bookService.findAllBooks());
    }

    @GetMapping("/addNewBook")
    public ModelAndView showAddNewBookForm() {
        return new ModelAndView("addBook",
                "book",
                new BookDTO());
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
    public String showEditBookForm(@PathVariable(value="id")Long id, Model model) {
        BookDTO book=mapBook.convertEntityToDTO(bookService.findBookById(id));
        model.addAttribute("book",book);
        return "editBook";
    }

    @PostMapping("/saveEditBook")
    public String saveEdit(@Valid  @ModelAttribute("book") BookDTO bookDTO, BindingResult result, Model model) {
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
}
