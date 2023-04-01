package id.agit.book.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class BaseBookRequest {
    private String id;

    private String judul;

    private String pengarang;

    private String penerbit;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date tanggalTerbit;

    private Long tebalBuku;
}
