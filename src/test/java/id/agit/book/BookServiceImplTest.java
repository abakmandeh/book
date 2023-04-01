package id.agit.book;

import id.agit.book.dto.request.BaseBookRequest;
import id.agit.book.dto.request.BorrowRequest;
import id.agit.book.dto.response.BookResponse;
import id.agit.book.entity.Book;
import id.agit.book.entity.Borrow;
import id.agit.book.exception.ResourceIsExistException;
import id.agit.book.exception.ResourceNotFoundException;
import id.agit.book.repository.BookRepository;
import id.agit.book.repository.BorrowRepository;
import id.agit.book.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository mockBookRepository;
    @Mock
    private BorrowRepository mockBorrowRepository;

    private BookServiceImpl bookServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        bookServiceImplUnderTest = new BookServiceImpl(mockBookRepository, mockBorrowRepository);
    }

    @Test
    void testGetAll() {
        // Setup
        final BookResponse bookResponse = new BookResponse();
        bookResponse.setId("id");
        bookResponse.setJudul("judul");
        bookResponse.setPengarang("pengarang");
        bookResponse.setPenerbit("penerbit");
        bookResponse.setTanggalTerbit(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bookResponse.setTebalBuku(0L);
        bookResponse.setStatus("status");
        bookResponse.setPeminjam("peminjam");
        bookResponse.setTanggalPinjam(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bookResponse.setTanggalKembali(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BookResponse> expectedResult = Arrays.asList(bookResponse);

        // Configure BookRepository.findAll(...).
        final Book book = new Book();
        book.setId("id");
        book.setJudul("judul");
        book.setPengarang("pengarang");
        book.setPenerbit("penerbit");
        book.setTanggalTerbit(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        book.setTebalBuku(0L);
        final Borrow borrow = new Borrow();
        borrow.setId("id");
        borrow.setStatus("status");
        borrow.setPeminjam("peminjam");
        borrow.setTanggalPinjam(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        borrow.setTanggalKembali(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        book.setBorrow(borrow);
        final List<Book> bookList = Arrays.asList(book);
        when(mockBookRepository.findAll()).thenReturn(bookList);

        // Run the test
        final List<BookResponse> result = bookServiceImplUnderTest.getAll();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAll_BookRepositoryReturnsNoItems() {
        // Setup
        when(mockBookRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        assertThatThrownBy(() -> bookServiceImplUnderTest.getAll()).isInstanceOf(ResourceIsExistException.class);
    }

    @Test
    void testGetByStatus() throws Exception {
        // Setup
        final BookResponse bookResponse = new BookResponse();
        bookResponse.setId("id");
        bookResponse.setJudul("judul");
        bookResponse.setPengarang("pengarang");
        bookResponse.setPenerbit("penerbit");
        bookResponse.setTanggalTerbit(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bookResponse.setTebalBuku(0L);
        bookResponse.setStatus("status");
        bookResponse.setPeminjam("peminjam");
        bookResponse.setTanggalPinjam(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bookResponse.setTanggalKembali(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BookResponse> expectedResult = Arrays.asList(bookResponse);

        // Configure BookRepository.findByStatus(...).
        final Book book = new Book();
        book.setId("id");
        book.setJudul("judul");
        book.setPengarang("pengarang");
        book.setPenerbit("penerbit");
        book.setTanggalTerbit(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        book.setTebalBuku(0L);
        final Borrow borrow = new Borrow();
        borrow.setId("id");
        borrow.setStatus("status");
        borrow.setPeminjam("peminjam");
        borrow.setTanggalPinjam(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        borrow.setTanggalKembali(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        book.setBorrow(borrow);
        final Optional<List<Book>> optionalBookList = Optional.of(Arrays.asList(book));
        when(mockBookRepository.findByStatus("status")).thenReturn(optionalBookList);

        // Run the test
        final List<BookResponse> result = bookServiceImplUnderTest.getByStatus("status");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetByStatus_BookRepositoryReturnsAbsent() {
        // Setup
        when(mockBookRepository.findByStatus("status")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> bookServiceImplUnderTest.getByStatus("status"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testGetByStatus_BookRepositoryReturnsNoItems() throws Exception {
        // Setup
        when(mockBookRepository.findByStatus("status")).thenReturn(Optional.of(Collections.emptyList()));

        // Run the test
        final List<BookResponse> result = bookServiceImplUnderTest.getByStatus("status");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testCreate_ThrowsResourceIsExistException() {
        // Setup
        final BaseBookRequest baseBookRequest = new BaseBookRequest();
        baseBookRequest.setId("id");
        baseBookRequest.setJudul("judul");
        baseBookRequest.setPengarang("pengarang");
        baseBookRequest.setPenerbit("penerbit");
        baseBookRequest.setTanggalTerbit(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseBookRequest.setTebalBuku(0L);
        final List<BaseBookRequest> baseBookRequestsList = Arrays.asList(baseBookRequest);

        // Configure BookRepository.findAllById(...).
        final Book book = new Book();
        book.setId("id");
        book.setJudul("judul");
        book.setPengarang("pengarang");
        book.setPenerbit("penerbit");
        book.setTanggalTerbit(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        book.setTebalBuku(0L);
        final Borrow borrow = new Borrow();
        borrow.setId("id");
        borrow.setStatus("status");
        borrow.setPeminjam("peminjam");
        borrow.setTanggalPinjam(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        borrow.setTanggalKembali(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        book.setBorrow(borrow);
        final List<Book> bookList = Arrays.asList(book);
        when(mockBookRepository.findAllById(new HashSet<>(Arrays.asList("id")))).thenReturn(bookList);

//        expectedException.expect(ResourceIsExistException.class);
        // Run the test
        assertThatThrownBy(() -> bookServiceImplUnderTest.create(baseBookRequestsList))
                .isInstanceOf(ResourceIsExistException.class);
    }

    @Test
    void testUpdateBook_BookRepositoryFindByIdReturnsAbsent() {
        // Setup
        final BaseBookRequest baseBookRequest = new BaseBookRequest();
        baseBookRequest.setId("id");
        baseBookRequest.setJudul("judul");
        baseBookRequest.setPengarang("pengarang");
        baseBookRequest.setPenerbit("penerbit");
        baseBookRequest.setTanggalTerbit(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseBookRequest.setTebalBuku(0L);

        when(mockBookRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> bookServiceImplUnderTest.updateBook(baseBookRequest))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testUpdateStatus_BorrowRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockBorrowRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> bookServiceImplUnderTest.updateStatus("id", "status"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testBorrow_BookRepositoryReturnsAbsent() {
        // Setup
        final BorrowRequest borrowRequest = new BorrowRequest();
        borrowRequest.setId("id");
        borrowRequest.setPeminjam("peminjam");
        borrowRequest.setTanggalKembali(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBookRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> bookServiceImplUnderTest.borrow(borrowRequest))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testDelete() {
        // Setup
        // Run the test
        bookServiceImplUnderTest.delete("id");

        // Verify the results
        verify(mockBorrowRepository).deleteById("id");
    }
}
