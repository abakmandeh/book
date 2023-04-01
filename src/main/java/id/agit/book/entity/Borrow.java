package id.agit.book.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@Table(name = "borrow")
public class Borrow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "peminjam")
    private String peminjam;

    @Column(name = "tanggal_pinjam")
    private Date tanggalPinjam;

    @Column(name = "tanggal_kembali")
    private Date tanggalKembali;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Book book;


}
