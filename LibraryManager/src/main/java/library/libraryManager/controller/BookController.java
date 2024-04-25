package library.libraryManager.controller;

import jakarta.validation.Valid;
import library.libraryManager.dto.BookDTO;
import library.libraryManager.dto.OrderDTO;
import library.libraryManager.service.BookService;

import lombok.RequiredArgsConstructor;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
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
    public ModelAndView addBook(@Valid @ModelAttribute("book") BookDTO bookDTO, BindingResult result) {

        if (bookService.existsTitle(bookDTO.getTitle())) {
            result.rejectValue("title",
                    "duplicate.title",
                    "There is a book with the same title");}
        if(bookDTO.getPrice()<=0){
            result.rejectValue("price",
                    "invalid.price" ,
                    "Price can't be 0 or less");}
        if (result.hasErrors()) {
            return new ModelAndView("addBook",
                    "book",
                    bookDTO);
        }

        bookService.saveBook(bookDTO);
        return new ModelAndView("redirect:/listBooks");
    }

    @GetMapping("/editBook/{id}")
    public ModelAndView showEditBookForm(@PathVariable(value="id")Long id) {
        return new ModelAndView("editBook",
                "book",
                bookService.findBookDTOById(id));
    }

    @PostMapping("/saveEditBook")
    public ModelAndView saveEdit(@ModelAttribute("book") BookDTO bookDTO, BindingResult result) {

        if(bookDTO.getPrice()<=0){
            result.rejectValue("price",
                    "invalid.price",
                    "Price can't be 0 or less");}
        if (result.hasErrors()) {
            return new ModelAndView("/editBook", "book", bookDTO);
            }

        bookService.saveBook(bookDTO);
        return new ModelAndView("redirect:/listBooks");
    }

    @GetMapping(value="/deleteBook/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/listBooks";
    }

    @GetMapping(value = "/orderBook/{id}")
    public ModelAndView order(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("confirmOrder");

        modelAndView.addObject("book", bookService.findBookById(id));
        modelAndView.addObject("order", new OrderDTO());

        return modelAndView;
    }

}
