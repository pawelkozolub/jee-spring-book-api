package pl.coderslab.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.model.Book;
import pl.coderslab.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private BookService bookService;

    // injection via class constructor > note also that we use interphase here, not a specific implementation
    // such as MockBookService > in future it allows us to change MockBookService into other e.g. FileBookService
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getList() {
        return bookService.getBooks();
    }

    @PostMapping
    public void addBook(@RequestBody Book book) {
        bookService.add(book);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable(name="id") Long id) {
        //return bookService.get(id).orElse(null);  // no information if object exists > only null returned
        return bookService.get(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable(name="id") Long id) {
        bookService.delete(id);
    }

    @PutMapping
    public void updateBook(@RequestBody Book book) {
        bookService.update(book);
    }

    @RequestMapping("/helloBook")
    public Book helloBook() {
        return new Book(1L, "9788324631766", "Thinking in Java", "Bruce Eckel", "Helion", "programming");
    }
}
