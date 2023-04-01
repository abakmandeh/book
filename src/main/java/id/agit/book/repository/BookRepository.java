package id.agit.book.repository;

import id.agit.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String>{

    @Query(
            "select b from Book b join b.borrow br where br.status =:status"
    )
    Optional<List<Book>> findByStatus(@Param("status") String status);
}