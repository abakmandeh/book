package id.agit.book.controller;

import id.agit.book.dto.request.BaseBookRequest;
import id.agit.book.dto.request.BorrowRequest;
import id.agit.book.dto.response.BookResponse;
import id.agit.book.exception.ResourceNotFoundException;
import id.agit.book.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static id.agit.book.ApiConstant.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {

    private final IBookService bookService;

    @GetMapping(GET)
    public ResponseEntity<List<BookResponse>> getAll() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping(GET_BY_STATUS)
    public ResponseEntity<List<BookResponse>> getByStatus(@PathVariable(value = "status") String status) throws ResourceNotFoundException {
        return ResponseEntity.ok(bookService.getByStatus(status));
    }

    @PostMapping(CREATE)
    public ResponseEntity create(@RequestBody List<BaseBookRequest> baseBookRequestsList) {
        bookService.create(baseBookRequestsList);
        return ResponseEntity.ok().body("Success to create book");
    }

    @PutMapping(UPDATE_BOOK)
    public ResponseEntity updateBook(@RequestBody BaseBookRequest updateBook) throws ResourceNotFoundException {
        bookService.updateBook(updateBook);
        return ResponseEntity.ok().body("Success to update book data");
    }

    @PutMapping(UPDATE_STATUS)
    public ResponseEntity updateStatus(@RequestParam String bookId, @RequestParam String status) throws ResourceNotFoundException {
        bookService.updateStatus(bookId, status);
        return ResponseEntity.ok().body("Success to update book status");
    }

    @PutMapping(BORROW)
    public ResponseEntity borrow(@RequestBody BorrowRequest borrowRequest) throws ResourceNotFoundException {
        bookService.borrow(borrowRequest);
        return ResponseEntity.ok().body("Success to borrow book");
    }

    @DeleteMapping(DELETE)
    public ResponseEntity delete(@PathVariable(value = "bookId") String bookId) {
        bookService.delete(bookId);
        return ResponseEntity.ok().body("Success to delete book");
    }
}
