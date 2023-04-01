package id.agit.book.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class BookResponse {

    private String id;

    private String judul;

    private String pengarang;

    private String penerbit;

    private Date tanggalTerbit;

    private Long tebalBuku;

    private String status;

    private String peminjam;

    private Date tanggalPinjam;

    private Date tanggalKembali;


}
