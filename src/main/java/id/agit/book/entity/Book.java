package id.agit.book.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@Table(name = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "judul", nullable = false)
    private String judul;

    @Column(name = "pengarang", nullable = false)
    private String pengarang;

    @Column(name = "penerbit", nullable = false)
    private String penerbit;

    @Column(name = "tanggal_terbit", nullable = false)
    private Date tanggalTerbit;

    @Column(name = "tebal_buku", nullable = false)
    private Long tebalBuku;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private Borrow borrow;

}
