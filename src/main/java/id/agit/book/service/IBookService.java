package id.agit.book.service;

import id.agit.book.dto.request.BaseBookRequest;
import id.agit.book.dto.request.BorrowRequest;
import id.agit.book.dto.response.BookResponse;
import id.agit.book.exception.ResourceNotFoundException;

import java.util.List;

public interface IBookService {
    List<BookResponse> getAll();
    List<BookResponse> getByStatus(String status) throws ResourceNotFoundException;
    void create(List<BaseBookRequest> baseBookRequestsList);
    void updateBook(BaseBookRequest baseBookRequest) throws ResourceNotFoundException;
    void updateStatus(String id, String status) throws ResourceNotFoundException;
    void borrow(BorrowRequest borrowRequest) throws ResourceNotFoundException;
    void delete(String id);
}
