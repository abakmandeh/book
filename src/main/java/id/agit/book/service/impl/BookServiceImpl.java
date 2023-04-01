package id.agit.book.service.impl;

import id.agit.book.dto.request.BaseBookRequest;
import id.agit.book.dto.request.BorrowRequest;
import id.agit.book.dto.response.BookResponse;
import id.agit.book.entity.Book;
import id.agit.book.entity.Borrow;
import id.agit.book.exception.BookIsBorrowedException;
import id.agit.book.exception.ResourceIsExistException;
import id.agit.book.exception.ResourceNotFoundException;
import id.agit.book.repository.BookRepository;
import id.agit.book.repository.BorrowRepository;
import id.agit.book.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;
    private final BorrowRepository borrowRepository;

    @Override
    public List<BookResponse> getAll() {
        List<Book> bookList = bookRepository.findAll();
        if (bookList.isEmpty()){
            throw new ResourceIsExistException("There is no book data");
        }
        return composeBookResponse(bookList);
    }

    private List<BookResponse> composeBookResponse(List<Book> bookList) {
        return bookList.stream()
                .map(book -> bookToBookResponse(book))
                .collect(Collectors.toList());
    }

    private BookResponse bookToBookResponse(Book book){
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(book.getId());
        bookResponse.setJudul(book.getJudul());
        bookResponse.setPengarang(book.getPengarang());
        bookResponse.setPenerbit(book.getPenerbit());
        bookResponse.setTanggalTerbit(book.getTanggalTerbit());
        bookResponse.setTebalBuku(book.getTebalBuku());
        bookResponse.setStatus(book.getBorrow().getStatus());
        bookResponse.setPeminjam(book.getBorrow().getPeminjam());
        bookResponse.setTanggalPinjam(book.getBorrow().getTanggalPinjam());
        bookResponse.setTanggalKembali(book.getBorrow().getTanggalKembali());
        return bookResponse;
    }

    @Override
    public List<BookResponse> getByStatus(String status) throws ResourceNotFoundException {
        List<Book> optionalBookList = bookRepository.findByStatus(status)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found for this status : " + status));
        return composeBookResponse(optionalBookList);
    }

    @Transactional
    @Override
    public void create(List<BaseBookRequest> baseBookRequestsList) {
        Set<String> idBookSet = baseBookRequestsList
                                    .stream()
                                    .map(BaseBookRequest::getId)
                                    .collect(Collectors.toSet());

        if (isBookIdExist(idBookSet)){
            throw new ResourceIsExistException("There is an existing book id from : " + idBookSet);
        }
        List<Book> bookList= composeBookToCreate(baseBookRequestsList);
        List<Borrow> borrowList= composeBorrowToCreate(baseBookRequestsList);
        bookRepository.saveAll(bookList);
        borrowRepository.saveAll(borrowList);
    }

    private boolean isBookIdExist(Set<String> idBookSet) {
        List<Book> bookListFromDB = bookRepository.findAllById(idBookSet);
        return !bookListFromDB.isEmpty();
    }

    private List<Book> composeBookToCreate(List<BaseBookRequest> baseBookRequestsList) {
        return baseBookRequestsList
                .stream()
                .map(bookRequest -> masterBookRequestToEntity(bookRequest))
                .collect(Collectors.toList());
    }

    private List<Borrow> composeBorrowToCreate(List<BaseBookRequest> baseBookRequestsList) {
        return baseBookRequestsList
                .stream()
                .map(bookRequest -> bookRequestToBorrow(bookRequest))
                .collect(Collectors.toList());
    }

    private Borrow bookRequestToBorrow(BaseBookRequest bookRequest){
        Borrow borrow = new Borrow();
        borrow.setId(bookRequest.getId());
        borrow.setStatus("0");
        return borrow;
    }
    private Book masterBookRequestToEntity(BaseBookRequest bookRequest) {
        Book book = new Book();
        book.setId(bookRequest.getId());
        book.setJudul(bookRequest.getJudul());
        book.setPengarang(bookRequest.getPengarang());
        book.setPenerbit(bookRequest.getPenerbit());
        book.setTanggalTerbit(bookRequest.getTanggalTerbit());
        book.setTebalBuku(bookRequest.getTebalBuku());
        return book;
    }

    @Override
    public void updateBook(BaseBookRequest baseBookRequest) throws ResourceNotFoundException {
        Book book = bookRepository.findById(baseBookRequest.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found for this id : " + baseBookRequest.getId()));
        bookRepository.save(composeBookToEntity(baseBookRequest, book));
    }

    private Book composeBookToEntity(BaseBookRequest baseBookRequest, Book book) {
        book.setJudul(baseBookRequest.getJudul());
        book.setPenerbit(baseBookRequest.getPenerbit());
        book.setPengarang(baseBookRequest.getPengarang());
        book.setPenerbit(baseBookRequest.getPenerbit());
        book.setTanggalTerbit(baseBookRequest.getTanggalTerbit());
        book.setTebalBuku(baseBookRequest.getTebalBuku());
        return book;
    }

    @Override
    public void updateStatus(String id, String status) throws ResourceNotFoundException {
        Borrow borrow = borrowRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found for this id : " + id));
        borrow.setStatus(status);
        borrowRepository.save(borrow);
    }

    @Override
    public void borrow(BorrowRequest borrowRequest) throws ResourceNotFoundException {
        Book book = bookRepository.findById(borrowRequest.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found for this id : " + borrowRequest.getId()));
        if (isBorrowed(book)){
            throw new BookIsBorrowedException("This book is borrowed by someone");
        }
        borrowRepository.save(mapBorrowReqToEntity(borrowRequest));
    }

    private boolean isBorrowed(Book book) {
        return book.getBorrow().getStatus().equals("1");
    }

    @Override
    public void delete(String id) {
        borrowRepository.deleteById(id);
    }

    private Borrow mapBorrowReqToEntity(BorrowRequest borrowRequest) {
        Borrow borrow = new Borrow();
        borrow.setId(borrowRequest.getId());
        borrow.setPeminjam(borrowRequest.getPeminjam());
        borrow.setStatus("1");
        borrow.setTanggalPinjam(new Date());
        borrow.setTanggalKembali(borrowRequest.getTanggalKembali());
        return borrow;
    }
}
